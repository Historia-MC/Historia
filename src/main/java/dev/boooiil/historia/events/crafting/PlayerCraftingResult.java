package dev.boooiil.historia.events.crafting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.specific.ArmorConfig;
import dev.boooiil.historia.configuration.specific.CustomItemConfig;
import dev.boooiil.historia.configuration.specific.OreConfig;
import dev.boooiil.historia.configuration.specific.WeaponConfig;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.handlers.crafting.CraftingResult;

public class PlayerCraftingResult implements Listener {

    @EventHandler
    public void onCraftResult(CraftItemEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getWhoClicked().getUniqueId(), false);
        WeaponConfig weaponConfig = Config.getWeaponConfig();
        ArmorConfig armorConfig = Config.getArmorConfig();
        CustomItemConfig customItemConfig = Config.getCustomItemConfig();
        CraftingResult craftingResult;

        if (weaponConfig.isValid(event.getRecipe().getResult().getItemMeta().getLocalizedName())) {

            craftingResult = new CraftingResult(
            event.getInventory(), 
            event.getRecipe().getResult(), 
            weaponConfig.getObject(event.getRecipe().getResult().getItemMeta().getLocalizedName()), 
            historiaPlayer);

        } else if (armorConfig.isValid(event.getRecipe().getResult().getItemMeta().getLocalizedName())) {

            craftingResult = new CraftingResult(
            event.getInventory(), 
            event.getRecipe().getResult(), 
            armorConfig.getObject(event.getRecipe().getResult().getItemMeta().getLocalizedName()), 
            historiaPlayer);

        } else {

            craftingResult = new CraftingResult(
            event.getInventory(), 
            event.getRecipe().getResult(), 
            customItemConfig.getObject(event.getRecipe().getResult().getItemMeta().getLocalizedName()), 
            historiaPlayer);

        }

        if (craftingResult != null) {

            craftingResult.generateRandomModifiers();

        }
    }
    
}
