package dev.boooiil.historia.classes.items.generic;

import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.configuration.specific.OreConfig;
import dev.boooiil.historia.util.Construct;

/**
 * This class should not be initialized outside of {@link OreConfig}.
 */
public class OreDrop extends GenericItem {

    private String requiredClass;

    private int chance;

    // It's a constructor.
    public OreDrop(String currentRoot, String dropName) {

        configuration = ConfigurationLoader.getOreConfig().getConfiguration();

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
