package dev.boooiil.historia.core.items.recipe.ingredient

import dev.boooiil.historia.core.items.HistoriaItemData
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

class TypeIngredient(
    val typeKey: NamespacedKey,
) : Ingredient {

    override fun matches(stack: ItemStack): Boolean = stack.itemId == typeKey
    override val exampleStacks: List<ItemStack> = listOf()

    private val ItemStack.itemId: NamespacedKey get() {
        val hData = HistoriaItemData.fromStack(this)
        return hData?.itemId ?: this.type.key
    }
}