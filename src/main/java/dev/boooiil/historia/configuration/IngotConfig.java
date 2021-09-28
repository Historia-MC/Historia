package dev.boooiil.historia.configuration;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.ConstructItemStack;
import dev.boooiil.historia.util.FileGetter;

public class IngotConfig {

    private static FileConfiguration configuration = FileGetter.get("ingots.yml");

    static final Set<String> ingotSet = configuration.getKeys(false);

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
    public class Ingot {

        private String type;
        private String localizedName;
        private String displayName;
        private List<String> lore;
        private int amount;

        public ItemStack ingotItemStack;
        public String progression;
        public String itemName;
        public boolean validIngot;
        public int smeltTime;
        public int smeltAmount;
        public int smeltFail;

        public Ingot(String ingotName) {

            this.itemName = ingotName;
            this.validIngot = IngotConfig.validIngot(ingotName);

            if (validIngot) {

                String root = ingotName;
                String itemRoot = ingotName + ".item";

                this.type = configuration.getString(itemRoot + ".type");
                this.localizedName = configuration.getString(itemRoot + ".loc-name");
                this.displayName = configuration.getString(itemRoot + ".display-name");
                this.lore = configuration.getStringList(itemRoot + ".lore");
                this.amount = configuration.getInt(itemRoot + ".amount");
                
                this.ingotItemStack = ConstructItemStack.construct(type, amount, displayName, localizedName, lore);
                this.progression = configuration.getString(root + ".smelt_into");
                this.smeltTime = configuration.getInt(root + ".time");
                this.smeltAmount = configuration.getInt(root + ".smelt_times");
                this.smeltFail = configuration.getInt(root + ".fail");
            
            }

        }

    }

    /*
     * ************************** STATIC METHODS **************************
     */

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
    public static boolean validIngot(String ingotName) {

        return ingotSet.contains(ingotName);

    }

}
