package dev.boooiil.historia.core.items.recipe.result

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

interface Result {
    val preview: ItemStack
    val isStatic: Boolean

    fun get(inputs: List<ItemStack>): ItemStack

    companion object {
        fun fromConfig(section: ConfigurationSection): Result {
            TODO()
        }
    }
}