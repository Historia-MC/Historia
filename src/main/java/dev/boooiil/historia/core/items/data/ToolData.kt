package dev.boooiil.historia.core.items.data

import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.configuration.specific.LoreConfiguration
import dev.boooiil.historia.core.items.ItemData
import dev.boooiil.historia.core.util.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import kotlin.jvm.optionals.getOrNull

/**
 * ToolData class for interfacing with tool data in an item.
 *
 * @param damage        Attack damage of the tool.
 * @param speed         Attack speed of the tool.
 * @param knockback     Knockback of the tool.
 * @param maxDurability Max durability of the tool.
 */
@JvmRecord
data class ToolData(
    val damage: Double,
    val speed: Double,
    val knockback: Double,
    val maxDurability: Int
) : ItemData {
    /**
     * Applies the tool data to an item stack.
     *
     * @param stack The item stack to apply the data to.
     */
    override fun apply(stack: ItemStack) {
        // TODO: apply modified quality and other information to stack

        // should be fine since item is passed as ref

        applyData(stack)
        applyLore(stack)
    }

    /**
     * Apply the item's data to the given [ItemStack]'s
     * [PersistentDataContainer].
     *
     * @param stack the [ItemStack] to apply the data to.
     */
    private fun applyData(stack: ItemStack) {
        PDCUtils.setInComplexContainer(stack, DATA_KEY, DATA_TYPE, this)

        val damageAttr = AttributeModifier(
            getNamespacedKey("tool-damage"),
            (this.damage - 1).toDouble(), AttributeModifier.Operation.ADD_NUMBER
        )
        val speedAttr = AttributeModifier(
            getNamespacedKey("tool-speed"),
            (this.speed - 4).toDouble(), AttributeModifier.Operation.ADD_NUMBER
        )
        val knockbackAttr = AttributeModifier(
            getNamespacedKey("tool-knockback"),
            this.knockback.toDouble(), AttributeModifier.Operation.ADD_NUMBER
        )

        val meta = stack.getItemMeta()
        val damageable = meta as Damageable

        damageable.addAttributeModifier(Attribute.ATTACK_DAMAGE, damageAttr)
        damageable.addAttributeModifier(Attribute.ATTACK_SPEED, speedAttr)
        damageable.addAttributeModifier(Attribute.ATTACK_KNOCKBACK, knockbackAttr)

        damageable.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)

        damageable.setMaxDamage(this.maxDurability)

        stack.setItemMeta(damageable)

        // stack.setData(DataComponentTypes.MAX_DAMAGE, this.maxDurability);

        // stack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        // ItemAttributeModifiers.itemAttributes()
        // .addModifier(Attribute.ATTACK_DAMAGE, damageAttr)
        // .addModifier(Attribute.ATTACK_SPEED, speedAttr)
        // .addModifier(Attribute.ATTACK_KNOCKBACK, knockbackAttr)
        // );
        // stack.setData(DataComponentTypes.MAX_DAMAGE, this.maxDurability);
    }

    /**
     * Apply the item's lore to the given [ItemStack].
     *
     * @param stack the [ItemStack] to apply the lore to.
     */
    private fun applyLore(stack: ItemStack) {
        val meta = stack.itemMeta
        val raw = LoreConfiguration.get("tool").get("attribute")!!

        val parsed = raw.map { line ->
            Component.text()
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GRAY)
                .append(
                    MiniMessage.miniMessage().deserialize(
                        line,
                        Placeholder.parsed("tool-damage",
                            "%.2f".format(damage)),
                        Placeholder.parsed("tool-speed",
                            "%.2f".format(speed)),
                        Placeholder.parsed("tool-knockback",
                            "%.2f".format(knockback)),
                    )
                )
                .build()
        }
        meta.lore(parsed)
        stack.setItemMeta(meta)
    }

    /**
     * Get the Registry ID of the item.
     *
     * @return The ID of the item.
     */
    fun id(): String {
        throw UnsupportedOperationException("Not imlpemented.")
    }

    /**
     * Return the formatted string representation of the ToolData object.
     *
     * @return The formatted string.
     */
    override fun toString(): String {
        val sb = "ToolData{" +
                toJSON()

        return sb
    }

    /**
     * Convert the ToolData object to a JSON formatted string.
     *
     * @return The JSON formatted string.
     */
    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromValue("damage", damage) + ", " +
                JSONUtils.fromValue("speed", speed) + ", " +
                JSONUtils.fromValue("knockback", knockback) + ", " +
                JSONUtils.fromValue("maxDurability", maxDurability) +
                "}"

        return sb
    }

    private class DataType : PersistentDataType<PersistentDataContainer, ToolData> {
        override fun fromPrimitive(
            container: PersistentDataContainer,
            adapterContext: PersistentDataAdapterContext
        ): ToolData {
            val damage = container.get(DAMAGE_KEY, PersistentDataType.DOUBLE)!!
            val speed = container.get(SPEED_KEY, PersistentDataType.DOUBLE)!!
            val knockback = container.get(KNOCKBACK_KEY, PersistentDataType.DOUBLE)!!
            val durability = container.get(DURABILITY_KEY, PersistentDataType.INTEGER)!!

            return ToolData(
                NumberUtils.roundDouble(damage, 2),
                NumberUtils.roundDouble(speed, 2),
                NumberUtils.roundDouble(knockback, 2),
                durability
            )
        }

        override fun getComplexType(): Class<ToolData> {
            return ToolData::class.java
        }

        override fun getPrimitiveType(): Class<PersistentDataContainer> {
            return PersistentDataContainer::class.java
        }

        override fun toPrimitive(
            data: ToolData,
            adapterContext: PersistentDataAdapterContext
        ): PersistentDataContainer {
            val container = adapterContext.newPersistentDataContainer()

            container.set(DAMAGE_KEY, PersistentDataType.DOUBLE, data.damage - 1)
            container.set(SPEED_KEY, PersistentDataType.DOUBLE, data.speed - 4)
            container.set(KNOCKBACK_KEY, PersistentDataType.DOUBLE, data.knockback)
            container.set(DURABILITY_KEY, PersistentDataType.INTEGER, data.maxDurability)

            return container
        }

        companion object {
            private val DAMAGE_KEY = getNamespacedKey("damage")
            private val SPEED_KEY = getNamespacedKey("speed")
            private val KNOCKBACK_KEY = getNamespacedKey("knockback")
            private val DURABILITY_KEY = getNamespacedKey("durability")
        }
    }

    companion object {
        val DATA_TYPE: PersistentDataType<PersistentDataContainer, ToolData> = DataType()
        val DATA_KEY: NamespacedKey = getNamespacedKey("tool")

        /**
         * Get tool data from an item stack.
         *
         * @param stack ItemStack to get data from.
         * @return ToolData object containing the tool data.
         */
        @JvmStatic
        fun fromStack(stack: ItemStack): ToolData? {
            return PDCUtils.getFromComplexContainer(stack, DATA_KEY, DATA_TYPE).getOrNull()
        }
    }
}
