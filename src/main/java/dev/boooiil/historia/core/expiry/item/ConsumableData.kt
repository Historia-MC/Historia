package dev.boooiil.historia.core.expiry.item

import dev.boooiil.historia.core.items.ItemData
import dev.boooiil.historia.core.time.GameCalendar
import dev.boooiil.historia.core.time.GameDate
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.PDCUtils
import dev.boooiil.historia.expiry.HistoriaExpiry
import dev.boooiil.historia.core.expiry.util.CustomDataType
import dev.boooiil.historia.core.expiry.util.Logging
import dev.boooiil.historia.items.Main
import dev.boooiil.historia.items.util.KyoriUtils
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect

class ConsumableData(
    var hunger: Int,
    var saturation: Float,
    var expireEpoch: Long,
    val effects: MutableList<PotionEffect>
) : ItemData, CustomDataComponentType<PersistentDataContainer, ConsumableData> by DataType {

    constructor(hunger: Int, saturation: Float, expirationDate: GameDate, effects: MutableList<PotionEffect>) : this(
        hunger,
        saturation,
        GameCalendar.epochOf(expirationDate),
        effects
    )

    override fun apply(stack: ItemStack) {
        PDCUtils.setInComplexContainer<PersistentDataContainer, ConsumableData>(stack, COMPONENT_KEY, DataType, this)
        applyLore(stack)
    }

    private fun applyLore(stack: ItemStack) {
        val configId = PDCUtils.getFromContainer(
            stack,
            Main.getNamespacedKey("config-id"),
            PersistentDataType.STRING
        ).orElse("")

        val lore = stack.lore()
        if (lore.isNullOrEmpty()) {
            Logging.debugToConsole(configId, "has no lore, skipping placeholder.")
            return
        }

        val expiration = when {
            !this.canExpire -> "Does not expire"
            this.isExpired -> "Expired"
            else -> "Expires on ${GameCalendar.dateOf(this.expireEpoch)}"
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

    val canExpire: Boolean = expireEpoch >= 0
    val isExpired: Boolean get() = canExpire && System.currentTimeMillis() > expireEpoch

    override fun toJSON(): String {
        return "{" +
                JSONUtils.fromValue("hunger", this.hunger) + ", " +
                JSONUtils.fromValue("saturation", this.saturation) + ", " +
                JSONUtils.fromValue("expireEpoch", this.expireEpoch) + ", " +
                //JSONUtils.fromPotionEffectList("effects", this.effects) +
                "}"
    }

    object DataType : CustomDataComponentType<PersistentDataContainer, ConsumableData> {

        override val key = HistoriaExpiry.getNamespacedKey(ID)

        override fun fromPrimitive(
            container: PersistentDataContainer,
            adapterContext: PersistentDataAdapterContext
        ): ConsumableData {
            val hunger = container.get(HUNGER_KEY, PersistentDataType.INTEGER)
                ?: 0
            val saturation = container.get(SATURATION_KEY, PersistentDataType.FLOAT)
                ?: 1f
            val expireEpoch = container.get(EXPIRE_KEY, PersistentDataType.LONG)
                ?: 0
            val effects = container.get(EFFECTS_KEY,
                PersistentDataType.LIST.listTypeFrom(CustomDataType.POTION_EFFECT)
            ) ?: mutableListOf()

            return ConsumableData(hunger, saturation, expireEpoch, effects)
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
            container.set(EXPIRE_KEY, PersistentDataType.LONG, data.expireEpoch)
            container.set(EFFECTS_KEY,
                PersistentDataType.LIST.listTypeFrom(CustomDataType.POTION_EFFECT),
                data.effects
            )

            return container
        }

        private val HUNGER_KEY: NamespacedKey = HistoriaExpiry.getNamespacedKey("hunger")
        private val SATURATION_KEY: NamespacedKey = HistoriaExpiry.getNamespacedKey("saturation")
        private val EXPIRE_KEY: NamespacedKey = HistoriaExpiry.getNamespacedKey("expire_epoch")
        private val EFFECTS_KEY: NamespacedKey = HistoriaExpiry.getNamespacedKey("effects")
    }

    companion object {
        const val ID: String = "consumable"
        private val COMPONENT_KEY: NamespacedKey = HistoriaExpiry.getNamespacedKey(ID)
    }
}
