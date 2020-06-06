package dev.boooiil.historia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.boooiil.historia.expiry.ExpiryHandler;

public class InventoryClick implements Listener {

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        
        InventoryAction action = event.getAction();

        if (action.equals(InventoryAction.UNKNOWN)) { System.out.println("Action was unknown."); return; };
        if (action.equals(InventoryAction.DROP_ALL_CURSOR)) { System.out.println("Dropped all through cursor."); return; };

        event.setCurrentItem(ExpiryHandler.setExpiry(event.getCurrentItem(), event.getCurrentItem().getAmount()));

        ExpiryHandler.initiateExpiry((Player) event.getWhoClicked(), event.getCurrentItem(), event.getSlot());
        
    }
}