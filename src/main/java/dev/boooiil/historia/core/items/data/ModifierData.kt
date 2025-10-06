package dev.boooiil.historia.core.items.data

import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.items.ItemData
import dev.boooiil.historia.core.items.types.Qualities
import dev.boooiil.historia.core.items.types.Weights
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.KyoriUtils
import dev.boooiil.historia.core.util.PDCUtils
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

/**
 * @param weight private String id;
 */
@JvmRecord
data class ModifierData(
    val weight: Weights,
    val quality: Qualities?
) : ItemData {

    override fun apply(stack: ItemStack) {
        writeData(stack)
        writeLore(stack)
    }

    private fun writeData(stack: ItemStack) {
        PDCUtils.setInComplexContainer(stack, KEY, DataType, this)
    }

    private fun writeLore(stack: ItemStack) {
        val configId = PDCUtils.getFromContainer<String>(
            stack,
            getNamespacedKey("item-id"), PersistentDataType.STRING
        ).orElse("")

        val meta = stack.itemMeta

        if (!meta.hasLore() || meta.lore()!!.isEmpty()) {
            CoreLogger.debugToConsole(configId, "has no lore, skipping placeholder.")
            return
        }

        val lore = meta.lore()
        val nLore: MutableList<Component?> = ArrayList()

        for (component in lore!!) {
            if (KyoriUtils.contains(component, "<modifier-weight>")) {
                CoreLogger.debugToConsole(configId, "has modifier weight placeholder.")

                nLore.add(KyoriUtils.replaceComponent(component, "modifier-weight", this.weight.displayName))

                continue
            }

            if (KyoriUtils.contains(component, "<modifier-quality>") && quality != null) {
                CoreLogger.debugToConsole(configId, "has modifier quality placeholder.")

                nLore.add(KyoriUtils.replaceComponent(component, "modifier-quality", this.quality.displayName))

                continue
            }

            // TODO temp test
            if (quality != null) {
                nLore.add(Component.text("Quality: ${this.quality.displayName}"))
            }

            nLore.add(component)
        }

        meta.lore(nLore)
        stack.setItemMeta(meta)
    }

    fun id(): String {
        throw UnsupportedOperationException("Not implemented.")
    }

    override fun toString(): String {
        val sb = "ModifierData" +
                toJSON()

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromValue("weight", weight.lowercase()) +
                (quality?.let { ", " + JSONUtils.fromValue("quality", it.lowercase()) } ?: "") +
                "}"

        return sb
    }

    companion object {
        val KEY: NamespacedKey = getNamespacedKey("modifier")

        fun fromStack(stack: ItemStack): ModifierData {
            return PDCUtils
                .getFromComplexContainer(stack, KEY, DataType)
                .orElse(ModifierData(Weights.LIGHT, Qualities.POOR))
        }
    }

    object DataType : PersistentDataType<PersistentDataContainer, ModifierData> {

        override fun fromPrimitive(
            container: PersistentDataContainer,
            adapterContext: PersistentDataAdapterContext
        ): ModifierData {
            val weight = Weights
                .fromString(container.get<String, String>(WEIGHT_KEY, PersistentDataType.STRING))
            val quality = Qualities
                .fromString(container.get<String, String>(QUALITY_KEY, PersistentDataType.STRING))

            return ModifierData(weight, quality)
        }

        override fun getComplexType(): Class<ModifierData> {
            return ModifierData::class.java
        }

        override fun getPrimitiveType(): Class<PersistentDataContainer> {
            return PersistentDataContainer::class.java
        }

        override fun toPrimitive(
            data: ModifierData,
            adapterContext: PersistentDataAdapterContext
        ): PersistentDataContainer {
            val container = adapterContext.newPersistentDataContainer()

            container.set(WEIGHT_KEY, PersistentDataType.STRING, data.weight.lowercase())
            if (data.quality != null) {
                container.set(QUALITY_KEY, PersistentDataType.STRING, data.quality.lowercase())
            }

            return container
        }

        private val WEIGHT_KEY = getNamespacedKey("weight")
        private val QUALITY_KEY = getNamespacedKey("quality")
    }
}
