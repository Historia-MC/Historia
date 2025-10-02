package dev.boooiil.historia.core.items.data

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.date.Calendar
import dev.boooiil.historia.core.items.ItemData
import dev.boooiil.historia.core.date.ServerCalendar
import dev.boooiil.historia.core.util.*
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import kotlin.jvm.optionals.getOrNull

class ConsumableData(
    var hunger: Int,
    var saturation: Float,
    var expireDay: Int,
    val effects: MutableList<PotionEffect>
) : ItemData {

//    constructor(hunger: Int, saturation: Float, expirationDays: Int, effects: MutableList<PotionEffect>) : this(
//        hunger,
//        saturation,
//        ,
//        effects
//    )

    override fun apply(stack: ItemStack) {
        PDCUtils.setInComplexContainer<PersistentDataContainer, ConsumableData>(stack, COMPONENT_KEY, DataType, this)
        applyLore(stack)
    }

    private fun applyLore(stack: ItemStack) {
        val configId = PDCUtils.getFromContainer(
            stack,
            HistoriaCore.getNamespacedKey("config-id"),
            PersistentDataType.STRING
        ).orElse("")

        val lore = stack.lore()
        if (lore.isNullOrEmpty()) {
            CoreLogger.debugToConsole(configId, "has no lore, skipping placeholder.")
            return
        }

        val expiration = when {
            !this.canExpire -> "Does not expire"
            this.isExpired -> "Expired"
            else -> "Expires on ${Calendar.Gregorian.dateOf(this.expireDay)}"
        }

        val updatedLore = lore.map { component ->
            if (KyoriUtils.contains(component, "<consumable-expiration>")) {
                KyoriUtils.replaceComponent(component, "consumable-expiration", expiration)
            } else {
                component
            }
        }

        stack.lore(updatedLore)
    }

    val canExpire: Boolean = expireDay >= 0
    val isExpired: Boolean get() = canExpire && ServerCalendar.daysSinceStart > expireDay

    override fun toJSON(): String {
        return "{" +
                JSONUtils.fromValue("hunger", this.hunger) + ", " +
                JSONUtils.fromValue("saturation", this.saturation) + ", " +
                JSONUtils.fromValue("expireDay", this.expireDay) + ", " +
                //JSONUtils.fromPotionEffectList("effects", this.effects) +
                "}"
    }

    companion object {
        const val ID: String = "consumable"
        val COMPONENT_KEY: NamespacedKey = HistoriaCore.getNamespacedKey(ID)

        fun fromStack(stack: ItemStack): ConsumableData? {
            return PDCUtils
                .getFromComplexContainer(stack, COMPONENT_KEY, DataType)
                .getOrNull()
        }
    }

    object DataType : PersistentDataType<PersistentDataContainer, ConsumableData> {

        override fun fromPrimitive(
            container: PersistentDataContainer,
            adapterContext: PersistentDataAdapterContext
        ): ConsumableData {
            val hunger = container.get(HUNGER_KEY, PersistentDataType.INTEGER)
                ?: 0
            val saturation = container.get(SATURATION_KEY, PersistentDataType.FLOAT)
                ?: 1f
            val expireDay = container.get(EXPIRE_KEY, PersistentDataType.INTEGER)
                ?: 0
            val effects = container.get(
                EFFECTS_KEY,
                PersistentDataType.LIST.listTypeFrom(CustomDataType.POTION_EFFECT)
            ) ?: mutableListOf()

            return ConsumableData(hunger, saturation, expireDay, effects)
        }

        override fun getComplexType(): Class<ConsumableData> = ConsumableData::class.java

        override fun getPrimitiveType(): Class<PersistentDataContainer> = PersistentDataContainer::class.java

        override fun toPrimitive(
            data: ConsumableData,
            adapterContext: PersistentDataAdapterContext
        ): PersistentDataContainer {
            val container = adapterContext.newPersistentDataContainer()

            container.set(HUNGER_KEY, PersistentDataType.INTEGER, data.hunger)
            container.set(SATURATION_KEY, PersistentDataType.FLOAT, data.saturation)
            container.set(EXPIRE_KEY, PersistentDataType.INTEGER, data.expireDay)
            container.set(
                EFFECTS_KEY,
                PersistentDataType.LIST.listTypeFrom(CustomDataType.POTION_EFFECT),
                data.effects
            )

            return container
        }

        private val HUNGER_KEY: NamespacedKey = HistoriaCore.getNamespacedKey("hunger")
        private val SATURATION_KEY: NamespacedKey = HistoriaCore.getNamespacedKey("saturation")
        private val EXPIRE_KEY: NamespacedKey = HistoriaCore.getNamespacedKey("expire_day")
        private val EFFECTS_KEY: NamespacedKey = HistoriaCore.getNamespacedKey("effects")
    }
}