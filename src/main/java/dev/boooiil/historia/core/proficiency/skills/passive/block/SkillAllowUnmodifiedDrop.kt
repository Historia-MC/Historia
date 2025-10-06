package dev.boooiil.historia.core.proficiency.skills.passive.block

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.*
import dev.boooiil.historia.core.proficiency.skills.passive.entity.SkillEntityDrop
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SkillAllowUnmodifiedDrop(section: ConfigurationSection) : AbstractSkillRunnable(section), ISkillHandler {

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    // Data class to hold block configuration
    data class BlockConfig(
        val cooldown: Int,
        val noDrop: Boolean = false,
        val restrict: Boolean = false,
        val alternate: Map<Material, IntRange>? = null
    )

    private val blocks: HashMap<Material, BlockConfig> = HashMap()
    private val blockCooldowns: ConcurrentHashMap<UUID, ConcurrentHashMap<Material, Long>> = ConcurrentHashMap()

    init {
        val sBlocks = section.getConfigurationSection("blocks") ?: error("Key 'blocks' must be specified.")

        sBlocks.getKeys(false).forEach { key ->
            val material = (Material.matchMaterial(key)?.takeIf { it.isBlock }
                ?: error("Material $key is not a valid block."))
            val sMaterial = sBlocks.getConfigurationSection(key) ?: error("Key 'material' must be specified.")
            
            val cooldown = sMaterial.getInt("cooldown")
            val noDrop = sMaterial.getBoolean("no_drop", false)
            val restrict = sMaterial.getBoolean("restrict", false)
            
            // Parse alternate drops if present
            val alternate = sMaterial.getConfigurationSection("alternate")?.let { altSection ->
                altSection.getKeys(false).associate { altKey ->
                    val altMaterial = Material.matchMaterial(altKey) 
                        ?: error("Invalid alternate material: $altKey")
                    val amount = altSection.get("$altKey.amount") ?: error("Amount must be specified for alternate material: $altKey")
                    val (minAmount, maxAmount) = when (amount) {
                        is Int -> amount to amount
                        is List<*> -> {
                            val amounts = amount.filterIsInstance<Int>()
                            if (amounts.size == 2) {
                                amounts[0] to amounts[1]
                            } else {
                                error("Amount must be a single integer or a list of two integers [min, max]")
                            }
                        }
                        else -> error("Amount must be an integer or a list of two integers [min, max]")
                    }
                    altMaterial to minAmount..maxAmount
                }
            }

            blocks[material] = BlockConfig(cooldown, noDrop, restrict, alternate)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun handle(event: BlockBreakEvent) {
        execute(
            SkillSupplier(event)
        )
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    override fun execute(vararg skillSuppliers: SkillSupplier<*>) {
        CoreLogger.debugToConsole("allow unmodified drop event")
        val event: BlockBreakEvent = getOrThrow(skillSuppliers, 0)
        val block: Block = event.block
        val player: Player = event.player

        val type = block.type
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        // Check if player has skill and level requirement
        val hasSkillAndLevel = hasSkill(historiaPlayer) && hasLevelRequirement(historiaPlayer)

        blocks[type]?.also { config ->
            CoreLogger.debugToConsole("allow unmodified drop event 2")
            
            // Handle restriction - if restrict is true and player doesn't have skill, prevent breaking
            if (config.restrict && !hasSkillAndLevel) {
                event.isCancelled = true
                CoreLogger.debugToConsole("Block breaking restricted - player lacks skill")
                return
            }

            // Handle no_drop - if no_drop is true and player doesn't have skill, allow breaking but no drops
            if (config.noDrop && !hasSkillAndLevel) {
                block.type = Material.AIR
                CoreLogger.debugToConsole("No drop for player without skill - cleared drops")
                return
            }

            // If player doesn't have skill and it's not restricted/no_drop, let normal behavior happen
            if (!hasSkillAndLevel) {
                return
            }

            // Player has skill - give them silk touch behavior
            // Check cooldown
            val currentTime = System.currentTimeMillis()
            val lastTime = blockCooldowns[player.uniqueId]?.get(type) ?: 0
            val onCooldown = (lastTime > currentTime)

            if (!onCooldown) {
                val cooldown = (config.cooldown * 1000).toLong()
                val nextTime = currentTime + cooldown

                (blockCooldowns.getOrPut(player.uniqueId) { ConcurrentHashMap() })[type] = nextTime

                // Cancel the event to prevent normal drops
                event.isCancelled = true

                // Set block to air
                block.type = Material.AIR

                // Handle different drop scenarios for skilled players
                when {
                    config.alternate != null -> {
                        // Drop alternate items
                        config.alternate.forEach { (material, range) ->
                            val amount = if (range.first == range.last) {
                                range.first
                            } else {
                                Random().nextInt(range.first, range.last + 1)
                            }
                            val item = ItemStack(material, amount)
                            block.world.dropItemNaturally(block.location, item)
                        }
                        CoreLogger.debugToConsole("Alternate drops configured - dropped ${config.alternate.size} different items")
                    }
                    
                    else -> {
                        // Default behavior - drop the block itself (silk touch effect)
                        val item: ItemStack = ItemStack(type)
                        block.world.dropItemNaturally(block.location, item)
                        CoreLogger.debugToConsole("Silk touch effect - dropped ${type.name}")
                    }
                }
            }
        }
    }

    private fun cleanup(uuid: UUID, material: Material) {
        blockCooldowns[uuid]?.also {
            it.remove(material)

            if (it.isEmpty()) blockCooldowns.remove(uuid)
        }
    }

    override fun run() {
        val now = System.currentTimeMillis()

        // Iterate over the outer map
        val outerIterator = blockCooldowns.iterator()
        while (outerIterator.hasNext()) {
            val (_, innerMap) = outerIterator.next()

            // Remove expired cooldowns from the inner map
            innerMap.entries.removeIf { (_, cooldownTime) -> cooldownTime <= now }

            // If the inner map is now empty, remove the outer entry
            if (innerMap.isEmpty()) {
                outerIterator.remove()
            }
        }
    }

    /**
     * Register to be used to handle when the skill executes.
     */
    override fun register() {
        HistoriaCore.instance.registerEvent(this)
        HistoriaCore.instance.registerRunnable(this.runnable())
    }

    override fun deregister() {
        TODO("Not yet implemented")
    }

    override fun create(section: ConfigurationSection): ISkill {
        return SkillEntityDrop(section)
    }

    override fun toJSON(): String {
        // TODO: finish
        val sb = StringBuilder()

        sb.append("{")
        sb.append(JSONUtils.fromValue("name", this.name)).append(",")
        sb.append(JSONUtils.fromValue("description", this.description))
        sb.append("}")

        return sb.toString()
    }
}