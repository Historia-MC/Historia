package dev.boooiil.historia.classes.items.generic;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.classes.items.BaseItem;
import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.util.Construct;

public class Ingot extends BaseItem {

    private String progression;
    // private String itemName;

    private boolean validIngot;
    private int smeltTime;
    private int smeltAmount;
    private int smeltFail;

    private Ingot progressInto;

    // A constructor.
    public Ingot(String ingotName) {

        YamlConfiguration configuration = ConfigurationLoader.getIngotConfig().getConfiguration();
        // this.itemName = ingotName;
        this.validIngot = ConfigurationLoader.getIngotConfig().isValidIngot(ingotName);

        if (validIngot) {

            this.name = ingotName;

            itemStack = Construct.itemStack(
                    configuration.getString(ingotName + ".item.type"),
                    configuration.getInt(ingotName + ".item.amount"),
                    configuration.getString(ingotName + ".item.display-name"),
                    configuration.getString(ingotName + ".item.loc-name"),
                    configuration.getStringList(ingotName + ".item.lore"));

            this.progression = configuration.getString(ingotName + ".smelt_into");
            this.smeltTime = configuration.getInt(ingotName + ".time");
            this.smeltAmount = configuration.getInt(ingotName + ".smelt_times");
            this.smeltFail = configuration.getInt(ingotName + ".fail");

            if (this.progression != null) {

                this.progressInto = new Ingot(this.progression);

            }

        }

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
     * @return The Ingot object that is being returned is the Ingot object that is
     *         being created in the
     *         constructor.
     */
    public Ingot getProgression() {

        return this.progressInto;

    }

    /**
     * This function returns true if the current object has a progression, and false
     * if it does not.
     * 
     * @return The method is returning a boolean value.
     */
    public boolean hasProgression() {

        return this.progressInto != null;

    }

}
