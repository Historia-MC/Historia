package dev.boooiil.historia.core.expiry.listeners.inventory

import dev.boooiil.historia.core.expiry.item.ConsumableComponent
import dev.boooiil.historia.core.expiry.item.ConsumableData
import dev.boooiil.historia.core.expiry.item.getCustomData
import dev.boooiil.historia.core.util.CoreLogger
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent

class InventoryOpenListener : Listener {
    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        val inventory = event.inventory
        for (slot in 0..<inventory.size) {
            CoreLogger.infoToConsole("Opened inventory")

            var stack = inventory.getItem(slot) ?: continue

            CoreLogger.infoToConsole("Found item: $stack")

            val consumable = stack.getCustomData(ConsumableData.DataType) ?: return
            if (!consumable.canExpire) continue

            CoreLogger.infoToConsole("Found consumable: $consumable")

            // Update item
            stack = ConsumableComponent.update(stack)

            inventory.setItem(slot, stack)
        }
    }
}
