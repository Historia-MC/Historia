package dev.boooiil.historia.core.items.recipe

import org.bukkit.inventory.Recipe

interface RecipeBookDisplayable {
    fun display(keyPrefix: String = ""): Recipe
}