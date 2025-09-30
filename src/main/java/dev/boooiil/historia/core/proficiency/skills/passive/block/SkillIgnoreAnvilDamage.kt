package dev.boooiil.historia.core.proficiency.skills.passive.block

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.dependents.Permissions
import dev.boooiil.historia.core.proficiency.skills.*
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SkillIgnoreAnvilDamage(section: ConfigurationSection) : AbstractSkillRunnable(), ISkillHandler {
    override val name: NamespacedKey = HistoriaCore.getNamespacedKey(section.name)
    override val description: String = section.getString("description") ?: "No description provided."

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private val anvilCooldowns: ConcurrentHashMap<UUID, Long> = ConcurrentHashMap()
    private val cooldown: Long = (section.getInt("cooldown", 1) * 1000).toLong()

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun handle(event: PrepareAnvilEvent) {
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
        val event: PrepareAnvilEvent = getOrThrow(skillSuppliers, 0)
        val player: Player = event.viewers.firstOrNull() as? Player ?: return
        val inventory = event.inventory

        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        if (!hasSkill(historiaPlayer) || !hasLevelRequirement(historiaPlayer)) return

        val currentTime = System.currentTimeMillis()
        val lastTime = anvilCooldowns[player.uniqueId] ?: 0
        val onCooldown = (lastTime > currentTime)

        if (!onCooldown && Permissions.canUseAnvil(player)) {
            val nextTime = currentTime + cooldown
            anvilCooldowns[player.uniqueId] = nextTime

            // Prevent anvil durability damage by setting repair cost to 0
            inventory.repairCost = 0
        }
    }

    override fun run() {
        val now = System.currentTimeMillis()

        // Remove expired cooldowns
        anvilCooldowns.entries.removeIf { (_, cooldownTime) -> cooldownTime <= now }
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
        return SkillIgnoreAnvilDamage(section)
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
