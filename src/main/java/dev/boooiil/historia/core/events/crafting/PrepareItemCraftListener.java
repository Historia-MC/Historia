package dev.boooiil.historia.core.events.crafting;

import dev.boooiil.historia.core.handlers.crafting.CraftingItemManager;
import dev.boooiil.historia.core.handlers.crafting.CraftingTableInspector;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraftListener implements Listener {

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {

        CraftingTableInspector inspector = new CraftingTableInspector(event.getInventory().getContents());
        ItemStack resultItem;

        Logging.debugToConsole("[PICE] Pattern: " + inspector.getPattern());
        Logging.debugToConsole("[PICE] Materials: " + inspector.getMaterials());
        Logging.debugToConsole("[PICE] Full Materials: " + inspector.getFullMaterials());

        CraftingItemManager cim = new CraftingItemManager(inspector);
        cim.doMatch();

        if (cim.getResult() != null) resultItem = cim.getResult();
        else if (event.getRecipe() != null) resultItem = event.getRecipe().getResult();
        else resultItem = null;

        if (resultItem != null) {
            if (resultItem.getItemMeta() != null) {
                
                Logging.debugToConsole("Result: " + resultItem.getItemMeta().getLocalizedName() + " " + resultItem.getAmount());

            }
            else {
                    
                Logging.debugToConsole("Result: " + resultItem.getType().toString() + " " + resultItem.getAmount());
            
            }
        }
        else Logging.debugToConsole("Result: null");



        event.getInventory().setResult(resultItem);

    }

}
