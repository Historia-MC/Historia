package dev.boooiil.historia.classes;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.ArmorConfig;
import dev.boooiil.historia.util.Construct;

/**
 *
 * Constructs specific information from a given armor.
 * 
 * To access the constructor in Armor:
 * 
 * <pre>
 * {
 *     ArmorConfig armorConfig = new ArmorConfig();
 *     ArmorConfig.Armor armor = armorConfig.new Armor(armorName);
 * }
 * </pre>
 *
 */
public class Armor {

    private FileConfiguration configuration = ArmorConfig.getConfiguration();

    private String type;

    private Integer weight;

    private List<Double> defense;
    private List<Integer> durability;
    private List<String> recipeItems;
    private List<String> recipeShape;

    private ItemStack itemStack;

    private boolean valid;

    public Armor(String armorName) {

        this.valid = ArmorConfig.validArmor(armorName);

        if (this.valid) {

            String itemRoot = armorName + ".item";

            this.type = configuration.getString(itemRoot + ".type");

            this.weight = configuration.getInt(itemRoot + ".weight");

            this.defense = configuration.getDoubleList(itemRoot + ".defense");
            this.durability = configuration.getIntegerList(itemRoot + ".durability");
            this.recipeItems = configuration.getStringList(itemRoot + ".recipe-items");
            this.recipeShape = configuration.getStringList(itemRoot + ".recipe-shape");

            this.itemStack = Construct.itemStack(
                    Material.getMaterial(configuration.getString(itemRoot + ".item.type")),
                    configuration.getInt(itemRoot + ".item.amount"),
                    configuration.getString(itemRoot + ".item.display-name"),
                    configuration.getString(itemRoot + ".item.loc-name"),
                    configuration.getStringList(itemRoot + ".item.lore"));

        }

        else
            this.valid = false;

    }

    public String getType() {

        return this.type;

    }

    public Integer getWeight() {

        return this.weight;

    }

    public Double getRandomDefenseValue() {

        Random random = new Random();
        Double result = random.nextDouble() * (getMinDefenceValue() - getMaxDefenceValue()) + getMinDefenceValue();

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

    public Double getMinDefenceValue() {

        return this.defense.get(0);

    }

    public Double getMaxDefenceValue() {

        return this.defense.get(1);

    }

    public Integer getMinDurabilityValue() {

        return this.durability.get(0);

    }

    public Integer getMaxDurabilityValue() {

        return this.durability.get(1);

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
