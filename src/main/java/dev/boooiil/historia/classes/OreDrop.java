package dev.boooiil.historia.classes;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.util.Construct;

/**
 * This class should not be initialized outside of {@link OreConfig}.
 */
public class OreDrop {

    private FileConfiguration configuration = OreConfig.getConfiguration();

    private String requiredClass;

    private int chance;

    private ItemStack item;

    public OreDrop(String currentRoot, String dropName) {

        String root = currentRoot + "." + dropName;

        this.requiredClass = configuration.getString(root + ".class");
        this.chance = configuration.getInt(root + ".chance");
        this.item = Construct.itemStack(
                Material.getMaterial(configuration.getString(root + ".item.type")),
                configuration.getInt(root + ".item.amount"),
                configuration.getString(root + ".item.display-name"),
                configuration.getString(root + ".item.loc-name"),
                configuration.getStringList(root + ".item.lore"));

    }

    /**
     * Get the name of the class this ore is required to mine.
     * 
     * @return Name of the user's class.
     */
    public String getRequiredClass() {

        return this.requiredClass;

    }

    /**
     * Get the chance this drop has of dropping.
     * 
     * @return Chance of the drop.
     */
    public int getChance() {

        return this.chance;

    }

    /**
     * Get the item stack of this drop.
     * 
     * @return The item stack of this drop.
     */
    public ItemStack getItemStack() {

        return this.item;

    }

}
