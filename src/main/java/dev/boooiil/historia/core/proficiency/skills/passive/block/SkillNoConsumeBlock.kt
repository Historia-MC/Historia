package dev.boooiil.historia.core.proficiency.skills.passive.block

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.dependents.Permissions
import dev.boooiil.historia.core.proficiency.skills.*
import dev.boooiil.historia.core.proficiency.skills.passive.entity.SkillEntityDrop
import dev.boooiil.historia.core.util.JSONUtils
import net.kyori.adventure.key.Key
import org.bukkit.GameEvent
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.block.Block
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

class SkillNoConsumeBlock(section: ConfigurationSection) : AbstractSkillRunnable(), ISkillHandler {
    override val name: NamespacedKey = HistoriaCore.getNamespacedKey(section.name)
    override val description: String = section.getString("description") ?: "No description provided."

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private val actions: Set<GameEvent>
    private val blocks: HashMap<Material, Pair<Double, Int>> = HashMap()
    private val blockCooldowns: ConcurrentHashMap<UUID, ConcurrentHashMap<Material, Long>> = ConcurrentHashMap()

    init {
        var sBlocks = section.getConfigurationSection("blocks") ?: error("Key 'blocks' must be specified.")
        var lActions = section.getStringList("actions")

        actions = lActions
            .map { action ->
                Registry.GAME_EVENT.get(Key.key(Key.MINECRAFT_NAMESPACE, action)) ?: error("Invalid action $action.")
            }
            .toSet()

        sBlocks.getKeys(false).forEach { key ->

            val material = (Material.matchMaterial(key)?.takeIf { it.isBlock }
                ?: error("Material $key is not a valid block."))
            val sMaterial = sBlocks.getConfigurationSection(key) ?: error("Key 'material' must be specified.")
            val chance = sMaterial.getDouble("chance")
            val cooldown = sMaterial.getInt("cooldown")
            val pair = Pair(chance, cooldown)

            blocks[material] = pair
        }

    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun handle(event: BlockPlaceEvent) {
        if (actions.contains(GameEvent.BLOCK_PLACE))
            execute(
                SkillSupplier(event),
                SkillSupplier(event.block),
                SkillSupplier(event.player),
                SkillSupplier(GameEvent.BLOCK_PLACE)
            )
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun handle(event: BlockBreakEvent) {
        if (actions.contains(GameEvent.BLOCK_DESTROY))
            execute(
                SkillSupplier(event),
                SkillSupplier(event.block),
                SkillSupplier(event.player),
                SkillSupplier(GameEvent.BLOCK_DESTROY)
            )
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    override fun execute(vararg skillSuppliers: SkillSupplier<*>) {

        skillSuppliers[0].get() as? Cancellable
            ?: error("Expected Block, but got null or wrong type.")
        val block: Block = getOrThrow(skillSuppliers, 1)
        val player: Player = getOrThrow(skillSuppliers, 2)
        val eventType: GameEvent = getOrThrow(skillSuppliers, 3)

        val type = block.type
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        if (!hasSkill(historiaPlayer) || !hasLevelRequirement(historiaPlayer)) return

        blocks[type]?.also { pair ->
            val currentTime = System.currentTimeMillis()
            val lastTime = blockCooldowns[player.uniqueId]?.get(type) ?: 0
            val onCooldown = (lastTime > currentTime)
            // TODO: uncomment this code and remove runnable after testing
//                .also {
//                    if (lastTime > 0 && !it) {
//                        cleanup(player.uniqueId, type)
//                    }
//                }

            if (!onCooldown && Permissions.canPlaceBlock(player, block)) {
                val chance = pair.first
                val cooldown = (pair.second * 1000).toLong()
                val nextTime = currentTime + cooldown

                // if we pass the chance
                if (Random.nextDouble() < chance) {
                    (blockCooldowns.getOrPut(player.uniqueId) { ConcurrentHashMap() })[type] = nextTime

                    when (eventType) {
                        GameEvent.BLOCK_PLACE -> player.inventory.addItem(ItemStack(type, 1))
                        GameEvent.BLOCK_DESTROY -> block.type = Material.AIR
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