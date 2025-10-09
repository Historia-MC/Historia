package dev.boooiil.historia.core.items.events.inventory

import dev.boooiil.historia.core.items.recipe.CustomRecipe
import dev.boooiil.historia.core.registry.RegistryHolder
import org.bukkit.Keyed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack

object PrepareCraftListener : Listener {

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val inventory = event.inventory

        val recipe = RegistryHolder.RECIPE_REGISTRY.values
            .filterIsInstance<CustomRecipe<CraftingInventory>>()
            .firstOrNull { it.matches(inventory) }

        if (recipe != null) {
            inventory.result = recipe.result.preview(inventory.matrix.filterNotNull().toList())
            return
        }

        val vanillaRecipe = event.recipe ?: return
        val key = (vanillaRecipe as? Keyed)?.key ?: return

        if (key.key.startsWith("display_")) {
            inventory.result = ItemStack.empty()
        }
    }
}