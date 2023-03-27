package dev.boooiil.historia.classes;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.util.Construct;
import dev.boooiil.historia.util.Logging;

/**
 * This class should not be initialized outside of {@link OreConfig}.
 */
public class OreDrop extends Item {

    private String requiredClass;

    private int chance;

    // It's a constructor.
    public OreDrop(String currentRoot, String dropName) {

        configuration = Config.getOreConfig().getConfiguration();

        String root = currentRoot + "." + dropName;

        Logging.infoToConsole(root);

        initItemStack(Construct.itemStack(
            configuration.getString(root + ".item.type"),
            configuration.getInt(root + ".item.amount"),
            configuration.getString(root + ".item.display-name"),
            configuration.getString(root + ".item.loc-name"),
            configuration.getStringList(root + ".item.lore")));

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
