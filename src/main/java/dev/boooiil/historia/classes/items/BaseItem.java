package dev.boooiil.historia.classes.items;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public abstract class BaseItem {

    /**
     * INTENDED GENERIC TO EXTEND CLASSES THAT HOLD ITEMS.
     * TODO: IMPLEMENT
     */

    protected YamlConfiguration configuration;
    protected boolean valid;

    /**
     * Constructor for the BaseItem class that takes an ItemStack as a parameter.
     * 
     * @param itemStack The ItemStack to be set as the itemStack variable.
     */
    public BaseItem(ItemStack itemStack) {

        this.itemStack = itemStack;
        this.valid = true;

    }

    public BaseItem() {
    };

    protected ItemStack itemStack;

    /**
     * It returns the item stack
     * 
     * @return The itemStack variable.
     */
    public ItemStack getItemStack() {

        return itemStack;

    }

    public boolean isValid() {

        return this.valid;

    }
}
