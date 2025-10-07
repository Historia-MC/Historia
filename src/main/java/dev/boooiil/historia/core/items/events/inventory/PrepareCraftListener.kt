package dev.boooiil.historia.core.items.events.inventory

import dev.boooiil.historia.core.items.recipe.CustomRecipe
import dev.boooiil.historia.core.registry.RegistryHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.CraftingInventory

object PrepareCraftListener : Listener {

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val inventory = event.inventory

        val recipe = RegistryHolder.RECIPE_REGISTRY.values
            .filterIsInstance<CustomRecipe<CraftingInventory>>()
            .firstOrNull { it.matches(inventory) }
            ?: return

        inventory.result = recipe.resultPreview
    }
}