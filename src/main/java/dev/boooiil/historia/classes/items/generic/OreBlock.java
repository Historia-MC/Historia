package dev.boooiil.historia.classes.items.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.configuration.specific.OreConfig;
import dev.boooiil.historia.util.Logging;

/**
 * This class should not be initialized outside of {@link OreConfig}.
 */
public class OreBlock {

    /**
     * THIS IS HOW THE LOADING WORKS:
     * ------ *** ITERATION 1 *** ------
     * ORE_NAME_1: (blockName)
     *     drop_name_1: (oreDropCategory)
     *     chance: <- setting to this.chance
     *         DROP_NAME_1: <- call the OreBlock constructor
     *                      <- passed will be (BLOCK_NAME_1.drop_category_1, DROP_NAME_1)
     *
     * OreBlock contains a list of OreDrop objects
     * associated with the provided block name and drop category.
     */

    private String name;
    private int chance;

    private List<OreDrop> drops = new ArrayList<OreDrop>();

    // Creating a new Ore object.
    public OreBlock(String blockName, String oreDropCategory, YamlConfiguration configuration) {

        String root = blockName + "." + oreDropCategory;

        // Accessing BLOCK_NAME_1.drop_category_1
        Set<String> dropSet = configuration.getConfigurationSection(root).getKeys(false);

        // Set the name to the drop category.
        this.name = oreDropCategory;

        // Set the chance to the chance of the category being selected.
        this.chance = configuration.getInt(root + ".chance");

        for (String drop : dropSet) {

            if (!drop.equals("chance"))
                this.drops.add(new OreDrop(root, drop, configuration));

        }

    }

    /**
     * Get a random drop.
     * 
     * @param className The user's class.
     * @return The random drop this player could mine.
     */
    public OreDrop getDropFromChance(String className) {

        int random = (int) Math.round(Math.random() * 100);

        List<Integer> chances = new ArrayList<>();
        HashMap<Integer, OreDrop> map = new HashMap<Integer, OreDrop>();

        for (int i = 0; i < this.drops.size(); i++) {

            OreDrop drop = this.drops.get(i);

            Logging.debugToConsole("[ORE] DROP:", drop.getItemStack().getItemMeta().getAsString());

            if (className.equals(drop.getRequiredClass()) || drop.getRequiredClass().equals("Any")) {

                map.put(i, drop);

                for (int j = 0; j < drop.getChance(); j++) {

                    chances.add(i);

                }

            }

        }

        return map.get(chances.get(random));

    }

    /**
     * Get the name of the current ore.
     * 
     * @return The name of the ore.
     */
    public String getName() {

        return this.name;

    }

    /**
     * Get the chance this ore has of dropping.
     * 
     * @return Chance of the ore dropping.
     */
    public int getChance() {

        return this.chance;

    }

}
