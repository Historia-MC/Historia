package dev.boooiil.historia.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.configuration.OreConfig;

/**
 * It's a class that manages the ores
 */
public class OreManager {

    // Creating a new instance of the OreConfig class.
    public static OreConfig oreConfig = new OreConfig();

    private FileConfiguration configuration = oreConfig.getConfiguration();
    
    private String name;

    private boolean validOre = true;

    private List<Ore> ores = new ArrayList<Ore>();

    //TODO: This throws the error when breaking a block. populateMap does not have any arguments.
    public OreManager(String oreName) {

        if (oreConfig.isValidOre(oreName)) {

            String root = oreName;

            Set<String> blockSet = configuration.getConfigurationSection(root).getKeys(false);

            this.name = oreName;

            for (String ore : blockSet) {

                if (!ore.equals("chance"))
                    this.ores.add(new Ore(root, ore));

            }

        }

        else {

            this.validOre = false;

        }
    }

    /**
     * Check if the current ore is listed in the config.
     * 
     * @return boolean
     */
    public boolean isValid() {

        return this.validOre;

    }

    /**
     * Get the name of the ore.
     * 
     * @return The name of the ore.
     */
    public String getName() {

        return this.name;

    }

    /**
     * Get a random ore from the block the player mined.
     * 
     * @return Random ore.
     */
    public Ore getOreFromChance() {

        int random = (int) Math.round(Math.random() * 100);

        List<Integer> chances = new ArrayList<>();
        HashMap<Integer, Ore> map = new HashMap<Integer, Ore>();

        for (int i = 0; i < this.ores.size(); i++) {

            Ore ore = this.ores.get(i);

            map.put(i, ore);

            for (int j = 0; j < ore.getChance(); j++) {

                chances.add(i);

            }

        }

        return map.get(chances.get(random));

    }

}

