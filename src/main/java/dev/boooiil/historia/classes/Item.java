package dev.boooiil.historia.classes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class Item {

    /**
     * INTENDED GENERIC TO EXTEND CLASSES THAT HOLD ITEMS.
     * TODO: IMPLEMENT
     */

    protected FileConfiguration configuration;
    protected boolean valid;

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
