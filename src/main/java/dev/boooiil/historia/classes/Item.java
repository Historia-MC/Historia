package dev.boooiil.historia.classes;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class Item extends ItemStack {

    /**
     * INTENDED GENERIC TO EXTEND CLASSES THAT HOLD ITEMS.
     * TODO: IMPLEMENT
     */

    protected FileConfiguration configuration;
    protected boolean valid;

    protected Item() {

        initItemStack(new ItemStack(Material.AIR));

    }

    protected void initItemStack(ItemStack itemStack) {

        this.setAmount(itemStack.getAmount());
        this.setData(itemStack.getData());
        this.setItemMeta(itemStack.getItemMeta());
        this.setType(itemStack.getType());

    }

    /**
     * It returns the item stack
     * 
     * @return The itemStack variable.
     */
    @Deprecated
    public ItemStack getItemStack() {

        return this;

    }

    public boolean isValid() {

        return this.valid;

    }
}
