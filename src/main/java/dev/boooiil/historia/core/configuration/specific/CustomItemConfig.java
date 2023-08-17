package dev.boooiil.historia.core.configuration.specific;

import dev.boooiil.historia.core.classes.items.craftable.CraftedItem;
import dev.boooiil.historia.core.classes.items.craftable.CustomItem;
import dev.boooiil.historia.core.configuration.BaseConfiguration;
import dev.boooiil.historia.core.util.Logging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CustomItemConfig extends BaseConfiguration<CustomItem> {

    @Override
    public CustomItem createNew(String itemName) {
        return new CustomItem(itemName);
    }

    @Override
    public CustomItem getObject(String objectName) {
        
        if (isValid(objectName))
            return map.get(objectName);
        else
            return null;
    }

    /**
     * Get custom item based on recipe items and shape.
     * 
     * @param inputItems List of recipe items.
     * @param inputShape Recipe shape.
     * @return CustomItem object.
     */
    public CustomItem getItem(List<String> inputItems, List<String> inputShape) {

        CustomItem item = null;

        for (Map.Entry<String, CustomItem> entry : map.entrySet()) {

            boolean valid = entry.getValue().isValidRecipe(inputItems, inputShape);

            if (valid) {
                item = entry.getValue();
                break;
            }

        }

        return item;

    }


    /**
     * It checks if the shape of the recipe is valid
     * 
     * @param shape The shape of the recipe.
     * @return A boolean value.
     */
    public boolean validShape(List<String> shape) {

        boolean found = false;

        for(CustomItem item : map.values()) {

            if (item.getRecipeShape().equals(shape)) {

                found = true;
                break;

            }
        }

        return found;

    }
    
    /**
     * It returns a list of all the recipes for the armor
     * 
     * @return A list of lists of strings.
     */
    public List<List<String>> getAllShapes() {

        List<List<String>> set = new ArrayList<>();

        for(CustomItem customItem : map.values()) {

            set.add(customItem.getRecipeShape());
        }

        return set;

    }

    /**
     * It returns a list of all the items that have the same recipe shape as the one passed in
     * 
     * @param shape A list of strings that represent the shape of the recipe.
     * @return A list of all the armor items that match the shape.
     */
    public List<CraftedItem> getAllMatchingShape(List<String> shape) {

        List<CraftedItem> set = new ArrayList<>();

        for(CustomItem customItem : map.values()) {

            if (!customItem.isShapeDependent()) set.add(customItem);
            else if (customItem.getRecipeShape().equals(shape)) {

                Logging.debugToConsole(customItem.getItemStack().getItemMeta().getAsString());

                set.add(customItem);

            }
        }

        return set;

    }
    
    public List<CraftedItem> getAllMatchingMaterials(List<String> materials) {

        List<CraftedItem> set = new ArrayList<>();

        for(CustomItem customItem : map.values()) {

            if (new HashSet<>(customItem.getRecipeItems()).containsAll(materials)) {

                Logging.debugToConsole(customItem.getItemStack().getItemMeta().getAsString());

                set.add(customItem);

            }
        }

        return set;

    }

}
