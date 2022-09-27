package dev.boooiil.historia.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.classes.Ore;
import dev.boooiil.historia.classes.OreDrop;
import dev.boooiil.historia.classes.OreManager;
import dev.boooiil.historia.util.FileGetter;

public class OreConfig {

    private static FileConfiguration configuration = FileGetter.get("ores.yml");

    private static final Set<String> oreSet = configuration.getKeys(false);

    private static final HashMap<String, OreManager> oreMap = new HashMap<>();

    /**
     * 
     * SEE IF WE CNA MOVE THIS TO A STATIC LIST SO THAT WE CAN JUIST CHECK AGAINST
     * BLOCKS THAT ARE BROKEN INSTEAD OF LOADING A NEW INSTANCE EACH BREAK
     * 
     */

    public static void init() {

        for (String ore : oreSet) {

            if (!ore.equals("version")) {

                oreMap.put(ore, new OreManager(ore));
                
            }

        }

    }

    public static FileConfiguration getConfiguration() {

        return configuration;

    }
    
    public static OreManager getOreManager(String oreName) {

        if (isValidOre(oreName)) {

            return oreMap.get(oreName);

        }
        else return null;

    }

    public static Ore getOreFromChance(String oreName) {

        OreManager oreManager = getOreManager(oreName);

        if (oreManager != null) {

            return oreManager.getOreFromChance();

        }
        else return null;

    }

    public static OreDrop getDropFromChance(String oreName, String className) {

        Ore ore = getOreFromChance(oreName);

        if (ore != null) {

            return ore.getDropFromChance(className);

        }

        else return null;

    }
    public static List<Material> getOreBlocks() {

        Set<String> blocks = oreSet;
        List<Material> found = new ArrayList<>();

        for (String block : blocks) {

            found.add(Material.getMaterial(block));

        }

        return found;

    }

    public static Set<String> getOreSet() {

        return oreSet;

    }

    public static boolean isValidOre(String oreName) {

        return oreSet.contains(oreName);

    }
}
