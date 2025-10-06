package dev.boooiil.historia.core.items.recipe

import dev.boooiil.historia.core.items.data.ModifierData
import dev.boooiil.historia.core.items.types.Qualities
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class RecipeWithQuality<C: Inventory>(
    val base: CustomRecipe<C>,
    val resultFactory: (Qualities) -> ItemStack,
) : CustomRecipe<C> by base {

    // TODO currently takes random quality from input items, calculation should be changed
    override fun getResult(inventory: C, ctx: CustomRecipe.Context): ItemStack {
        val quality = base.getInput(inventory)
            .map { ModifierData.fromStack(it) }
            .mapNotNull { it.quality }
            .random()
        return resultFactory(quality)
    }
}

fun <C : Inventory> CustomRecipe<C>.withQuality(
    factory: (Qualities) -> ItemStack
): CustomRecipe<C> = RecipeWithQuality(this, factory)