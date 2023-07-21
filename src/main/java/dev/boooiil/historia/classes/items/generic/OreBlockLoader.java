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
    private String name;

    private boolean validOre = true;

    private List<OreBlock> blocks = new ArrayList<OreBlock>();

    //TODO: This throws the error when breaking a block. populateMap does not have any arguments.
    public OreBlockLoader(String oreBlockName, YamlConfiguration configuration) {

        // THIS IS HOW THE LOADING WORKS:
        // ------ *** ITERATION 1 *** ------
        // BLOCK_NAME_1: <- this is the provided name
        //     drop_category_1: <- putting into oreDropCategorySet
        //          chance: <- ignoring this
        //          DROP_NAME_1: <- call the OreBlock constructor
        //                       <- passed will be (BLOCK_NAME_1, drop_category_1)
        //
        // OreBlockLoader contains a list of OreBlock objects
        // associated with the provided name.

        if (ConfigurationLoader.getOreConfig().isValidOre(oreBlockName)) {

            Set<String> oreDropCategorySet = configuration.getConfigurationSection(oreBlockName).getKeys(false);

            this.name = oreBlockName;

            for (String oreDropCategory : oreDropCategorySet) {

                if (!oreDropCategory.equals("chance"))
                    this.blocks.add(new OreBlock(oreBlockName, oreDropCategory, configuration));

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

        for (int i = 0; i < this.blocks.size(); i++) {

            OreBlock ore = this.blocks.get(i);

            map.put(i, ore);

            for (int j = 0; j < ore.getChance(); j++) {

                chances.add(i);

            }

        }

        return map.get(chances.get(random));

    }

}

