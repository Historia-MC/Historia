package dev.boooiil.historia.core.items.recipe

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.condition.Condition
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack

class CustomShapelessRecipe(
    override val key: NamespacedKey,
    val ingredients: Array<Ingredient>,
    val result: ItemStack,
) : CustomRecipe<CraftingInventory> {

    override val resultPreview = result
    override val hasRandomResult = false

    override fun matches(inventory: CraftingInventory, ctx: Condition.Context): Boolean {
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

    override fun getResult(inventory: CraftingInventory, ctx: Condition.Context): ItemStack = result.clone()

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

    object Type : RecipeType<CustomShapelessRecipe> {
        override val key = "shapeless"

        override fun fromConfig(
            recipeKey: String,
            section: ConfigurationSection
        ): CustomShapelessRecipe {
            val ingredientStrings = section.getStringList(INGREDIENTS_KEY)

            require(ingredientStrings.isNotEmpty()) { "Shapeless recipe must have at least one ingredient" }
            require(ingredientStrings.size <= 9) { "Shapeless recipe must have at most 9 ingredients" }

            val ingredients = ingredientStrings.map { parseIngredient(it) }.toTypedArray<Ingredient>()

            return CustomShapelessRecipe(
                HistoriaCore.getNamespacedKey(recipeKey),
                ingredients,
                getResult(section),
            )
        }

        private const val INGREDIENTS_KEY = "ingredients"
    }
}