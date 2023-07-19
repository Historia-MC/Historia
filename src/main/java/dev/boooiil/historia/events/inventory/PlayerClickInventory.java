package dev.boooiil.historia.events.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.boooiil.historia.handlers.inventory.InventorySwapWithCursor;

public class PlayerClickInventory implements Listener {

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {

        if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
            InventorySwapWithCursor inventorySwapWithCursor = new InventorySwapWithCursor(event);
            inventorySwapWithCursor.doClick();
        }

    }
    
}
