package dev.boooiil.historia.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.Construct;
import dev.boooiil.historia.util.FileGetter;
import dev.boooiil.historia.util.Logging;

public class IngotConfig {

    private static FileConfiguration configuration = FileGetter.get("ingots.yml");

    private static final Set<String> ingotSet = configuration.getKeys(false);

    private static final HashMap<String, Ingot> ingotMap = new HashMap<>();

    public static void init() {

        for (String ingot : ingotSet) {

            if (!ingot.equals("version")) {

                ingotMap.put(ingot, new Ingot(ingot));
                
            }

        }

    }

    /**
     *
     * Constructs specific information from a given ingot.
     * 
     * <p> To access the constructor in Ingot:
     * 
     * <pre>
     * {
     *  IngotConfig ingotConfig = new IngotConfig();
     *  IngotConfig.Ingot ingot = ingotConfig.new Ingot(ingotName);
     * }
     * </pre>
     */
    public static class Ingot {

        private String type;
        private String localizedName;
        private String displayName;
        private List<String> lore;
        private int amount;

        private String progression;
        //private String itemName;

        public ItemStack itemStack;
        public boolean validIngot;
        public int smeltTime;
        public int smeltAmount;
        public int smeltFail;

        public Ingot progressInto;

        public Ingot(String ingotName) {

            //this.itemName = ingotName;
            this.validIngot = IngotConfig.isValidIngot(ingotName);

            if (validIngot) {

                String root = ingotName;
                String itemRoot = ingotName + ".item";

                this.type = configuration.getString(itemRoot + ".type");
                this.localizedName = configuration.getString(itemRoot + ".loc-name");
                this.displayName = configuration.getString(itemRoot + ".display-name");
                this.lore = configuration.getStringList(itemRoot + ".lore");
                this.amount = configuration.getInt(itemRoot + ".amount");
                
                this.itemStack = Construct.itemStack(Material.getMaterial(type), amount, displayName, localizedName, lore);
                this.progression = configuration.getString(root + ".smelt_into");
                this.smeltTime = configuration.getInt(root + ".time");
                this.smeltAmount = configuration.getInt(root + ".smelt_times");
                this.smeltFail = configuration.getInt(root + ".fail");

                if (this.progression != null) {

                    this.progressInto = new Ingot(this.progression);

                }
            
            }

        }

        public ItemStack getItemStack() {

            return this.itemStack;

        }

        public int getSmeltTime() {

            return this.smeltTime;

        }

        public int getSmeltAmount() {

            return this.smeltAmount;

        }

        public int getFailChance() {

            return this.smeltFail;

        }

        public Ingot getProgression() {

            return this.progressInto;

        }

    }

    /*
     * ************************** STATIC METHODS **************************
     */

    

    public static Ingot getIngot(String ingotLocName) {

        if (isValidIngot(ingotLocName)) {

            return ingotMap.get(ingotLocName);

        }

        else return null;

    }
    
    /**
     * Get a set (unordered list) of all items described in ingots.yml.
     * 
     * 
     * @return Set of all ingots described in ingots.yml.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    public static Set<String> getIngotSet() {

        return ingotSet;

    }

    /**
     * If the ingot provided is in ingots.yml.
     * 
     * @param blockName - Name of the ingot to check.
     * @return If the ingot provided is in ingots.yml.
     */
    public static boolean isValidIngot(String ingotName) {
        
        return ingotSet.contains(ingotName);

    }

}
