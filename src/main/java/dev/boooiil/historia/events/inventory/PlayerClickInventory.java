package dev.boooiil.historia.events.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.boooiil.historia.handlers.inventory.InventorySwapWithCursor;
import dev.boooiil.historia.util.Logging;

public class PlayerClickInventory implements Listener {

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {

        Logging.debugToConsole("Player interacted with their inventory");
        Logging.debugToConsole("Action: " + event.getAction().toString());

        if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
            
            Logging.debugToConsole("Player clicked in their inventory with cursor");

            InventorySwapWithCursor inventorySwapWithCursor = new InventorySwapWithCursor(event);
            inventorySwapWithCursor.doClick();
        }

    }
    
}
