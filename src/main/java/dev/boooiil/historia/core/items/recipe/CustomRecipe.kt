package dev.boooiil.historia.core.items.recipe

import dev.boooiil.historia.core.condition.Condition
import dev.boooiil.historia.core.items.recipe.result.Result
import org.bukkit.NamespacedKey
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

interface CustomRecipe<C: Inventory> {
    val key: NamespacedKey
    val result: Result

    fun matches(inventory: C, ctx: Condition.Context = Condition.Context()): Boolean
    fun getResultStack(inventory: C, ctx: Condition.Context = Condition.Context()): ItemStack
}