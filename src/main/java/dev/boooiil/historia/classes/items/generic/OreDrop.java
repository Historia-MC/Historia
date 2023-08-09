package dev.boooiil.historia.classes.items.generic;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.classes.items.BaseItem;
import dev.boooiil.historia.configuration.specific.OreConfig;
import dev.boooiil.historia.util.Construct;

/**
 * This class should not be initialized outside of {@link OreConfig}.
 */
public class OreDrop extends BaseItem {

    /**
     * THIS IS HOW THE LOADING WORKS:
     * ------ *** ITERATION 1 *** ------
     * ORE_NAME_1: (OreBlock)
     *     drop_name_1: (OreBlock)
     *     chance: (OreBlock)
     *         DROP_NAME_1: (dropName)
     *                      
     *
     * OreBlock contains a list of OreDrop objects
     * associated with the provided block name and drop category.
     */

    private String requiredClass;

    private int chance;

    // It's a constructor.
    public OreDrop(String currentRoot, String dropName, YamlConfiguration configuration) {

        this.name = dropName;
        // Accessing BLOCK_NAME_1.drop_category_1.DROP_NAME_1
        String root = currentRoot + "." + dropName;

        itemStack = Construct.itemStack(
                configuration.getString(root + ".item.type"),
                configuration.getInt(root + ".item.amount"),
                configuration.getString(root + ".item.display-name"),
                configuration.getString(root + ".item.loc-name"),
                configuration.getStringList(root + ".item.lore"));

        this.requiredClass = configuration.getString(root + ".class");
        this.chance = configuration.getInt(root + ".chance");

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

}
