package dev.boooiil.historia.core.items.recipe.result

import org.bukkit.inventory.ItemStack

interface Result {
    val preview: ItemStack

    fun get(inputs: List<ItemStack>): ItemStack
}