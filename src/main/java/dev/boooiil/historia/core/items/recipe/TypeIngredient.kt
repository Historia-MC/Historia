package dev.boooiil.historia.core.items.recipe

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

class TypeIngredient(
    val typeKey: NamespacedKey,
) : Ingredient {

    override fun matches(stack: ItemStack): Boolean = stack.itemId == typeKey
    override val exampleStacks: List<ItemStack> = listOf()
}