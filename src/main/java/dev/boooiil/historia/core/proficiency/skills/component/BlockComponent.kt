package dev.boooiil.historia.core.proficiency.skills.component

import dev.boooiil.historia.core.util.CoreLogger
import net.kyori.adventure.key.Key
import org.bukkit.GameEvent
import org.bukkit.Material
import org.bukkit.Registry
import org.bukkit.configuration.ConfigurationSection
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Component for handling block-related skill configurations and cooldowns.
 *
 * @property section The configuration section containing block settings.
 */
class BlockComponent(
    /**
     * SKILL_CONFIG_ROOT.blocks
     */
    section: ConfigurationSection
) {

    @ConsistentCopyVisibility
    data class BlockComponentConfig private constructor(
        val cooldown: Int = 0,
        val chance: Float = 1.0f,
        val dropUnmodifiedItem: Boolean = false,
        val itemDoesntDrop: Boolean = false,
        val skillRestricted: Boolean = true,
        val amount: IntRange = 1..1,
        val actions: Set<GameEvent> = emptySet(),
        val alternateDrops: Map<Material, IntRange> = EnumMap(Material::class.java)
    ) {
        companion object {
            fun fromSection(section: ConfigurationSection): BlockComponentConfig {
                return BlockComponentConfig(
                    cooldown = section.getInt("cooldown", 0),
                    chance = section.getDouble("chance", 1.0).toFloat(),
                    dropUnmodifiedItem = section.getBoolean("drop_unmodified", false),
                    itemDoesntDrop = section.getBoolean("no_drop", false),
                    skillRestricted = section.getBoolean("restrict", true),
                    amount = section.get("amount")?.let {
                        when (it) {
                            is Int -> it..it
                            is List<*> -> {
                                val intList = it.filterIsInstance<Int>()
                                if (intList.size != 2) {
                                    error("Amount list must contain exactly two integers.")
                                }
                                intList[0]..intList[1]
                            }

                            else -> error("Amount must be an integer or a list of two integers.")
                        }
                    } ?: (1..1),
                    actions = section.getStringList("actions").map { action ->
                        Registry.GAME_EVENT.get(Key.key(Key.MINECRAFT_NAMESPACE, action))
                            ?: error("Invalid action $action.")
                    }.toSet(),
                    alternateDrops = section.getConfigurationSection("alternate")?.let { altSection ->
                        altSection.getKeys(false).associate { altKey ->
                            val altMaterial = Material.matchMaterial(altKey)
                                ?: error("Invalid alternate material: $altKey")
                            val amount = altSection.getConfigurationSection(altKey)?.let { materialSection ->
                                materialSection.get("amount").let {
                                    CoreLogger.debugToConsole(materialSection.toString())
                                    when (it) {
                                        is Int -> it to it
                                        is List<*> -> {
                                            val intList = it.filterIsInstance<Int>()
                                            if (intList.size != 2) {
                                                error("Amount list for $altKey must contain exactly two integers.")
                                            }
                                            intList[0] to intList[1]
                                        }

                                        else -> error("Amount for $altKey must be an integer or a list of two integers.")
                                    }
                                }
                            } ?: Pair(1, 1)
                            altMaterial to (amount.first..amount.second)
                        }
                    } ?: EnumMap(Material::class.java) // same as your logic
                )

            }
        }
    }

    var blocks: EnumMap<Material, BlockComponentConfig> = EnumMap(Material::class.java)
    val blockCooldowns: ConcurrentHashMap<UUID, ConcurrentHashMap<Material, Long>> = ConcurrentHashMap()

    init {
        val blockKeys = section.getKeys(false)

        blockKeys.forEach { block ->
            val material = Material.matchMaterial(block)?.takeIf { it.isBlock }
                ?: error("Material $block is not a valid block.")

            val blockSection = section.getConfigurationSection(block)
                ?: error("Key 'material' must be specified.")

            val blockComponentConfig = BlockComponentConfig.fromSection(blockSection)

            blocks[material] = blockComponentConfig

        }
    }

    private fun cleanup(uuid: UUID, material: Material) {
        blockCooldowns[uuid]?.also {
            it.remove(material)

            if (it.isEmpty()) blockCooldowns.remove(uuid)
        }
    }
}
