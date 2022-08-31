package dev.boooiil.historia.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.Construct;
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

    public static class OreManager {

        private String name;

        private boolean validOre = true;

        private List<Ore> ores = new ArrayList<Ore>();

        public OreManager(String oreName) {

            if (isValidOre(oreName)) {

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

    /**
     * This class should not be initialized outside of {@link OreConfig}.
     */
    public static class Ore {

        public String name;
        public int chance;

        public List<Drop> drops = new ArrayList<Drop>();

        public Ore(String currentRoot, String oreName) {

            String root = currentRoot + "." + oreName;

            Set<String> dropSet = configuration.getConfigurationSection(root).getKeys(false);

            this.name = oreName;
            this.chance = configuration.getInt(root + ".chance");

            for (String drop : dropSet) {

                if (!drop.equals("chance"))
                    this.drops.add(new Drop(root, drop));

            }

        }

        /**
         * Get a random drop.
         * 
         * @param className The user's class.
         * @return The random drop this player could mine.
         */
        public Drop getDropFromChance(String className) {

            int random = (int) Math.round(Math.random() * 100);

            List<Integer> chances = new ArrayList<>();
            HashMap<Integer, Drop> map = new HashMap<Integer, Drop>();

            for (int i = 0; i < this.drops.size(); i++) {

                Drop drop = this.drops.get(i);

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

    /**
     * This class should not be initialized outside of {@link OreConfig}.
     */
    public static class Drop {

        private String requiredClass;

        private int chance;

        private ItemStack item;

        public Drop(String currentRoot, String dropName) {

            String root = currentRoot + "." + dropName;

            this.requiredClass = configuration.getString(root + ".class");
            this.chance = configuration.getInt(root + ".chance");
            this.item = Construct.itemStack(
                    Material.getMaterial(configuration.getString(root + ".item.type")),
                    configuration.getInt(root + ".item.amount"),
                    configuration.getString(root + ".item.display-name"),
                    configuration.getString(root + ".item.loc-name"),
                    configuration.getStringList(root + ".item.lore"));

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

        /**
         * Get the item stack of this drop.
         * 
         * @return The item stack of this drop.
         */
        public ItemStack getItemStack() {

            return this.item;

        }

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

    public static Drop getDropFromChance(String oreName, String className) {

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
