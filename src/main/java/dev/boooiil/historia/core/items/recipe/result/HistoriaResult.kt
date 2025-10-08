package dev.boooiil.historia.core.items.recipe.result

import dev.boooiil.historia.core.items.HistoriaItem
import dev.boooiil.historia.core.items.data.ModifierData
import dev.boooiil.historia.core.items.types.Quality
import dev.boooiil.historia.core.registry.RegistryHolder
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

class HistoriaResult(
    itemKey: NamespacedKey,
    val amount: Int,
) : Result {

    val item: HistoriaItem = RegistryHolder.ITEM_REGISTRY.get(itemKey)
        ?: throw IllegalArgumentException("Could not find item with key $itemKey")

    override val isStatic = false

    override fun preview(inputs: List<ItemStack>): ItemStack {
        val qualityModifier = inputs
            .mapNotNull { ModifierData.fromStack(it) }
            .mapNotNull { it.quality }
            .map { when(it) {
                Quality.POOR -> 0.0
                Quality.COMMON -> 0.5
                Quality.PERFECT -> 1.0
            } }
            .average()

        return item.createPreviewStack(amount, qualityModifier)
    }

    override fun get(inputs: List<ItemStack>): ItemStack {
        val qualityModifier = inputs
            .mapNotNull { ModifierData.fromStack(it) }
            .mapNotNull { it.quality }
            .map { when(it) {
                Quality.POOR -> 0.0
                Quality.COMMON -> 0.5
                Quality.PERFECT -> 1.0
            } }
            .average()

        return item.createItemStack(amount, qualityModifier)
    }
}