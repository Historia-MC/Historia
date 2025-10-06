package dev.boooiil.historia.core.items.events.inventory

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.items.recipe.CustomShapedRecipe
import dev.boooiil.historia.core.items.recipe.CustomShapelessRecipe
import dev.boooiil.historia.core.items.recipe.Ingredient
import dev.boooiil.historia.core.items.recipe.TypeIngredient
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack

class PrepareCraftListener : Listener {

    val saltIngredient = TypeIngredient(HistoriaCore.getNamespacedKey("salt"))
    val emeraldIngredient = TypeIngredient(NamespacedKey.minecraft("emerald"))

    val pattern: Array<Array<Ingredient>> = arrayOf(
        arrayOf(Ingredient.Empty, saltIngredient, Ingredient.Empty),
        arrayOf(saltIngredient, emeraldIngredient, saltIngredient),
        arrayOf(Ingredient.Empty, saltIngredient, Ingredient.Empty),
    )

    val shaped = CustomShapedRecipe(
        HistoriaCore.getNamespacedKey("shaped"),
        pattern,
        ItemStack(Material.PUMPKIN),
    )

    val shapeless = CustomShapelessRecipe(
        HistoriaCore.getNamespacedKey("shapeless"),
        arrayOf(saltIngredient, saltIngredient, saltIngredient, saltIngredient, emeraldIngredient),
        ItemStack(Material.PUFFERFISH),
    )

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val inventory = event.inventory

        if (shaped.matches(inventory)) {
            inventory.result = shaped.result
            return
        }
        if (shapeless.matches(inventory)) {
            inventory.result = shapeless.result
            return
        }
    }
}