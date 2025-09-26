package dev.boooiil.historia.core.expiry.item

import dev.boooiil.historia.core.items.ItemData
import dev.boooiil.historia.core.util.PDCUtils
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

interface CustomDataComponentType<P : Any, C : ItemData> : PersistentDataType<P, C> {
    val key: NamespacedKey
}

fun <C : ItemData> ItemStack.getCustomData(type: CustomDataComponentType<*, C>): C? {
    return PDCUtils.getFromComplexContainer(
        this,
        type.key,
        type
    ).orElse(null)
}