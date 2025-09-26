package dev.boooiil.historia.core.expiry.util

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object PlayerInteractUtil {
    @Deprecated("")
    fun replaceUsedItem(event: PlayerInteractEvent, newItem: ItemStack) {
        val player = event.player
        val hand = event.hand ?: return
        replaceUsedItem(player, hand, newItem)
    }

    fun replaceUsedItem(player: Player, hand: EquipmentSlot, newItem: ItemStack) {
        val bucket = player.inventory.getItem(hand)
        val inventory = player.inventory

        //Creative mode players should not get duplicate items
        if (player.gameMode == GameMode.CREATIVE && inventory.contains(newItem)) {
            return
        }

        //Remove old item
        if (player.gameMode != GameMode.CREATIVE) {
            inventory.setItem(hand, bucket.subtract())
        }

        //Add new items
        if (inventory.getItem(hand).type == Material.AIR) {
            inventory.setItem(hand, newItem)
        } else {
            inventory.addItem(newItem)
        }
    }
}
