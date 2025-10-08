package dev.boooiil.historia.core.proficiency.skills.passive.block

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.*
import dev.boooiil.historia.core.proficiency.skills.component.BlockComponent
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
import java.util.concurrent.ConcurrentHashMap

class SkillAllowUnmodifiedDrop(section: ConfigurationSection) : AbstractSkillRunnable(section), ISkillHandler {

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    val blockComponent =
        BlockComponent(
            section.getConfigurationSection("blocks")
                ?: error("Tried to create a block component in $name that did not supply a 'blocks' key.")
        )

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

        blockComponent.blocks[type]?.also { config ->
            CoreLogger.debugToConsole("allow unmodified drop event 2")

            // Handle restriction - if restrict is true and player doesn't have skill, prevent breaking
            if (config.skillRestricted && !hasSkillAndLevel) {
                event.isCancelled = true
                CoreLogger.debugToConsole("Block breaking restricted - player lacks skill")
                return
            }

            // Handle no_drop - if no_drop is true and player doesn't have skill, allow breaking but no drops
            if (config.itemDoesntDrop && !hasSkillAndLevel) {
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
            val lastTime = blockComponent.blockCooldowns[player.uniqueId]?.get(type) ?: 0
            val onCooldown = (lastTime > currentTime)
            // TODO: uncomment this code and remove runnable after testing
//                .also {
//                    if (lastTime > 0 && !it) {
//                        blockComponent.cleanup(player.uniqueId, type)
//                    }
//                }

            if (!onCooldown) {
                val cooldown = (config.cooldown * 1000).toLong()
                val nextTime = currentTime + cooldown

                (blockComponent.blockCooldowns.getOrPut(player.uniqueId) { ConcurrentHashMap() })[type] = nextTime

                // Set block to air
                block.type = Material.AIR

                if (!config.alternateDrops.isEmpty()) {
                    config.alternateDrops.forEach { drop ->
                        val loc = block.location.clone().add(0.5, 0.5, 0.5)
                        block.world.dropItemNaturally(loc, ItemStack(drop.key, drop.value.random()))
                    }

                } else {
                    val loc = block.location.clone().add(0.5, 0.5, 0.5)
                    block.world.dropItemNaturally(loc, ItemStack(type, config.amount.random()))
                }
            }
        }
    }


    override fun run() {
        val now = System.currentTimeMillis()

        // Iterate over the outer map
        val outerIterator = blockComponent.blockCooldowns.iterator()
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