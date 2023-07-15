package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.configuration.Config;

public class CraftingCustomItemManager {

    ItemStack result;

    public CraftingCustomItemManager(TableInspector inspector) {

        //TODO: This might change at some point. 
        //      Eventually we will have an item that requires a pattern match.

        ArrayList<String> patterns = inspector.getPattern();
        ArrayList<String> materials = inspector.getMaterials();

        List<CraftedItem> matchingItems;

        // Getting all the items that match the material.
        matchingItems = Config.getCustomItemConfig().getAllMatchingMaterials(materials);

        if (matchingItems.size() > 0) {

            result = new GetItem(matchingItems, materials).getItem();

        }

    }

    public ItemStack getResult() {
        return result;
    }

}
