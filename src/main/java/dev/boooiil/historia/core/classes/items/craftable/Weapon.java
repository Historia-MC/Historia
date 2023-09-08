package dev.boooiil.historia.core.classes.items.craftable;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.util.Construct;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * It's a class that represents a weapon in the game.
 */
public class Weapon extends CraftedItem {

    private String weightClass;

    private String weight;

    private List<Double> damageRange;
    private double damage;

    private List<Double> speedRange;
    private double speed;

    private List<Double> knockbackRange;
    private double knockback;

    private List<Double> sweepRange;
    private double sweep;

    private List<Integer> durabilityRange;
    private int durability;

    private final ItemStack itemStack;

    // It's a constructor.
    public Weapon(String localizedName) {

        YamlConfiguration configuration = ConfigurationLoader.getWeaponConfig().getConfiguration();

        valid = ConfigurationLoader.getWeaponConfig().isValid(localizedName);

        if (valid) {

            // It's calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(localizedName + ".item.type"),
                    configuration.getInt(localizedName + ".item.amount"),
                    configuration.getString(localizedName + ".item.display-name"),
                    configuration.getString(localizedName + ".item.loc-name"),
                    configuration.getStringList(localizedName + ".item.lore"));

            this.recipeItems = configuration.getStringList(localizedName + ".recipe-items");
            this.recipeShape = configuration.getStringList(localizedName + ".recipe-shape");

            this.weightClass = configuration.getString(localizedName + ".type");
            this.damageRange = configuration.getDoubleList(localizedName + ".damage");
            this.speedRange = configuration.getDoubleList(localizedName + ".speed");
            this.knockbackRange = configuration.getDoubleList(localizedName + ".knockback");
            this.sweepRange = configuration.getDoubleList(localizedName + ".sweeping");
            this.durabilityRange = configuration.getIntegerList(localizedName + ".durability");

            this.isShaped = configuration.getBoolean(localizedName + ".requireShape");
            
            this.proficiencies = configuration.getStringList(localizedName + ".canCraft");

        } else {
            itemStack = new ItemStack(Material.AIR);
        }
    }
    
    public void updateWeaponStats(List<String> lore) {

        

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
     * It returns a random integer between the minimum and maximum damage values
     * 
     * @return A random number between the min and max damage values.
     */
    public double getDamageRandomValue() {

        return NumberUtils.random(getMinDamageValue(), getMaxDamageValue());

    }

    /**
     * This function returns the speed range of the car
     * 
     * @return The speedRange list.
     */
    public List<Double> getSpeedRange() {
        return speedRange;
    }

    public double getMinSpeedValue() {
        return speedRange.get(0);
    }

    public double getMaxSpeedValue() {
        return speedRange.get(1);
    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public double getSpeedRandomValue() {

        return NumberUtils.random(getMinSpeedValue(), getMaxSpeedValue());

    }

    /**
     * It returns a list of doubles
     * 
     * @return The knockbackRange list.
     */
    public List<Double> getKnockbackRange() {
        return knockbackRange;
    }

    public double getMinKnockbackValue() {
        return knockbackRange.get(0);
    }

    public double getMaxKnockbackValue() {
        return knockbackRange.get(1);
    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public double getKnockbackRandomValue() {

        return NumberUtils.random(getMinKnockbackValue(), getMaxKnockbackValue());

    }

    /**
     * This function returns a list of doubles that represent the sweep range
     * 
     * @return The sweepRange list is being returned.
     */
    public List<Double> getSweepRange() {
        return sweepRange;
    }

    public double getMinSweepValue() {
        return sweepRange.get(0);
    }

    public double getMaxSweepValue() {
        return sweepRange.get(1);
    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public double getSweepRandomValue() {

        return NumberUtils.random(getMinSweepValue(), getMaxSweepValue());

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

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public Integer getRandomDurabilityValue() {

        return NumberUtils.randomInt(getMinDurabilityValue(), getMaxDurabilityValue());

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

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public double getKnockback() {
        return knockback;
    }

    public double getSweep() {
        return sweep;
    }

    public int getDurability() {
        return durability;
    }
    
}