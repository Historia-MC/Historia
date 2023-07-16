package dev.boooiil.historia.classes.items.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.configuration.ConfigurationLoader;

/**
 * It's a class that manages the ores
 */
public class OreBlockLoader {

    // Creating a new instance of the OreConfig class.
    private YamlConfiguration configuration = ConfigurationLoader.getOreConfig().getConfiguration();
    
    private String name;

    private boolean validOre = true;

    private List<OreBlock> ores = new ArrayList<OreBlock>();

    //TODO: This throws the error when breaking a block. populateMap does not have any arguments.
    public OreBlockLoader(String oreName) {

        if (ConfigurationLoader.getOreConfig().isValidOre(oreName)) {

            String root = oreName;

            Set<String> blockSet = configuration.getConfigurationSection(root).getKeys(false);

            this.name = oreName;

            for (String ore : blockSet) {

                if (!ore.equals("chance"))
                    this.ores.add(new OreBlock(root, ore));

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
    public OreBlock getOreFromChance() {

        int random = (int) Math.round(Math.random() * 100);

        List<Integer> chances = new ArrayList<>();
        HashMap<Integer, OreBlock> map = new HashMap<Integer, OreBlock>();

        for (int i = 0; i < this.ores.size(); i++) {

            OreBlock ore = this.ores.get(i);

            map.put(i, ore);

            for (int j = 0; j < ore.getChance(); j++) {

                chances.add(i);

            }

        }

        return map.get(chances.get(random));

    }

}

