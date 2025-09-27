package dev.boooiil.historia.core.expiry.item

import dev.boooiil.historia.core.items.component.ConsumableComponent
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

class ConsumableUpdater : Runnable {

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val view = player.openInventory
            updateConsumables(view.topInventory)
            updateConsumables(view.bottomInventory)
        }
    }

    fun updateConsumables(inventory: Inventory) {
        for (i in 0..<inventory.size) {
            val stack = inventory.getItem(i)
                ?: continue
            val updated = ConsumableComponent.update(stack)
            inventory.setItem(i, updated)
        }
    }
}
