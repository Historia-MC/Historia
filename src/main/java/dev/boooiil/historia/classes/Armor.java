package dev.boooiil.historia.classes;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.configuration.ArmorConfig;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.definitions.ArmorTypes;
import dev.boooiil.historia.util.Construct;

/**
 *
 * Constructs specific information from a given armor.
 *
 */
public class Armor {

    private FileConfiguration configuration = Config.getArmorConfig().getConfiguration();

    private ArmorTypes type;

    private Integer weight;

    private List<Double> defense;
    private List<Integer> durability;
    private List<String> recipeItems;
    private List<String> recipeShape;

    private ItemStack itemStack;

    private boolean valid;

    /**
     * Class constructor
     * @param armorName - Name of the armor
     */
    public Armor(String armorName) {

        this.valid = Config.getArmorConfig().isValid(armorName);

        if (this.valid) {

            String itemRoot = armorName + ".item";

            this.type = ArmorTypes.valueOf(configuration.getString(itemRoot + ".type"));

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

    /**
     * Get the type (weight class) of armor.
     * @return Type of armor.
     */
    public ArmorTypes getType() {

        return this.type;

    }

    /**
     * Get the weight of the armor.
     * @return Weight of the armor.
     */
    public Integer getWeight() {

        return this.weight;

    }

    /**
     * Get the randomized defense value of the armor.
     * @return Defence value.
     */
    public Double getRandomDefenseValue() {

        Random random = new Random();
        Double result = random.nextDouble() * (getMinDefenceValue() - getMaxDefenceValue()) + getMinDefenceValue();

        return result;

    }

    /**
     * Get the randomized durability value of the armor.
     * @return Durability value.
     */
    public Integer getRandomDurabilityValue() {

        Random random = new Random();
        Integer result = random.nextInt() * (getMinDurabilityValue() - getMaxDurabilityValue())
                + getMinDurabilityValue();

        return result;

    }

    /**
     * Get the recipe items needed to complete this recipe.
     * @return List of recipe items.
     */
    public List<String> getRecipeItems() {

        return this.recipeItems;

    }

    /**
     * Get the recipe shape needed to complete this recipe.
     * <p>List is denoted as:
     * 
     *   <p>| X  X  X|
     *   <p>| X  X  X|
     *   <p>| X  X  X|
     * 
     * <p>... where each row is a corresponding list item.
     * @return Recipe shape.
     */
    public List<String> getRecipeShape() {

        return this.recipeShape;

    }

    /**
     * Get the minimum base defense value of the armor.
     * @return Defense value.
     */
    public Double getMinDefenceValue() {

        return this.defense.get(0);

    }

    /**
     * Get the maximum base defense value of the armor.
     * @return Defence value.
     */
    public Double getMaxDefenceValue() {

        return this.defense.get(1);

    }

    /**
     * Get the minimum base durability value of the armor.
     * @return Durability value.
     */
    public Integer getMinDurabilityValue() {

        return this.durability.get(0);

    }

    /**
     * Get the minimum base durability value of the armor.
     * @return Durability value.
     */
    public Integer getMaxDurabilityValue() {

        return this.durability.get(1);

    }

    /**
     * Get the item corresponding to this armor.
     * @return {@link ItemStack}
     */
    public ItemStack getItemStack() {

        return this.itemStack;

    }

    /**
     * Validate recipe of items to see if it matches an armor.
     * @param inputItems List of recipe items.
     * @param inputShape Recipe shape.
     * 
     * @return If the recipe is valid.
     */
    public Boolean isValidRecipe(List<String> inputItems, List<String> inputShape) {

        boolean validItems = this.recipeItems.equals(inputItems);
        boolean validShape = this.recipeShape.equals(inputShape);

        return validItems && validShape;

    }
}
