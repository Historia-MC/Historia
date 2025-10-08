package dev.boooiil.historia.core.items.recipe.result

import org.bukkit.inventory.ItemStack

class SimpleResult(
    val stack: ItemStack,
) : Result {
    override val preview = stack.clone()
    override val isStatic = true

    override fun get(inputs: List<ItemStack>): ItemStack {
        return stack.clone()
    }
}