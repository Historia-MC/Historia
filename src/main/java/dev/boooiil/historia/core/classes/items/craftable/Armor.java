package dev.boooiil.historia.core.classes.items.craftable;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.util.Construct;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

/**
 *
 * Constructs specific information from a given armor.
 *
 */
public class Armor extends CraftedItem {

    private String weightClass;

    private Integer weight;

    private List<Double> defense;
    private List<Integer> durability;

    // Getting the armor's information from the config.
    public Armor(String localizedName) {

        YamlConfiguration configuration = ConfigurationLoader.getArmorConfig().getConfiguration();

        valid = ConfigurationLoader.getArmorConfig().isValid(localizedName);

        if (valid) {

            // Calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(localizedName + ".item.type"),
                    configuration.getInt(localizedName + ".item.amount"),
                    configuration.getString(localizedName + ".item.display-name"),
                    configuration.getString(localizedName + ".item.loc-name"),
                    configuration.getStringList(localizedName + ".item.lore"));

            // Getting the weight class of the armor.
            this.weightClass = configuration.getString(localizedName + ".type");

            // Getting the weight of the armor.
            this.weight = configuration.getInt(localizedName + ".weight");

            // Getting the defense value of the armor.
            this.defense = configuration.getDoubleList(localizedName + ".defense");

            // Getting the durability of the armor.
            this.durability = configuration.getIntegerList(localizedName + ".durability");

            // Getting the recipe items from the config.
            this.recipeItems = configuration.getStringList(localizedName + ".recipe-items");

            // Getting the recipe shape from the config.
            this.recipeShape = configuration.getStringList(localizedName + ".recipe-shape");
            
            this.proficiencies = configuration.getStringList(localizedName + ".canCraft");

        }

    }

    /**
     * Get the type (weight class) of armor.
     * 
     * @return Type of armor.
     */
    public String getWeightClass() {

        return this.weightClass;

    }

    /**
     * Get the weight of the armor.
     * 
     * @return Weight of the armor.
     */
    public Integer getWeight() {

        return this.weight;

    }

    /**
     * Get the randomized defense value of the armor.
     * 
     * @return Defence value.
     */
    public Double getRandomDefenseValue() {

        return NumberUtils.random(getMinDefenceValue(), getMaxDefenceValue());

    }

    /**
     * Get the randomized durability value of the armor.
     * 
     * @return Durability value.
     */
    public Integer getRandomDurabilityValue() {

        return NumberUtils.randomInt(getMinDurabilityValue(), getMaxDurabilityValue());

    }

    /**
     * Get the minimum base defense value of the armor.
     * 
     * @return Defense value.
     */
    public Double getMinDefenceValue() {

        return this.defense.get(0);

    }

    /**
     * Get the maximum base defense value of the armor.
     * 
     * @return Defence value.
     */
    public Double getMaxDefenceValue() {

        return this.defense.get(1);

    }

    /**
     * Get the minimum base durability value of the armor.
     * 
     * @return Durability value.
     */
    public Integer getMinDurabilityValue() {

        return this.durability.get(0);

    }

    /**
     * Get the minimum base durability value of the armor.
     * 
     * @return Durability value.
     */
    public Integer getMaxDurabilityValue() {

        return this.durability.get(1);

    }

}
