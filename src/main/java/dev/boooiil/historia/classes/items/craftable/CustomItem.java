package dev.boooiil.historia.classes.items.craftable;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.util.Construct;

public class CustomItem extends CraftedItem {

    private String itemName;
    private boolean valid;

    public CustomItem(String itemName) {

        configuration = Config.getCustomItemConfig().getConfiguration();
        this.valid = Config.getCustomItemConfig().isValid(itemName);

        if (this.valid) {

            this.itemName = itemName;

            // Calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(itemName + ".item.type"),
                    configuration.getInt(itemName + ".item.amount"),
                    configuration.getString(itemName + ".item.display-name"),
                    configuration.getString(itemName + ".item.loc-name"),
                    configuration.getStringList(itemName + ".item.lore"));

            // Getting the recipe items from the config.
            this.recipeItems = configuration.getStringList(itemName + ".recipe-items");

            // Getting the recipe shape from the config.
            this.recipeShape = configuration.getStringList(itemName + ".recipe-shape");


        }
    }
    
    /**
     * It returns the name of the item.
     * 
     * @return The name of the item.
     */
    public String getItemName() {
        
        return this.itemName;

    }
}
