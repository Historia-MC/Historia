package dev.boooiil.historia.classes.items.craftable;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.util.Construct;

public class CustomItem extends CraftedItem {

    private String itemName;

    public CustomItem(String itemName) {

        YamlConfiguration configuration = ConfigurationLoader.getCustomItemConfig().getConfiguration();
        valid = ConfigurationLoader.getCustomItemConfig().isValid(itemName);

        if (valid) {

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

            this.isShaped = configuration.getBoolean(itemName + ".requireShape");

            this.proficiencies = configuration.getStringList(itemName + ".canCraft");

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

    public boolean isShapeDependent() {
        return isShaped;
    }
}
