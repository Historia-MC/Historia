package dev.boooiil.historia.classes;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.WeaponConfig;
import dev.boooiil.historia.util.Construct;

public class Weapon {

    private WeaponConfig weaponConfig = new WeaponConfig();
    private FileConfiguration configuration = weaponConfig.getConfiguration();
        
    private String type;
    private String localizedName;
    private String displayName;
    private List<String> lore;
    private int amount;

    public String weight;

    public ItemStack itemStack;

    public List<String> recipeShape;
    public List<String> recipeItems;

    public boolean valid;

    public List<Double> damageRange;
    public List<Double> speedRange;
    public List<Double> knockbackRange;
    public List<Double> sweepRange;

    public List<Integer> durabilityRange;


    public Weapon(String weaponName) {

        this.valid = weaponConfig.isValid(weaponName);

        if (valid) {

            String root = weaponName;
            String itemRoot = weaponName + ".item";

            this.weight = configuration.getString(itemRoot + ".type");
            this.localizedName = configuration.getString(itemRoot + ".loc-name");
            this.displayName = configuration.getString(itemRoot + ".display-name");
            this.lore = configuration.getStringList(itemRoot + ".lore");
            this.amount = configuration.getInt(itemRoot + ".amount");

            this.itemStack = Construct.itemStack(Material.getMaterial(type), amount, displayName, localizedName, lore);
            this.damageRange = configuration.getDoubleList(root + ".damage");
            this.knockbackRange = configuration.getDoubleList(root + ".knockback");
            this.sweepRange = configuration.getDoubleList(root + ".sweeping");
            this.durabilityRange = configuration.getIntegerList(root + ".durability");

            this.type = configuration.getString(root + ".type");
            this.recipeItems = configuration.getStringList(root + ".recipe-shape");
            this.recipeShape = configuration.getStringList(root + ".recipe-items");

        }


    }

    public String getType() {

        return this.type;

    }

    public String getWeight() {

        return this.weight;

    }

    public Double getRandomDefenseValue() {

        Random random = new Random();
        Double result = random.nextDouble() * (getMinDamageValue() - getMaxDamageValue()) + getMinDamageValue();

        return result;

    }

    public Integer getRandomDurabilityValue() {

        Random random = new Random();
        Integer result = random.nextInt() * (getMinDurabilityValue() - getMaxDurabilityValue())
                + getMinDurabilityValue();

        return result;

    }

    public List<String> getRecipeItems() {

        return this.recipeItems;

    }

    public List<String> getRecipeShape() {

        return this.recipeShape;

    }

    public Double getMinDamageValue() {

        return this.damageRange.get(0);

    }

    public Double getMaxDamageValue() {

        return this.damageRange.get(1);

    }

    public Integer getMinDurabilityValue() {

        return this.durabilityRange.get(0);

    }

    public Integer getMaxDurabilityValue() {

        return this.durabilityRange.get(1);

    }

    public ItemStack getItemStack() {

        return this.itemStack;

    }

    public Boolean isValidRecipe(List<String> inputItems, List<String> inputShape) {

        boolean validItems = this.recipeItems.equals(inputItems);
        boolean validShape = this.recipeShape.equals(inputShape);

        return validItems && validShape;

    }
}