package dev.boooiil.historia;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.boooiil.historia.expiry.ExpiryManager;

public class InventoryClick implements Listener {

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        
        InventoryAction action = event.getAction();

        if (action.equals(InventoryAction.UNKNOWN)) { System.out.println("Action was unknown."); return; };
        if (action.equals(InventoryAction.DROP_ALL_CURSOR)) { System.out.println("Dropped all through cursor."); return; };

        ExpiryManager.initiate(event.getCurrentItem(), (HumanEntity) event.getWhoClicked(), event.getSlot());
        
    }
}