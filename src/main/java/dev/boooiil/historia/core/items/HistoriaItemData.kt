package dev.boooiil.historia.core.items

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONSerializable
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.PDCUtils
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.jvm.optionals.getOrNull

class HistoriaItemData(
    val itemId: NamespacedKey,
    private val itemData: MutableList<NamespacedKey>,
    val stack: ItemStack
) : JSONSerializable {

    fun hasData(key: NamespacedKey): Boolean {
        return itemData.contains(key)
    }

    fun getHistoriaItem(): HistoriaItem {
        return RegistryHolder.ITEM_REGISTRY.get(itemId)!!
    }

    fun <C : Any, T : ItemData> getData(
        key: NamespacedKey,
        type: PersistentDataType<C, T>
    ): T? {
        if (!hasData(key)) {
            CoreLogger.debugToConsole("No data found for key $key, creating one...")
            val sRegkey = key.key
            val regKey = NamespacedKey(key.namespace, sRegkey)
            val itemData: Any? = RegistryHolder.COMPONENT_REGISTRY.get(regKey)!!.data
            return type.getComplexType().cast(itemData)
        }

        return stack.itemMeta.persistentDataContainer.get(key, type)
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromValue("id", itemId) + "," +
                JSONUtils.fromList("item_data", itemData) + "," +
                JSONUtils.fromValue("item_stack", stack.toString()) +
                "}"

        return sb
    }

    companion object {
        @JvmStatic
        fun fromStack(stack: ItemStack): HistoriaItemData? {
            if (stack.itemMeta == null) {
                return null
            }

            val container = stack.itemMeta.persistentDataContainer
            val itemData = mutableListOf<NamespacedKey>()

            for (key in container.keys) {
                val sKey = key.key.lowercase(Locale.getDefault())

                if (sKey != "item-id") {
                    itemData.add(key)
                }
            }

            val id = PDCUtils.getFromContainer(stack, HistoriaCore.getNamespacedKey("item-id"), PersistentDataType.STRING).getOrNull()
                ?: return null

            return HistoriaItemData(HistoriaCore.getNamespacedKey(id), itemData, stack)
        }
    }
}
