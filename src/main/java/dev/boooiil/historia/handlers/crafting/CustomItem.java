package dev.boooiil.historia.handlers.crafting;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.util.Logging;

public class CustomItem {
    
    ItemStack item;

    /**
     * It takes a list of items, and returns an item.
     * 
     * @param items The list of items to compare.
     * @param materials The list of materials to compare.
     */
    CustomItem(List<CraftedItem> items, List<String> materials) {

        String replace = "LOW_|MEDIUM_|HIGH_";

        for (CraftedItem item : items) {

            int need = materials.size();
            int matched = 0;

            Logging.debugToConsole("[getItemBasedOnIngot] Item: " + item);
            Logging.debugToConsole("[getItemBasedOnIngot] Item Name: " + item.getItemStack().getItemMeta().getLocalizedName());
            Logging.debugToConsole("[getItemBasedOnIngot] Materials: " + item.getRecipeItems());
            Logging.debugToConsole("[getItemBasedOnIngot] need: " + need);
            Logging.debugToConsole("[getItemBasedOnIngot] matched: " + matched);

            if (materials.size() == item.getRecipeItems().size()) {

                for (String material : materials) {

                    Logging.debugToConsole("" + item.getRecipeItems().contains(material.replaceFirst(replace, "")));

                    if (item.getRecipeItems().contains(material.replaceFirst(replace, "")))
                        matched++;

                }

                Logging.debugToConsole("[getItemBasedOnIngot] " + item);

                if (matched == need)
                    this.item = item.getItemStack();

            }
        }

        this.item = new CraftedItem().getItemStack();

    }

    /**
     * It returns the item.
     * 
     * @return The item variable.
     */
    public ItemStack getItem() {

        return this.item;

    }
}
