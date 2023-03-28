package dev.boooiil.historia.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.util.CraftingTableManager;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerCraft implements Listener {
    
    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {

        Player player = (Player) event.getViewers().get(0);
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), false);

        Logging.infoToConsole(player.getName());

        CraftingTableManager.craftItem(event.getInventory());

    }

}
