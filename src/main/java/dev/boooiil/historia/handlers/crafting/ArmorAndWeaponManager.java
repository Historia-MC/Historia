package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.configuration.ConfigurationLoader;

/**
 * It takes an inventory, and returns an item.
 */
public class ArmorAndWeaponManager {

    //TODO: merge with CraftingCustomItemManager

    ItemStack result;

    public ArmorAndWeaponManager(CraftingTableInspector inspector) {

        ArrayList<String> patterns = inspector.getPattern();
        ArrayList<String> materials = inspector.getMaterials();

        List<CraftedItem> matchingItems;

        // Getting all the items that match the pattern.
        matchingItems = ConfigurationLoader.getArmorConfig().getAllMatchingShape(patterns);
        matchingItems.addAll(ConfigurationLoader.getWeaponConfig().getAllMatchingShape(patterns));

        if (matchingItems.size() > 0) {

            result = new GetItem(matchingItems, materials).getItem();

        }

    }

    public ItemStack getResult() {
        return result;
    }

}