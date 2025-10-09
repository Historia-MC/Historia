package dev.boooiil.historia.core.items.recipe.ingredient

import dev.boooiil.historia.core.items.HistoriaItemData
import dev.boooiil.historia.core.registry.RegistryHolder
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice

class TypeIngredient(
    val typeKey: NamespacedKey,
) : Ingredient {

    override fun matches(stack: ItemStack): Boolean = stack.itemId == typeKey

    override fun display(): RecipeChoice {
        val historiaItem = RegistryHolder.ITEM_REGISTRY.get(typeKey)
        if (historiaItem != null) {
            return RecipeChoice.ExactChoice(historiaItem.createItemStack())
        }
        val itemType = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM).get(typeKey)
        if (itemType != null) {
            return RecipeChoice.ExactChoice(itemType.createItemStack())
        }
        throw IllegalStateException("TypeIngredient: item key '$typeKey' not found in historia or vanilla registry")
    }

    private val ItemStack.itemId: NamespacedKey get() {
        val hData = HistoriaItemData.fromStack(this)
        return hData?.itemId ?: this.type.key
    }
}