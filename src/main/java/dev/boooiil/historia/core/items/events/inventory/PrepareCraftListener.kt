package dev.boooiil.historia.core.items.events.inventory

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.items.recipe.CustomShapedRecipe
import dev.boooiil.historia.core.items.recipe.CustomShapelessRecipe
import dev.boooiil.historia.core.items.recipe.Ingredient
import dev.boooiil.historia.core.items.recipe.TypeIngredient
import dev.boooiil.historia.core.items.recipe.withQuality
import dev.boooiil.historia.core.items.types.Qualities
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack

object PrepareCraftListener : Listener {

    val copperIngredient = TypeIngredient(HistoriaCore.getNamespacedKey("light_copper_ingot"))
    val emeraldIngredient = TypeIngredient(NamespacedKey.minecraft("emerald"))

    val pattern: Array<Array<Ingredient>> = arrayOf(
        arrayOf(Ingredient.Empty, copperIngredient, Ingredient.Empty),
        arrayOf(copperIngredient, emeraldIngredient, copperIngredient),
        arrayOf(Ingredient.Empty, copperIngredient, Ingredient.Empty),
    )

    val shaped = CustomShapedRecipe(
        HistoriaCore.getNamespacedKey("shaped"),
        pattern,
        ItemStack(Material.BARRIER),
    ).withQuality { when (it) {
        Qualities.POOR -> ItemStack(Material.DIRT)
        Qualities.COMMON -> ItemStack(Material.IRON_INGOT)
        Qualities.PERFECT -> ItemStack(Material.DIAMOND)
    } }

    val shapeless = CustomShapelessRecipe(
        HistoriaCore.getNamespacedKey("shapeless"),
        arrayOf(copperIngredient, copperIngredient, copperIngredient, copperIngredient, emeraldIngredient),
        ItemStack(Material.PUFFERFISH),
    )

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val inventory = event.inventory

        if (shaped.matches(inventory)) {
            inventory.result = shaped.resultPreview
            return
        }
        if (shapeless.matches(inventory)) {
            inventory.result = shaped.resultPreview
            return
        }
    }
}