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
