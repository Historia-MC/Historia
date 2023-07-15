package dev.boooiil.historia.events.crafting;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.handlers.crafting.ArmorAndWeaponManager;
import dev.boooiil.historia.handlers.crafting.CraftingCustomItemManager;
import dev.boooiil.historia.handlers.crafting.TableInspector;
import dev.boooiil.historia.util.Logging;

public class PlayerCraftingPrepare implements Listener {

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {

        Player player = (Player) event.getViewers().get(0);
        TableInspector inspector = new TableInspector(event.getInventory().getContents());
        ItemStack resultItem;

        ArmorAndWeaponManager ccm = new ArmorAndWeaponManager(inspector);
        CraftingCustomItemManager ccim = new CraftingCustomItemManager(inspector);

        


        if (ccm.getResult() != null) resultItem = ccm.getResult();
        else if (ccim.getResult() != null) resultItem = ccim.getResult();
        else if (event.getRecipe() != null) resultItem = event.getRecipe().getResult();
        else resultItem = null;

        if (resultItem != null) Logging.debugToConsole("Result: " + resultItem.getItemMeta().getLocalizedName() + " " + resultItem.getAmount());
        else Logging.debugToConsole("Result: null");



        event.getInventory().setResult(resultItem);

    }

}
