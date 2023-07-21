package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.configuration.ConfigurationLoader;

public class CraftingCustomItemManager {

    ItemStack result;
    CraftedItem customItem;

    public CraftingCustomItemManager(CraftingTableInspector inspector) {

        //TODO: This might change at some point. 
        //      Eventually we will have an item that requires a pattern match.

        ArrayList<String> patterns = inspector.getPattern();
        ArrayList<String> materials = inspector.getMaterials();

        List<CraftedItem> matchingItems;

        // Getting all the items that match the material.
        matchingItems = ConfigurationLoader.getCustomItemConfig().getAllMatchingMaterials(materials);

        if (matchingItems.size() > 0) {

            GetItem getItem = new GetItem(matchingItems, materials);

            result = getItem.getItem();
            customItem = getItem.getCraftedItem();

        }

    }

    public ItemStack getResult() {
        return result;
    }

    public CraftedItem getCustomItem() {
        return customItem;
    }

}
