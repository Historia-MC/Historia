package dev.boooiil.historia.events.crafting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.specific.ArmorConfig;
import dev.boooiil.historia.configuration.specific.CustomItemConfig;
import dev.boooiil.historia.configuration.specific.WeaponConfig;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.handlers.crafting.CraftingResult;
import dev.boooiil.historia.util.Logging;

public class PlayerCraftingResult implements Listener {

    @EventHandler
    public void onCraftResult(CraftItemEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getWhoClicked().getUniqueId(), false);
        WeaponConfig weaponConfig = Config.getWeaponConfig();
        ArmorConfig armorConfig = Config.getArmorConfig();
        CustomItemConfig customItemConfig = Config.getCustomItemConfig();
        CraftingResult craftingResult;

        ItemStack item = event.getClickedInventory().getItem(0);

        Logging.debugToConsole("[onCraftResult] Crafting a " + item.getItemMeta().getLocalizedName());

        if (weaponConfig.isValid(item.getItemMeta().getLocalizedName())) {

            Logging.debugToConsole("[onCraftResult] Crafting a weapon");

            craftingResult = new CraftingResult(
            event.getInventory(), 
            item, 
            weaponConfig.getObject(item.getItemMeta().getLocalizedName()), 
            historiaPlayer);

        } else if (armorConfig.isValid(item.getItemMeta().getLocalizedName())) {

            Logging.debugToConsole("[onCraftResult] Crafting an armor");

            craftingResult = new CraftingResult(
            event.getInventory(), 
            item, 
            armorConfig.getObject(item.getItemMeta().getLocalizedName()), 
            historiaPlayer);

        } else {

            Logging.debugToConsole("[onCraftResult] Crafting a custom item");

            craftingResult = new CraftingResult(
            event.getInventory(), 
            item, 
            customItemConfig.getObject(item.getItemMeta().getLocalizedName()), 
            historiaPlayer);

        }

        if (craftingResult != null) {

            craftingResult.generateRandomModifiers();

        }
    }
    
}