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

class SkillGreaterBlockDrop(section: ConfigurationSection) : AbstractSkill(section), ISkillHandler {

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private data class BlockConfig(
        val chance: Double = 1.0,
        val minAmount: Int = 1,
        val maxAmount: Int = 1
    )

    private val blocks: HashMap<Material, BlockConfig> = HashMap()

    init {
        var sBlocks = section.getConfigurationSection("blocks") ?: error("Key 'blocks' must be specified.")

        sBlocks.getKeys(false).forEach { key ->

            val material = (Material.matchMaterial(key)?.takeIf { it.isBlock }
                ?: error("Material $key is not a valid block."))
            val sMaterial = sBlocks.getConfigurationSection(key) ?: error("Key 'material' must be specified.")
            val chance = sMaterial.getDouble("chance", 1.0)
            
            // Support both single amount and range [min, max]
            val amount = sMaterial.get("amount")
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

            blocks[material] = BlockConfig(chance, minAmount, maxAmount)
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
        CoreLogger.debugToConsole("greater block drop event")
        val event: BlockBreakEvent = getOrThrow(skillSuppliers, 0)
        val block: Block = event.block
        val player: Player = event.player

        val type = block.type
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        if (!hasSkill(historiaPlayer) || !hasLevelRequirement(historiaPlayer)) {
           // log hasSkill and hasLevelRequirement
           CoreLogger.debugToConsole("hasSkill: ${hasSkill(historiaPlayer)}")
           CoreLogger.debugToConsole("hasLevelRequirement: ${hasLevelRequirement(historiaPlayer)}")
           return
        }

        blocks[type]?.also { config ->
            CoreLogger.debugToConsole("greater block drop event 2")
            
            // Check chance for greater drop
            val random = Random()
            if (random.nextDouble() <= config.chance) {
                // Cancel the event to prevent normal drops
                event.isCancelled = true

                // Set block to air
                block.type = Material.AIR

                // Calculate random amount between min and max (inclusive)
                val randomAmount = if (config.minAmount == config.maxAmount) {
                    config.minAmount
                } else {
                    random.nextInt(config.minAmount, config.maxAmount + 1)
                }

                // Drop the random amount of items
                val item: ItemStack = ItemStack(type, randomAmount)
                block.world.dropItemNaturally(block.location, item)

                CoreLogger.debugToConsole("greater block drop: dropped $randomAmount ${type.name} (range: ${config.minAmount}-${config.maxAmount}) with ${config.chance * 100}% chance")
            } else {
                CoreLogger.debugToConsole("greater block drop: chance failed (${config.chance * 100}%)")
            }
        }
    }


    /**
     * Register to be used to handle when the skill executes.
     */
    override fun register() {
        HistoriaCore.instance.registerEvent(this)
    }

    override fun deregister() {
        TODO("Not yet implemented")
    }

    override fun create(section: ConfigurationSection): ISkill {
        return SkillGreaterBlockDrop(section)
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