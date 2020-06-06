package dev.boooiil.historia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.expiry.ExpiryHandler;

public class PlayerItemHeld implements Listener {

    @EventHandler
    public void onPlayerItemHeldSwitch(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
        Integer newItemSlot = event.getNewSlot();
        Integer oldItemSlot = event.getPreviousSlot();

        if (previousItem != null) ExpiryHandler.initiateExpiry(player, previousItem, oldItemSlot);
        if (newItem != null) ExpiryHandler.initiateExpiry(player, newItem, newItemSlot);

    }
}