package dev.boooiil.historia.core.items.recipe.ingredient

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice

interface Ingredient {
    fun matches(stack: ItemStack): Boolean
    fun display(): RecipeChoice

    val isEmpty: Boolean
        get() = this == Empty

    object Empty : Ingredient {
        override fun matches(stack: ItemStack): Boolean = stack.isEmpty
        override fun display(): RecipeChoice = RecipeChoice.MaterialChoice(Material.AIR)
    }
}