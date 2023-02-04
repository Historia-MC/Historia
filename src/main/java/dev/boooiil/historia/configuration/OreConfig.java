package dev.boooiil.historia.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.classes.Ore;
import dev.boooiil.historia.classes.OreDrop;
import dev.boooiil.historia.classes.OreManager;

public class OreConfig extends Configuration {

    //private static FileConfiguration configuration = FileGetter.get("ores.yml");

    //private static final Set<String> oreSet = configuration.getKeys(false);

    //private static final HashMap<String, OreManager> oreMap = new HashMap<>();

    /**
     * 
     * SEE IF WE CNA MOVE THIS TO A STATIC LIST SO THAT WE CAN JUIST CHECK AGAINST
     * BLOCKS THAT ARE BROKEN INSTEAD OF LOADING A NEW INSTANCE EACH BREAK
     * 
     */

    public OreConfig() {

        super("ores.yml", OreManager.class);

    }

    public OreManager getOreManager(String oreName) {

        if (isValidOre(oreName)) {

            return (OreManager) map.get(oreName);

        }
        else return null;

    }

    public Ore getOreFromChance(String oreName) {

        OreManager oreManager = getOreManager(oreName);

        if (oreManager != null) {

            return oreManager.getOreFromChance();

        }
        else return null;

    }

    public OreDrop getDropFromChance(String oreName, String className) {

        Ore ore = getOreFromChance(oreName);

        if (ore != null) {

            return ore.getDropFromChance(className);

        }

        else return null;

    }
    
    public List<Material> getOreBlocks() {

        Set<String> blocks = set;
        List<Material> found = new ArrayList<>();

        for (String block : blocks) {

            found.add(Material.getMaterial(block));

        }

        return found;

    }

    public boolean isValidOre(String oreName) {

        return set.contains(oreName);

    }
}
