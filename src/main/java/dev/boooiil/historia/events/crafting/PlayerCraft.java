package dev.boooiil.historia.events.crafting;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.handlers.crafting.ArmorAndWeaponManager;
import dev.boooiil.historia.handlers.crafting.CraftingCustomItemManager;
import dev.boooiil.historia.handlers.crafting.TableInspector;

public class PlayerCraft implements Listener {

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {

        Player player = (Player) event.getViewers().get(0);
        TableInspector inspector = new TableInspector(event.getInventory().getContents());
        ItemStack resultItem;

        ArmorAndWeaponManager ccm = new ArmorAndWeaponManager(inspector);
        CraftingCustomItemManager ccim = new CraftingCustomItemManager(inspector);


        if (ccm.getResult() != null) resultItem = ccm.getResult();
        else if (ccim.getResult() != null) resultItem = ccim.getResult();
        else resultItem = event.getRecipe().getResult();


        event.getInventory().setResult(resultItem);

    }

}
