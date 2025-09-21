package dev.boooiil.historia.core.proficiency.skills.passive.item

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.proficiency.skills.AbstractSkillHandler
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier
import dev.boooiil.historia.core.proficiency.skills.SkillType
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerItemHeldEvent
import java.util.*

class SkillAttributeWithItem(section: ConfigurationSection) : AbstractSkillHandler() {
    override val name: NamespacedKey
    override val description: String = section.getString("description") ?: "No description provided."

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    override val type: SkillType = SkillType.PASSIVE

    private val attribute: Attribute
    private val modifier: AttributeModifier
    private var material: Set<Material> = section.getStringList("material").map { mat ->
        requireNotNull(Material.matchMaterial(mat)) { "Invalid material specified $mat" }
    }.toSet()


    init {

        val sModifier = section.getConfigurationSection("attributes")
            ?: error("Key 'attributes' must be specified.")

        val sAttribute = sModifier.getString("attribute")
            ?: error("Key 'attribute' in attributes must be specified.")

        val sOperation = sModifier.getString("operation")
            ?: error("Key 'operation' in attributes must be specified.")

        val modifierLevel = sModifier.getDouble("factor", 1.0)

        @Suppress("DEPRECATION")
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
        val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

        val inventory = player.inventory
        val previousItem = inventory.getItem(event.previousSlot)
        val newItem = inventory.getItem(event.newSlot)

        newItem?.takeIf { material.contains(it.type) }?.also { _ ->

            if (!hasSkill(historiaPlayer) || !hasLevelRequirement(historiaPlayer)) return

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
