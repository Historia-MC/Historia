package dev.boooiil.historia.core.handlers.crafting;

import dev.boooiil.historia.core.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftingItemManager {

    private ItemStack result;
    private List<CraftedItem> matchingItems;
    private ArrayList<String> patterns;
    private ArrayList<String> materials;
    private ArrayList<String> fullMaterials;

    public CraftingItemManager(CraftingTableInspector inspector) {

        patterns = inspector.getPattern();
        materials = inspector.getMaterials();
        fullMaterials = inspector.getFullMaterials();

        // Getting all the items that match the pattern.
        matchingItems = ConfigurationLoader.getArmorConfig().getAllMatchingShape(patterns);
        matchingItems.addAll(ConfigurationLoader.getWeaponConfig().getAllMatchingShape(patterns));
        matchingItems.addAll(ConfigurationLoader.getCustomItemConfig().getAllMatchingShape(materials));

    }

    public void doMatch() {

        if (matchingItems.size() > 0) {

            for (CraftedItem item : matchingItems) {

                Logging.debugToConsole("[CIM] Item: " + item.getItemStack().getItemMeta().getLocalizedName());

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
