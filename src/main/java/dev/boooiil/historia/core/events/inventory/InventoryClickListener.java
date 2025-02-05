package dev.boooiil.historia.core.events.inventory;

import dev.boooiil.historia.core.handlers.inventory.InventoryClickHandler;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        CoreLogger.debugToConsole("Player interacted with their inventory");
        CoreLogger.debugToConsole("Action: " + event.getAction());

        if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {

            CoreLogger.debugToConsole("Player clicked in their inventory with cursor");

            InventoryClickHandler inventorySwapWithCursor = new InventoryClickHandler(event);
            inventorySwapWithCursor.doInventoryClick();
        }

    }

}
