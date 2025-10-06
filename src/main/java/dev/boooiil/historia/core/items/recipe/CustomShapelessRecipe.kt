package dev.boooiil.historia.core.items.recipe

import org.bukkit.NamespacedKey
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack

class CustomShapelessRecipe(
    override val key: NamespacedKey,
    val ingredients: Array<Ingredient>,
    val result: ItemStack,
) : CustomRecipe<CraftingInventory> {

    override val resultPreview = result

    override fun matches(inventory: CraftingInventory, ctx: CustomRecipe.Context): Boolean {
        val stacks = inventory.matrix.filterNotNull().toTypedArray()

        if (stacks.size != ingredients.size) return false

        val matches = mutableMapOf<Ingredient, List<Int>>()
        for (ingredient in ingredients) {
            val matchingIndices = stacks
                .mapIndexedNotNull { index, stack -> if (ingredient.matches(stack)) index else null }
            if (matchingIndices.isEmpty()) return false
            matches[ingredient] = matchingIndices
        }

        return canMatchAll(matches, ingredients.toList())
    }

    override fun getInput(inventory: CraftingInventory): Array<ItemStack> {
        return inventory.matrix.filterNotNull().toTypedArray()
    }

    override fun getResult(inventory: CraftingInventory, ctx: CustomRecipe.Context): ItemStack = result.clone()

    private fun canMatchAll(
        possibleMatches: Map<Ingredient, List<Int>>,
        remainingIngredients: List<Ingredient>,
        usedIndices: MutableSet<Int> = mutableSetOf()
    ): Boolean {
        if (remainingIngredients.isEmpty()) return true

        val ingredient = remainingIngredients.first()
        val indices = possibleMatches[ingredient] ?: return false

        for (index in indices) {
            if (index in usedIndices) continue

            usedIndices.add(index)
            if (canMatchAll(possibleMatches, remainingIngredients.drop(1), usedIndices))
                return true

            usedIndices.remove(index)
        }
        return false
    }
}