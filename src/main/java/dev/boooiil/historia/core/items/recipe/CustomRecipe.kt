package dev.boooiil.historia.core.items.recipe

import dev.boooiil.historia.core.condition.Condition
import org.bukkit.NamespacedKey
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

interface CustomRecipe<C: Inventory> {
    val key: NamespacedKey
    val resultPreview: ItemStack
    val hasRandomResult: Boolean

    fun matches(inventory: C, ctx: Condition.Context = Condition.Context()): Boolean
    fun getInput(inventory: C): Array<ItemStack>
    fun getResult(inventory: C, ctx: Condition.Context = Condition.Context()): ItemStack
}