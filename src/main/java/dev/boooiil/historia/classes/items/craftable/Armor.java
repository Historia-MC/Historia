package dev.boooiil.historia.classes.items.craftable;

import java.util.List;
import java.util.Random;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.util.Construct;

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

    private boolean valid;

    // Getting the armor's information from the config.
    public Armor(String armorName) {

        configuration = Config.getArmorConfig().getConfiguration();

        valid = Config.getArmorConfig().isValid(armorName);

        if (valid) {

            String itemRoot = armorName;

            // Calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(armorName + ".item.type"),
                    configuration.getInt(armorName + ".item.amount"),
                    configuration.getString(armorName + ".item.display-name"),
                    configuration.getString(armorName + ".item.loc-name"),
                    configuration.getStringList(armorName + ".item.lore"));

            // Getting the weight class of the armor.
            this.weightClass = configuration.getString(itemRoot + ".type");

            // Getting the weight of the armor.
            this.weight = configuration.getInt(itemRoot + ".weight");

            // Getting the defense value of the armor.
            this.defense = configuration.getDoubleList(itemRoot + ".defense");

            // Getting the durability of the armor.
            this.durability = configuration.getIntegerList(itemRoot + ".durability");

            // Getting the recipe items from the config.
            this.recipeItems = configuration.getStringList(itemRoot + ".recipe-items");

            // Getting the recipe shape from the config.
            this.recipeShape = configuration.getStringList(itemRoot + ".recipe-shape");

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

        Random random = new Random();
        Double result = random.nextDouble() * (getMinDefenceValue() - getMaxDefenceValue()) + getMinDefenceValue();

        return result;

    }

    /**
     * Get the randomized durability value of the armor.
     * 
     * @return Durability value.
     */
    public Integer getRandomDurabilityValue() {

        Random random = new Random();
        Integer result = random.nextInt() * (getMinDurabilityValue() - getMaxDurabilityValue())
                + getMinDurabilityValue();

        return result;

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
