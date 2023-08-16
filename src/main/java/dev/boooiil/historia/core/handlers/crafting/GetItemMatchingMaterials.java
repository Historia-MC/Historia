package dev.boooiil.historia.core.handlers.crafting;

import dev.boooiil.historia.core.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GetItemMatchingMaterials {

    ItemStack item;
    CraftedItem craftedItem;

    /**
     * It takes a list of items, and returns an item.
     * 
     * @param items     The list of items to compare.
     * @param materials The list of materials to compare.
     */
    GetItemMatchingMaterials(CraftedItem item, List<String> materials) {

        String replace = "LOW_|MEDIUM_|HIGH_";

        int need = materials.size();
        int matched = 0;

        // Logging.debugToConsole("[getItemBasedOnIngot] Item: " + item.getItemStack().getType());
        // Logging.debugToConsole("[getItemBasedOnIngot] Item Name: " + item.getItemStack().getItemMeta().getLocalizedName());
        // Logging.debugToConsole("[getItemBasedOnIngot] Materials: " + item.getRecipeItems());
        // Logging.debugToConsole("[getItemBasedOnIngot] Recipe Item Size: " + item.getRecipeItems().size());
        // Logging.debugToConsole("[getItemBasedOnIngot] Materials Size: " + materials.size());

        if (materials.size() == item.getRecipeItems().size()) {

            for (String material : materials) {

                Logging.debugToConsole(
                        "[GIMM] Contains Material? " + item.getRecipeItems() + " " + material.replaceFirst(replace, ""));

                if (item.getRecipeItems().contains(material.replaceFirst(replace, "")))
                    matched++;

            }

            Logging.debugToConsole("[GIMM] " + item.getItemStack().getType());
            Logging.debugToConsole("[GIMM] need: " + need);
            Logging.debugToConsole("[GIMM] matched: " + matched);

            if (matched == need) {

                this.item = item.getItemStack();
                this.craftedItem = item;
                Logging.debugToConsole("[GIMM] Item: " + this.item.getType());

            }

        }

    }

    /**
     * It returns the item.
     * 
     * @return The item variable.
     */
    public ItemStack getItem() {

        return this.item;

    }

    public CraftedItem getCraftedItem() {

        return this.craftedItem;

    }
}
