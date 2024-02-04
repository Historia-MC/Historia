package dev.boooiil.historia.core.events.inventory;

import dev.boooiil.historia.core.handlers.inventory.InventoryClickHandler;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Logging.debugToConsole("Player interacted with their inventory");
        Logging.debugToConsole("Action: " + event.getAction());

        if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {

            Logging.debugToConsole("Player clicked in their inventory with cursor");

            InventoryClickHandler inventorySwapWithCursor = new InventoryClickHandler(event);
            inventorySwapWithCursor.doClick();
        }

    }

}
