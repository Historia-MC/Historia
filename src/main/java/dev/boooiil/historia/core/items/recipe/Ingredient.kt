package dev.boooiil.historia.core.items.recipe

import org.bukkit.inventory.ItemStack

interface Ingredient {
    fun matches(stack: ItemStack): Boolean
    val exampleStacks: List<ItemStack>

    val isEmpty: Boolean
        get() = this == Empty

    object Empty : Ingredient {
        override fun matches(stack: ItemStack): Boolean = stack.isEmpty
        override val exampleStacks: List<ItemStack> = listOf(ItemStack.empty())
    }
}