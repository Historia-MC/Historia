package dev.boooiil.historia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.expiry.ExpiryManager;

public class PlayerItemHeld implements Listener {

    @EventHandler
    public void onPlayerItemHeldSwitch(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
        Integer newItemSlot = event.getNewSlot();
        Integer oldItemSlot = event.getPreviousSlot();

        if (previousItem != null) ExpiryManager.initiate(previousItem, player, oldItemSlot);
        if (newItem != null) ExpiryManager.initiate(newItem, player, newItemSlot);

    }
}