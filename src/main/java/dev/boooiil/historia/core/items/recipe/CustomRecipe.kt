package dev.boooiil.historia.core.items.recipe

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

interface CustomRecipe<C: Inventory> {
    val key: NamespacedKey
    val resultPreview: ItemStack
    val hasRandomResult: Boolean

    fun matches(inventory: C, ctx: Context = Context()): Boolean
    fun getInput(inventory: C): Array<ItemStack>
    fun getResult(inventory: C, ctx: Context = Context()): ItemStack

    data class Context(
        val player: Player? = null,
    )
}