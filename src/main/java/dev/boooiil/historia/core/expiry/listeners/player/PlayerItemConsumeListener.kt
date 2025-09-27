package dev.boooiil.historia.core.expiry.listeners.player

import dev.boooiil.historia.core.expiry.item.consume
import dev.boooiil.historia.core.expiry.item.getCustomData
import dev.boooiil.historia.core.items.data.ConsumableData
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent

class PlayerItemConsumeListener : Listener {
    @EventHandler
    fun onPlayerItemConsume(event: PlayerItemConsumeEvent) {
        val player = event.player
        val stack = event.item
        val consumable = stack.getCustomData(ConsumableData.DataType) ?: return

        player.consume(consumable)
        player.sendMessage("Consumed ${consumable.toJSON()}")

        if (player.gameMode != GameMode.CREATIVE) {
            player.inventory.setItem(event.hand, stack.subtract(1))
        }

        event.isCancelled = true
    }
}
