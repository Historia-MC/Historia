package dev.boooiil.historia.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.OreConfig;

/**
 * This class should not be initialized outside of {@link OreConfig}.
 */
public class Ore {

    private FileConfiguration configuration =  Config.getOreConfig().getConfiguration();
    
    private String name;
    private int chance;

    private List<OreDrop> drops = new ArrayList<OreDrop>();

    // Creating a new Ore object.
    public Ore(String currentRoot, String oreName) {

        String root = currentRoot + "." + oreName;

        Set<String> dropSet = configuration.getConfigurationSection(root).getKeys(false);

        this.name = oreName;
        this.chance = configuration.getInt(root + ".chance");

        for (String drop : dropSet) {

            if (!drop.equals("chance"))
                this.drops.add(new OreDrop(root, drop));

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
