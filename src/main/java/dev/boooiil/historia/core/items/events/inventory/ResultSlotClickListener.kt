package dev.boooiil.historia.core.items.events.inventory

import dev.boooiil.historia.core.items.recipe.CustomRecipe
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import java.util.HashMap


object ResultSlotClickListener : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inventory = event.inventory as? CraftingInventory ?: return

        if (event.slotType != SlotType.RESULT) return

        val recipe = getRecipe(inventory) ?: return

        event.isCancelled = true
        val amount = takeResults(event, recipe)
        event.currentItem = ItemStack.empty()
        decrementStacks(event.view, amount)
    }

    fun takeResults(
        event: InventoryClickEvent,
        recipe: CustomRecipe<CraftingInventory>,
        ctx: CustomRecipe.Context = CustomRecipe.Context()
    ): Int {
        val craftingInv = event.inventory as? CraftingInventory ?: return 0
        val cursor = event.cursor

        val maxAmount = craftingInv.matrix
            .filterNotNull()
            .minOf { it.amount }

        if (event.isShiftClick) {
            val playerInv = event.whoClicked.inventory
            var added = 0
            if (recipe.hasRandomResult) {
                while (added < maxAmount && playerInv.firstEmpty() != -1) {
                    playerInv.addShiftClick(recipe.getResult(craftingInv))
                    added++
                }
            } else {
                while (added < maxAmount) {
                    val leftOver = playerInv.addShiftClick(recipe.getResult(craftingInv))
                    if (!leftOver.isEmpty()) break
                    added++
                }
            }
            return added
        }

        if (cursor.isEmpty) {
            event.setCursor(recipe.getResult(craftingInv))
            return 1
        }
        if (!recipe.hasRandomResult) {
            event.cursor.amount++
            return 1
        }
        return 0
    }

    fun PlayerInventory.addShiftClick(vararg items: ItemStack): HashMap<Int, ItemStack> {
        return this.addItem(*items)
    }

    fun getRecipe(inventory: CraftingInventory): CustomRecipe<CraftingInventory>? {
        return when {
            PrepareCraftListener.shaped.matches(inventory) -> PrepareCraftListener.shaped
            PrepareCraftListener.shapeless.matches(inventory) -> PrepareCraftListener.shapeless
            else -> null
        }
    }

    fun decrementStacks(view: InventoryView, amount: Int = 1) {
        for (i in 0 until view.countSlots()) {
            if (view.getSlotType(i) != SlotType.CRAFTING) continue
            val stack = view.getItem(i) ?: continue
            stack.amount -= amount
            view.setItem(i, stack)
        }
    }
}