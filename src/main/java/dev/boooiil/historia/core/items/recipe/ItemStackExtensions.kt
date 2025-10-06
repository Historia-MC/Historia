package dev.boooiil.historia.core.items.recipe

import dev.boooiil.historia.core.items.HistoriaItemData
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

val ItemStack.itemId: NamespacedKey get() {
    val hData = HistoriaItemData.fromStack(this)
    return hData?.itemId ?: this.type.key
}