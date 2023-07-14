package dev.boooiil.historia.events.crafting;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import dev.boooiil.historia.handlers.crafting.CustomCraftingManager;
import dev.boooiil.historia.util.Logging;

public class PlayerCraft implements Listener {

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {

        Player player = (Player) event.getViewers().get(0);

        Logging.infoToConsole(player.getName());

        CustomCraftingManager ccm = new CustomCraftingManager(event.getInventory());

        event.getInventory().setResult(ccm.getResult());

    }

}
