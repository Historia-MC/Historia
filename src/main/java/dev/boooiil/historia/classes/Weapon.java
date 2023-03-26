package dev.boooiil.historia.classes;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.WeaponConfig;
import dev.boooiil.historia.util.Construct;

/**
 * It's a class that represents a weapon in the game.
 */
public class Weapon {

    private FileConfiguration configuration = Config.getWeaponConfig().getConfiguration();
        
    private String type;
    private String localizedName;
    private String displayName;
    private List<String> lore;
    private int amount;

    private String weight;

    private ItemStack itemStack;

    private List<String> recipeShape;

    private List<String> recipeItems;

    private boolean valid;

    private List<Double> damageRange;

    private List<Double> speedRange;

    private List<Double> knockbackRange;

    private List<Double> sweepRange;

    private List<Integer> durabilityRange;

    // It's a constructor.
    public Weapon(String weaponName) {

        this.valid = Config.getWeaponConfig().isValid(weaponName);

        if (valid) {

            String root = weaponName;
            String itemRoot = weaponName + ".item";

            this.weight = configuration.getString(itemRoot + ".type");
            this.localizedName = configuration.getString(itemRoot + ".loc-name");
            this.displayName = configuration.getString(itemRoot + ".display-name");
            this.lore = configuration.getStringList(itemRoot + ".lore");
            this.amount = configuration.getInt(itemRoot + ".amount");

            this.type = configuration.getString(root + ".type");
            this.recipeItems = configuration.getStringList(root + ".recipe-shape");
            this.recipeShape = configuration.getStringList(root + ".recipe-items");

            this.itemStack = Construct.itemStack(Material.getMaterial(type), amount, displayName, localizedName, lore);
            this.damageRange = configuration.getDoubleList(root + ".damage");
            this.knockbackRange = configuration.getDoubleList(root + ".knockback");
            this.sweepRange = configuration.getDoubleList(root + ".sweeping");
            this.durabilityRange = configuration.getIntegerList(root + ".durability");

        }

    }

    /**
     * This function returns the localized name of the item
     * 
     * @return The localized name of the item.
     */
    public String getLocalizedName() {
        return localizedName;
    }

    /**
     * This function returns the display name of the user
     * 
     * @return The displayName is being returned.
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * It returns the lore of the item
     * 
     * @return The lore of the item.
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * This function returns the amount of money in the account
     * 
     * @return The amount of the item.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * This function returns a boolean value that indicates whether the object is valid or not
     * 
     * @return The boolean value of valid.
     */
    public boolean isValid() {
        return valid;
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
     * This function returns a list of integers that represent the durability range of the item
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
    public String getType() {

        return this.type;

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
     * This function returns a random double value between the minimum and maximum damage values
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
     * This function returns the list of items that are required to craft the item
     * 
     * @return The list of recipe items.
     */
    public List<String> getRecipeItems() {

        return this.recipeItems;

    }

    /**
     * It returns the recipe shape
     * 
     * @return The recipe shape.
     */
    public List<String> getRecipeShape() {

        return this.recipeShape;

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

    /**
     * It returns the item stack
     * 
     * @return The itemStack variable.
     */
    public ItemStack getItemStack() {

        return this.itemStack;

    }

    /**
     * "If the input items and input shape are equal to the recipe items and recipe shape, then return
     * true, otherwise return false."
     * 
     * @param inputItems A list of strings that represent the items in the crafting grid.
     * @param inputShape The shape of the recipe.
     * @return A boolean value.
     */
    public Boolean isValidRecipe(List<String> inputItems, List<String> inputShape) {

        boolean validItems = this.recipeItems.equals(inputItems);
        boolean validShape = this.recipeShape.equals(inputShape);

        return validItems && validShape;

    }
}