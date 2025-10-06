package dev.boooiil.historia.core.proficiency.skills.passive.entity

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.*
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Ageable
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SkillAnimalDropsShear(section: ConfigurationSection) : AbstractSkillRunnable(section), ISkillHandler {

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private val entityConfigs: Map<EntityType, EntityShearConfig>
    private val shearCooldowns: ConcurrentHashMap<UUID, Long> = ConcurrentHashMap()

    data class EntityShearConfig(
        val drops: Map<Material, IntRange>,
        val cooldown: Long
    )

    init {
        val entitiesSection = section.getConfigurationSection("entities")
            ?: error("Key 'entities' must be specified.")

        this.entityConfigs = entitiesSection.getKeys(false).associate { entityName ->
            val entityType = requireNotNull(EntityType.fromName(entityName)) {
                "Invalid entity type specified $entityName"
            }

            val entitySection = entitiesSection.getConfigurationSection(entityName)
                ?: error("Entity section for $entityName must be specified.")

            val dropsSection = entitySection.getConfigurationSection("drops")
                ?: error("Drops section for $entityName must be specified.")

            val drops = dropsSection.getKeys(false).associate { dropName ->
                val dropSection = dropsSection.getConfigurationSection(dropName)
                    ?: error("Drop section for $dropName must be specified.")

                val material = requireNotNull(Material.matchMaterial(dropName)) {
                    "Invalid material specified $dropName"
                }

                val amountList = dropSection.getIntegerList("amount")
                require(amountList.size == 2) { "Amount must be a list of exactly 2 integers [min, max]" }

                val min = amountList[0]
                val max = amountList[1]
                require(max >= min) { "Max amount must be >= min amount" }

                material to min..max
            }

            val cooldown = (entitySection.getInt("cooldown", 1) * 1000).toLong()

            entityType to EntityShearConfig(drops, cooldown)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun handle(event: PlayerInteractEntityEvent) {
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
        val event: PlayerInteractEntityEvent = getOrThrow(skillSuppliers, 0)
        val player: Player = event.player
        val entity = event.rightClicked

        // Check if entity is in the allowed list
        val entityConfig = entityConfigs[entity.type] ?: return

        // Check if player is holding shears
        val heldItem = player.inventory.itemInMainHand
        if (heldItem.type != Material.SHEARS) {
            return
        }

        // Check if entity can be sheared using Paper API
        if (entity is Ageable && !entity.isAdult) {
            CoreLogger.debugToConsole("entity ${entity.name} not sheared as it is a child")
            return
        }

        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        // If player doesn't have the skill, cancel the event (prevent shearing)
        if (!hasSkill(historiaPlayer) || !hasLevelRequirement(historiaPlayer)) {
            // console log historiaPlayer name and skill level
            CoreLogger.debugToConsole(
                "skill? ${hasSkill(historiaPlayer)} level? ${hasLevelRequirement(historiaPlayer)}"
            )
            return
        }

        val currentTime = System.currentTimeMillis()
        val lastTime = shearCooldowns[player.uniqueId] ?: 0
        val onCooldown = (lastTime > currentTime)

        if (!onCooldown) {
            val nextTime = currentTime + entityConfig.cooldown
            shearCooldowns[player.uniqueId] = nextTime

            entityConfig.drops.forEach { (material, range) ->
                val dropAmount = range.random()
                if (dropAmount > 0) {
                    val dropItem = ItemStack(material, dropAmount)
                    entity.world.dropItemNaturally(entity.location, dropItem)
                    (entity as Ageable).setBaby()
                }
            }
        } else {
            val timeLeftMillis = lastTime - currentTime
            val timeLeftSeconds = (timeLeftMillis / 1000).coerceAtLeast(1)
            CoreLogger.infoToPlayer(
                "You must wait $timeLeftSeconds seconds before using this skill again.",
                player.uniqueId
            )
        }
    }

    override fun run() {
        val now = System.currentTimeMillis()

        // Remove expired cooldowns
        shearCooldowns.entries.removeIf { (_, cooldownTime) -> cooldownTime <= now }
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
        return SkillAnimalDropsShear(section)
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
