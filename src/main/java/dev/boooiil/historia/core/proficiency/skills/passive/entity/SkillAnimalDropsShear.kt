package dev.boooiil.historia.core.proficiency.skills.passive.entity

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.dependents.Permissions
import dev.boooiil.historia.core.proficiency.skills.*
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.Material
import org.bukkit.NamespacedKey
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

class SkillAnimalDropsShear(section: ConfigurationSection) : AbstractSkillRunnable(), ISkillHandler {
    override val name: NamespacedKey = HistoriaCore.getNamespacedKey(section.name)
    override val description: String = section.getString("description") ?: "No description provided."

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private val entities: Set<EntityType> = section.getStringList("entity").map { entity ->
        requireNotNull(EntityType.fromName(entity)) { "Invalid entity type specified $entity" }
    }.toSet()
    
    private val shearCooldowns: ConcurrentHashMap<UUID, Long> = ConcurrentHashMap()
    private val cooldown: Long = (section.getInt("cooldown", 1) * 1000).toLong()

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
        if (!entities.contains(entity.type)) return

        // Check if player is holding shears
        val heldItem = player.inventory.itemInMainHand
        if (heldItem.type != Material.SHEARS) return

        // Check if entity is an adult (for ageable entities)
        if (entity is Ageable && !entity.isAdult) return

        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        // If player doesn't have the skill, cancel the event (prevent shearing)
        if (!hasSkill(historiaPlayer) || !hasLevelRequirement(historiaPlayer)) {
            event.isCancelled = true
            return
        }

        val currentTime = System.currentTimeMillis()
        val lastTime = shearCooldowns[player.uniqueId] ?: 0
        val onCooldown = (lastTime > currentTime)

        if (!onCooldown && Permissions.canShearAnimal(player)) {
            val nextTime = currentTime + cooldown
            shearCooldowns[player.uniqueId] = nextTime

            // Allow the shearing to proceed (don't cancel the event)
            // The default Minecraft shearing behavior will handle the rest
        } else if (onCooldown) {
            // Cancel if on cooldown
            event.isCancelled = true
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
        HistoriaCore.instance.registerRunnable(this)
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
