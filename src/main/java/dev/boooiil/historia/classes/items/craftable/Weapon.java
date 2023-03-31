package dev.boooiil.historia.classes.items.craftable;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.util.Construct;

/**
 * It's a class that represents a weapon in the game.
 */
public class Weapon extends CraftedItem {

    private String weightClass;

    private String weight;

    private List<Double> damageRange;

    private List<Double> speedRange;

    private List<Double> knockbackRange;

    private List<Double> sweepRange;

    private List<Integer> durabilityRange;

    // It's a constructor.
    public Weapon(String weaponName) {

        configuration = Config.getWeaponConfig().getConfiguration();

        valid = Config.getWeaponConfig().isValid(weaponName);

        if (valid) {

            String root = weaponName;

            // It's calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(weaponName + ".item.type"),
                    configuration.getInt(weaponName + ".item.amount"),
                    configuration.getString(weaponName + ".item.display-name"),
                    configuration.getString(weaponName + ".item.loc-name"),
                    configuration.getStringList(weaponName + ".item.lore"));

            this.recipeItems = configuration.getStringList(root + ".recipe-items");
            this.recipeShape = configuration.getStringList(root + ".recipe-shape");

            this.weightClass = configuration.getString(root + ".type");
            this.damageRange = configuration.getDoubleList(root + ".damage");
            this.knockbackRange = configuration.getDoubleList(root + ".knockback");
            this.sweepRange = configuration.getDoubleList(root + ".sweeping");
            this.durabilityRange = configuration.getIntegerList(root + ".durability");

        } else {
            itemStack = new ItemStack(Material.AIR);
        }
    }

    /**
     * This function returns the damage range of the weapon.
     * 
     * @return The damage range of the weapon.
     */
    public List<Double> getDamageRange() {
        return damageRange;
    }

    /**
     * This function returns the speed range of the car
     * 
     * @return The speedRange list.
     */
    public List<Double> getSpeedRange() {
        return speedRange;
    }

    /**
     * It returns a list of doubles
     * 
     * @return The knockbackRange list.
     */
    public List<Double> getKnockbackRange() {
        return knockbackRange;
    }

    /**
     * This function returns a list of doubles that represent the sweep range
     * 
     * @return The sweepRange list is being returned.
     */
    public List<Double> getSweepRange() {
        return sweepRange;
    }

    /**
     * This function returns a list of integers that represent the durability range
     * of the item
     * 
     * @return The durabilityRange variable is being returned.
     */
    public List<Integer> getDurabilityRange() {
        return durabilityRange;
    }

    /**
     * This function returns the type of the current object
     * 
     * @return The type of the object.
     */
    public String getWeightClass() {

        return this.weightClass;

    }

    /**
     * This function returns the weight of the object
     * 
     * @return The weight of the object.
     */
    public String getWeight() {

        return this.weight;

    }

    /**
     * This function returns a random double value between the minimum and maximum
     * damage values
     * 
     * @return A random double value between the min and max damage values.
     */
    public Double getRandomDefenseValue() {

        Random random = new Random();
        Double result = random.nextDouble() * (getMinDamageValue() - getMaxDamageValue()) + getMinDamageValue();

        return result;

    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public Integer getRandomDurabilityValue() {

        Random random = new Random();
        Integer result = random.nextInt() * (getMinDurabilityValue() - getMaxDurabilityValue())
                + getMinDurabilityValue();

        return result;

    }

    /**
     * This function returns the first element of the damageRange ArrayList
     * 
     * @return The first element of the damageRange array.
     */
    public Double getMinDamageValue() {

        return this.damageRange.get(0);

    }

    /**
     * It returns the maximum damage value of the damage range
     * 
     * @return The second value in the damageRange array.
     */
    public Double getMaxDamageValue() {

        return this.damageRange.get(1);

    }

    /**
     * This function returns the first value in the durabilityRange array
     * 
     * @return The first value in the durabilityRange array.
     */
    public Integer getMinDurabilityValue() {

        return this.durabilityRange.get(0);

    }

    /**
     * This function returns the maximum durability value of the item
     * 
     * @return The second value in the durabilityRange array.
     */
    public Integer getMaxDurabilityValue() {

        return this.durabilityRange.get(1);

    }

}