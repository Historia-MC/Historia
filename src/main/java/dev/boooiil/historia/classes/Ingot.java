package dev.boooiil.historia.classes;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.util.Construct;

/**
 *
 * Constructs specific information from a given ingot.
 * 
 * <p>
 * To access the constructor in Ingot:
 * 
 * <pre>
 * {
 *     IngotConfig ingotConfig = new IngotConfig();
 *     IngotConfig.Ingot ingot = ingotConfig.new Ingot(ingotName);
 * }
 * </pre>
 */
public class Ingot {

    private FileConfiguration configuration = Config.getIngotConfig().getConfiguration();

    private String type;
    private String localizedName;
    private String displayName;
    private List<String> lore;
    private int amount;

    private String progression;
    // private String itemName;

    private ItemStack itemStack;
    private boolean validIngot;
    private int smeltTime;
    private int smeltAmount;
    private int smeltFail;

    private Ingot progressInto;

    // A constructor.
    public Ingot(String ingotName) {

        // this.itemName = ingotName;
        this.validIngot = Config.getIngotConfig().isValidIngot(ingotName);

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

    /**
     * It returns the item stack
     * 
     * @return The itemStack variable.
     */
    public ItemStack getItemStack() {

        return this.itemStack;

    }

    /**
     * This function returns the smeltTime variable.
     * 
     * @return The smeltTime variable.
     */
    public int getSmeltTime() {

        return this.smeltTime;

    }

    /**
     * It returns the amount of items that can be smelted
     * 
     * @return The amount of items that are being smelted.
     */
    public int getSmeltAmount() {

        return this.smeltAmount;

    }

    /**
     * This function returns the smeltFail variable.
     * 
     * @return The smeltFail variable.
     */
    public int getFailChance() {

        return this.smeltFail;

    }

    /**
     * It returns the Ingot that this Ingot progresses into
     * 
     * @return The Ingot object that is being returned is the Ingot object that is being created in the
     * constructor.
     */
    public Ingot getProgression() {

        return this.progressInto;

    }

    /**
     * This function returns true if the current object has a progression, and false if it does not.
     * 
     * @return The method is returning a boolean value.
     */
    public boolean hasProgression() {

        return this.progressInto != null;

    }

}
