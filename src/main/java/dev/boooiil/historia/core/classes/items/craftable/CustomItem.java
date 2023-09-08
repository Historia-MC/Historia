package dev.boooiil.historia.core.classes.items.craftable;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.util.Construct;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomItem extends CraftedItem {

    private String itemName;

    public CustomItem(String localizedName) {

        YamlConfiguration configuration = ConfigurationLoader.getCustomItemConfig().getConfiguration();
        valid = ConfigurationLoader.getCustomItemConfig().isValid(localizedName);

        if (valid) {

            this.itemName = localizedName;

            // Calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(localizedName + ".item.type"),
                    configuration.getInt(localizedName + ".item.amount"),
                    configuration.getString(localizedName + ".item.display-name"),
                    configuration.getString(localizedName + ".item.loc-name"),
                    configuration.getStringList(localizedName + ".item.lore"));

            // Getting the recipe items from the config.
            this.recipeItems = configuration.getStringList(localizedName + ".recipe-items");

            // Getting the recipe shape from the config.
            this.recipeShape = configuration.getStringList(localizedName + ".recipe-shape");

            this.isShaped = configuration.getBoolean(localizedName + ".requireShape");

            this.proficiencies = configuration.getStringList(localizedName + ".canCraft");

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
