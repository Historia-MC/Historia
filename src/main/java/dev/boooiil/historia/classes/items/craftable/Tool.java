package dev.boooiil.historia.classes.items.craftable;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.util.Construct;
import dev.boooiil.historia.util.NumberUtils;

/**
 * It's a class that represents tools in the game.
 */
public class Tool extends CraftedItem {

    private String weightClass;

    private String weight;

    private List<Double> damageRange;
    private double damage;

    private List<Double> speedRange;
    private double speed;

    private List<Double> knockbackRange;
    private double knockback;

    private List<Integer> durabilityRange;
    private int durability;

    private ItemStack itemStack;

    // It's a constructor.
    public Tool(String toolName) {

        YamlConfiguration configuration = ConfigurationLoader.getToolConfig().getConfiguration();

        valid = ConfigurationLoader.getToolConfig().isValid(toolName);

        if (valid) {

            this.name = toolName;

            String root = toolName;

            // It's calling the parent class's constructor.
            itemStack = Construct.itemStack(
                    configuration.getString(toolName + ".item.type"),
                    configuration.getInt(toolName + ".item.amount"),
                    configuration.getString(toolName + ".item.display-name"),
                    configuration.getString(toolName + ".item.loc-name"),
                    configuration.getStringList(toolName + ".item.lore"));

            this.recipeItems = configuration.getStringList(root + ".recipe-items");
            this.recipeShape = configuration.getStringList(root + ".recipe-shape");
            this.proficiencies = configuration.getStringList(root + ".canCraft");

            this.weightClass = configuration.getString(root + ".type");
            this.damageRange = configuration.getDoubleList(root + ".damage");
            this.speedRange = configuration.getDoubleList(root + ".speed");
            this.knockbackRange = configuration.getDoubleList(root + ".knockback");
            this.durabilityRange = configuration.getIntegerList(root + ".durability");

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

        Random random = new Random();
        double result = random.nextDouble() * (getMinDamageValue() - getMaxDamageValue())
                + getMaxDamageValue();

        return result;

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

        Random random = new Random();
        double result = random.nextDouble() * (getMinSpeedValue() - getMaxSpeedValue())
                + getMaxSpeedValue();

        return result;

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

        Random random = new Random();
        double result = random.nextDouble() * (getMinKnockbackValue() - getMaxKnockbackValue())
                + getMaxKnockbackValue();

        return result;

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

        Integer result = (int) NumberUtils.random(getMinDurabilityValue(), getMaxDurabilityValue());

        return result;

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

    public int getDurability() {
        return durability;
    }
    

}