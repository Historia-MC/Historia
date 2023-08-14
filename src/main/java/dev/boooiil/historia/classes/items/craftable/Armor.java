package dev.boooiil.historia.classes.items.craftable;

import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.util.Construct;
import dev.boooiil.historia.util.NumberUtils;
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
    public Armor(String armorName) {

        YamlConfiguration configuration = ConfigurationLoader.getArmorConfig().getConfiguration();

        valid = ConfigurationLoader.getArmorConfig().isValid(armorName);

        if (valid) {

            // Calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(armorName + ".item.type"),
                    configuration.getInt(armorName + ".item.amount"),
                    configuration.getString(armorName + ".item.display-name"),
                    configuration.getString(armorName + ".item.loc-name"),
                    configuration.getStringList(armorName + ".item.lore"));

            // Getting the weight class of the armor.
            this.weightClass = configuration.getString(armorName + ".type");

            // Getting the weight of the armor.
            this.weight = configuration.getInt(armorName + ".weight");

            // Getting the defense value of the armor.
            this.defense = configuration.getDoubleList(armorName + ".defense");

            // Getting the durability of the armor.
            this.durability = configuration.getIntegerList(armorName + ".durability");

            // Getting the recipe items from the config.
            this.recipeItems = configuration.getStringList(armorName + ".recipe-items");

            // Getting the recipe shape from the config.
            this.recipeShape = configuration.getStringList(armorName + ".recipe-shape");
            
            this.proficiencies = configuration.getStringList(armorName + ".canCraft");

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
