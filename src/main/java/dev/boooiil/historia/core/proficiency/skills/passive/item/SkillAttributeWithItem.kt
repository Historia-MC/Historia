package dev.boooiil.historia.core.proficiency.skills.passive.item

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.HistoriaCore.Companion.instance
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.ISkillHandler
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier
import dev.boooiil.historia.core.proficiency.skills.SkillType
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.Inventory
import java.util.*

class SkillAttributeWithItem(section: ConfigurationSection) : ISkillHandler {
    override val name: NamespacedKey
    override val description: String = section.getString("description") ?: "No description provided."
    override val type: SkillType
        /**
         * Get the type of the skill.
         *
         * @return Type of the skill.
         */
        get() = SkillType.PASSIVE

    private val attribute: Attribute
    private val modifier: AttributeModifier
    private var material: Set<Material>


    init {
        require(section.contains("attributes")) { "Key 'attributes' must be specified." }
        require(section.contains("material")) { "Key 'material' must be specified." }

        this.material = section.getStringList("material").map {
                mat -> requireNotNull(Material.matchMaterial(mat)) { "Invalid material specified $mat" }
        }.toSet()

        val sModifier = section.getConfigurationSection("attributes")
            ?: error("Key 'attributes' must be specified.")

        val sAttribute = sModifier.getString("attribute")
            ?: error("Key 'attribute' in attributes must be specified.")

        val sOperation = sModifier.getString("operation")
            ?: error("Key 'operation' in attributes must be specified.")

        val modifierLevel = sModifier.getDouble("factor", 1.0)

        @Suppress("deprecation")
        val attribute = Attribute.valueOf(sAttribute)
        val operation = AttributeModifier.Operation.valueOf(sOperation.uppercase(Locale.getDefault()))
        val modifier = AttributeModifier(
            getNamespacedKey("skill_attribute_with_item"),
            modifierLevel,
            operation
        )

        this.name = getNamespacedKey(section.name)
        this.modifier = modifier
        this.attribute = attribute
    }

    @EventHandler
    fun handle(event: PlayerItemHeldEvent?) {
        execute(SkillSupplier<PlayerItemHeldEvent?>(event))
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    override fun execute(vararg skillSuppliers: SkillSupplier<*>?) {
        val event = (skillSuppliers[0]?.get() as? PlayerItemHeldEvent)
            ?: error("Expected PlayerItemHeldEvent, but got null or wrong type")

        val player = event.player
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        val inventory = player.inventory
        val previousItem = inventory.getItem(event.previousSlot)
        val newItem = inventory.getItem(event.newSlot)

        newItem?.takeIf { material.contains(it.type) }?.also { _ ->
            // Skip if player doesn't have this skill
            val proficiency = historiaPlayer.proficiency
            if (!proficiency.hasSkill(this)) return

            val playerLevel = historiaPlayer.level
            val skillLevel = proficiency.skills[this]
                ?: return // safely handle missing skill entry

            // Skip if player level is too low
            if (playerLevel < skillLevel) return

            player.getAttribute(attribute)?.takeIf { !it.modifiers.contains(modifier) }?.addModifier(modifier)
        }

        previousItem?.takeIf { material.contains(it.type) }?.also { _ ->
            player.getAttribute(attribute)?.takeIf { it.modifiers.contains(modifier) }?.removeModifier(modifier)
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
        sb.append(JSONUtils.fromValue("attribute", this.attribute.key)).append(",")
        sb.append(JSONUtils.fromValue("modifier", this.modifier.amount)).append(",")
        sb.append(JSONUtils.fromValue("operation", this.modifier.operation.toString())).append(",")
        sb.append(JSONUtils.fromValue("material", this.material.toString()))
        sb.append("}")

        return sb.toString()
    }
}
