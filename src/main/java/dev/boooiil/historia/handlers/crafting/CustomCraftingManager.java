package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.configuration.Config;

/**
 * It takes an inventory, and returns an item.
 */
public class CustomCraftingManager {

    ItemStack result;

    public CustomCraftingManager(CraftingInventory inventory) {

        TableInspector inspector = new TableInspector(inventory.getContents());

        ArrayList<String> patterns = inspector.getPattern();
        ArrayList<String> materials = inspector.getMaterials();

        List<CraftedItem> matchingItems;

        // Getting all the items that match the pattern.
        matchingItems = Config.getArmorConfig().getAllMatchingShape(patterns);
        matchingItems.addAll(Config.getWeaponConfig().getAllMatchingShape(patterns));

        if (matchingItems.size() > 0) {

            result = new CustomItem(matchingItems, materials).getItem();

        }

    }

    public ItemStack getResult() {
        return result;
    }

}