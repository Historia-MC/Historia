package dev.boooiil.historia.classes;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.IngotConfig;
import dev.boooiil.historia.util.Construct;
import dev.boooiil.historia.util.Logging;

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

    public static IngotConfig ingotConfig = new IngotConfig();

    private FileConfiguration configuration = ingotConfig.getConfiguration();

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

    public Ingot(String ingotName) {

        // this.itemName = ingotName;
        this.validIngot = ingotConfig.isValidIngot(ingotName);

        if (validIngot) {

            String root = ingotName;
            String itemRoot = ingotName + ".item";

            this.type = configuration.getString(itemRoot + ".type");
            this.localizedName = configuration.getString(itemRoot + ".loc-name");
            this.displayName = configuration.getString(itemRoot + ".display-name");
            this.lore = configuration.getStringList(itemRoot + ".lore");
            this.amount = configuration.getInt(itemRoot + ".amount");

            Logging.infoToConsole(ingotName, this.type, this.localizedName, this.displayName, this.lore.toString(), "" + this.amount, configuration.saveToString());

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

    public boolean hasProgression() {

        return this.progressInto != null;

    }

}
