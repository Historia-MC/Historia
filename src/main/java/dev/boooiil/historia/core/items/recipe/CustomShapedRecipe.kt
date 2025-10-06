package dev.boooiil.historia.core.items.recipe

import org.bukkit.NamespacedKey
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack

class CustomShapedRecipe(
    override val key: NamespacedKey,
    pattern: Array<Array<Ingredient>>,
    val result: ItemStack,
) : CustomRecipe<CraftingInventory> {

    val pattern = pattern.trim { !it.isEmpty }

    override val resultPreview = result

    override fun matches(inventory: CraftingInventory, ctx: CustomRecipe.Context): Boolean {
        val matrix = inventory.matrix
        val grid = gridOf(matrix).trim { !it.isEmpty }

        if (grid.isEmpty() || pattern.isEmpty()) return false
        if (grid.size != pattern.size) return false
        if (grid[0].size != pattern[0].size) return false

        return pattern.matches(grid) || pattern.matches(grid.flip())
    }

    override fun getInput(inventory: CraftingInventory): Array<ItemStack> {
        return inventory.matrix.filterNotNull().toTypedArray()
    }

    override fun getResult(inventory: CraftingInventory, ctx: CustomRecipe.Context): ItemStack = result.clone()
}

// HELPER FUNCTIONS
private fun gridOf(matrix: Array<ItemStack?>): Array<Array<ItemStack>> {
    val notNull = matrix
        .map { it ?: ItemStack.empty() }

    return when(notNull.size) {
        9 -> arrayOf(
            arrayOf(notNull[0], notNull[1], notNull[2]),
            arrayOf(notNull[3], notNull[4], notNull[5]),
            arrayOf(notNull[6], notNull[7], notNull[8]),
        )
        4 -> arrayOf(
            arrayOf(notNull[0], notNull[1]),
            arrayOf(notNull[2], notNull[3]),
        )
        else -> throw IllegalArgumentException("crafting matrix must be size 4 or 9")
    }
}

/** Trims rows and columns where all values do not fulfill the predicate */
private inline fun <reified T> Array<Array<T>>.trim(predicate: (T) -> Boolean): Array<Array<T>> {
    val nonEmptyRows = this.filter { row -> row.any { predicate(it) } }
    if (nonEmptyRows.isEmpty()) return arrayOf()

    val firstCol = nonEmptyRows[0].indices.firstOrNull { col -> nonEmptyRows.any { row -> predicate(row[col]) } } ?: 0
    val lastCol = nonEmptyRows[0].indices.lastOrNull { col -> nonEmptyRows.any { row -> predicate(row[col]) } } ?: nonEmptyRows[0].lastIndex

    val trimmed = nonEmptyRows.map { row ->
        row.sliceArray(firstCol..lastCol)
    }.toTypedArray()

    return trimmed
}

private inline fun <reified T> Array<Array<T>>.flip(): Array<Array<T>> = this.map { it.reversedArray() }.toTypedArray()

private fun Array<Array<Ingredient>>.matches(grid: Array<Array<ItemStack>>): Boolean {
    for (row in 0 until grid.size) {
        for (col in 0 until grid[row].size) {

            val stack = grid[row][col]
            val ingredient = this[row][col]
            if (!ingredient.matches(stack))
                return false
        }
    }
    return true
}