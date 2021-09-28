package dev.boooiil.historia.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

public class OreConfig {

    private static FileConfiguration configuration = FileGetter.get("ores.yml");

    static final Set<String> oreSet = configuration.getKeys(false);

    /**
     *
     * Constructs specific information from a given ore.
     * 
     * <p>
     * To access the constructor in Ore:
     * 
     * <pre>
     * {
     *   OreConfig oreConfig = new OreConfig();
     *   OreConfig.Ore ore = oreConfig.new Ore(oreName);
     * }
     * </pre>
     * 
     *
     */
    public class Ore {

        private String type;
        private String localizedName;
        private String displayName;
        private List<String> lore;
        private int amount;

        

        Ore(String oreName) {

        }
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
