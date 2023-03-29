package dev.boooiil.historia.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.classes.Ore;
import dev.boooiil.historia.classes.OreDrop;
import dev.boooiil.historia.classes.OreManager;
import dev.boooiil.historia.util.Logging;

/**
 * It's a configuration class that loads a configuration file and stores the data in a HashMap
 */
public class OreConfig extends Configuration<OreManager> {

    //private static YamlConfiguration configuration = FileGetter.get("ores.yml");

    //private static final Set<String> oreSet = configuration.getKeys(false);

    //private static final HashMap<String, OreManager> oreMap = new HashMap<>();

    /**
     * Used to create a new instance of OreManager.
     * 
     * @param oreName - Name of the oreManager to check.
     * @return Returns an OreManager object.
     */
    public OreManager createNew(String oreName) {

        return new OreManager(oreName);

    }

    /**
     * If the ore name is valid, return the OreManager object associated with the ore name
     * 
     * @param oreName The name of the ore you want to get the OreManager for.
     * @return The OreManager object.
     */
    public OreManager getObject(String oreName) {

        if (isValidOre(oreName)) {

            return (OreManager) map.get(oreName);

        }
        else return null;

    }

    /**
     * It gets an ore from a chance
     * 
     * @param oreName The name of the ore you want to get the ore from.
     * @return The Ore object that is being returned is the one that is being returned from the
     * OreManager class.
     */
    public Ore getOreFromChance(String oreName) {

        OreManager oreManager = getObject(oreName);

        if (oreManager != null) {

            Ore ore = oreManager.getOreFromChance();

            return ore;

        }
        else return null;

    }

    /**
     * It gets the OreDrop from the Ore
     * 
     * @param oreName The name of the ore.
     * @param className The name of the class.
     * @return The drop from the chance.
     */
    public OreDrop getDropFromChance(String oreName, String className) {

        Ore ore = getOreFromChance(oreName);

        if (ore != null) {
            
            OreDrop oreDrop = ore.getDropFromChance(className);

            return oreDrop;

        }

        else return null;

    }
    
    /**
     * It takes a set of strings, and returns a list of materials
     * 
     * @return A list of materials.
     */
    public List<Material> getOreBlocks() {

        Set<String> blocks = set;
        List<Material> found = new ArrayList<>();

        for (String block : blocks) {

            found.add(Material.getMaterial(block, false));

        }

        return found;

    }

    /**
     * This function checks if the oreName is in the set
     * 
     * @param oreName The name of the ore to check.
     * @return A boolean value.
     */
    public boolean isValidOre(String oreName) {

        Logging.debugToConsole("[OreConfig] VALID:", set.toString(), oreName);

        return set.contains(oreName);

    }
}
