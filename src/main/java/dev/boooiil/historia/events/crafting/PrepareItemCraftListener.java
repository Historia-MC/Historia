package dev.boooiil.historia.events.crafting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.handlers.crafting.CraftingItemManager;
import dev.boooiil.historia.handlers.crafting.CraftingTableInspector;
import dev.boooiil.historia.util.Logging;

public class PrepareItemCraftListener implements Listener {

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {

        CraftingTableInspector inspector = new CraftingTableInspector(event.getInventory().getContents());
        ItemStack resultItem;

        CraftingItemManager cim = new CraftingItemManager(inspector);

        if (cim.getResult() != null) resultItem = cim.getResult();
        else resultItem = null;

        if (resultItem != null) Logging.debugToConsole("Result: " + resultItem.getItemMeta().getLocalizedName() + " " + resultItem.getAmount());
        else Logging.debugToConsole("Result: null");



        event.getInventory().setResult(resultItem);

    }

}
