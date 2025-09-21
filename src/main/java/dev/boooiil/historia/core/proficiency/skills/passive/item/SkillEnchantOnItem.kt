package dev.boooiil.historia.core.proficiency.skills.passive.item

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.proficiency.skills.AbstractSkillHandler
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier
import dev.boooiil.historia.core.proficiency.skills.SkillType
import dev.boooiil.historia.core.util.JSONUtils
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerItemHeldEvent

class SkillEnchantOnItem(section: ConfigurationSection) : AbstractSkillHandler() {
    override val name: NamespacedKey = HistoriaCore.getNamespacedKey(section.name)
    override val description: String = section.getString("description") ?: "No description provided."

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private val enchants: Set<Pair<Enchantment, Int>> = section.getStringList("enchants").map { enchant ->
        val sEnchantment = section.getConfigurationSection("enchants")!!
        val enchantLevel = sEnchantment.getInt(enchant, 0)
        // what type of stupid ass shit is this
        val enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT)
            .get(Key.key(Key.MINECRAFT_NAMESPACE, enchant)) ?: error("Enchantment was not valid for ${section.name}")

        Pair(enchantment, enchantLevel)
    }.toSet()

    private var material: Set<Material> = section.getStringList("material").map { mat ->
        requireNotNull(Material.matchMaterial(mat)) { "Invalid material specified $mat" }
    }.toSet()

    @EventHandler
    fun handle(event: PlayerItemHeldEvent) {
        execute(SkillSupplier(event))
    }


    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    override fun execute(vararg skillSuppliers: SkillSupplier<*>) {
        val event: PlayerItemHeldEvent = getOrThrow(skillSuppliers, 0)

        val player = event.player
        val historiaPlayer = getHistoriaPlayer(player)

        val inventory = player.inventory
        val previousItem = inventory.getItem(event.previousSlot)
        val newItem = inventory.getItem(event.newSlot)

        // Handle new item
        newItem?.takeIf { material.contains(it.type) }?.itemMeta?.also { meta ->

            if (!hasSkill(historiaPlayer) || !hasLevelRequirement(historiaPlayer))
                return

            val itemMeta = newItem.itemMeta
            val existingEnchants = itemMeta.enchants

            enchants.forEach { enchant ->
                existingEnchants[enchant.first] = enchant.second
            }

            newItem.itemMeta = itemMeta

        }

        // Handle previous item
        previousItem?.takeIf { material.contains(it.type) }?.itemMeta?.also { meta ->

            val itemMeta = previousItem.itemMeta
            val existingEnchants = itemMeta.enchants

            enchants.forEach { enchant ->
                existingEnchants.remove(enchant.first)
            }

            previousItem.itemMeta = itemMeta
        }
    }

    /**
     * Register to be used to handle when the skill executes.
     */
    override fun register() {
        HistoriaCore.instance.registerEvent(this)
    }

    override fun deregister() {
    }

    override fun create(section: ConfigurationSection): SkillAttributeWithItem {
        return SkillAttributeWithItem(section)
    }

    override fun toJSON(): String {
        val sb = StringBuilder()

        sb.append("{")
        sb.append(JSONUtils.fromValue("name", this.name)).append(",")
        sb.append(JSONUtils.fromValue("material", this.material.toString()))
        sb.append("}")

        return sb.toString()
    }
}