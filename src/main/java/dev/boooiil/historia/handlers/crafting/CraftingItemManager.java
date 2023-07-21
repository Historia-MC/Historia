package dev.boooiil.historia.handlers.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.configuration.ConfigurationLoader;

public class CraftingItemManager {

    private ItemStack result;

    public CraftingItemManager(CraftingTableInspector inspector) {

        ArrayList<String> patterns = inspector.getPattern();
        ArrayList<String> materials = inspector.getMaterials();
        ArrayList<String> fullMaterials = inspector.getFullMaterials();

        List<CraftedItem> matchingItems;

        // Getting all the items that match the pattern.
        matchingItems = ConfigurationLoader.getArmorConfig().getAllMatchingShape(patterns);
        matchingItems.addAll(ConfigurationLoader.getWeaponConfig().getAllMatchingShape(patterns));
        matchingItems.addAll(ConfigurationLoader.getCustomItemConfig().getAllMatchingShape(materials));

        if (matchingItems.size() > 0) {

            for (CraftedItem item : matchingItems) {

                if (item.isShapeDependent()) {

                    result = new GetItemMatchingMaterials(item, materials).getItem();
                    
                }
                else {

                    result = new GetItemMatchingMaterials(item, fullMaterials).getItem();

                }

                if (result != null) break;

            }

        }

    }

    public ItemStack getResult() {
        return result;
    }
        
}
