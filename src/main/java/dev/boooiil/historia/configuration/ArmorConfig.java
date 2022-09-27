package dev.boooiil.historia.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.FileGetter;
import dev.boooiil.historia.abstractions.Configuration;
import dev.boooiil.historia.util.Construct;

public class ArmorConfig extends Configuration {

    private static FileConfiguration configuration = FileGetter.get("amror.yml");

    private static final Set<String> armorSet = configuration.getKeys(false);
    private static final HashMap<String, Armor> armorMap = new HashMap<>();

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
    public static class Armor {

        private String type;

        private Integer weight;

        private List<Double> defense;
        private List<Integer> durability;
        private List<String> recipeItems;
        private List<String> recipeShape;

        private ItemStack itemStack;

        private boolean valid;

        public Armor(String armorName) {

            this.valid = validArmor(armorName);

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
                    configuration.getStringList(itemRoot + ".item.lore")
                );

            }

            else this.valid = false;

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
            Integer result = random.nextInt() * (getMinDurabilityValue() - getMaxDurabilityValue()) + getMinDurabilityValue();

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

    public static Set<String> getArmorSet() {

        return armorSet;

    }

    public static boolean validArmor(String armorName) {

        return armorSet.contains(armorName);

    }

    // O(n^2) - cound be problematic as we increase the amount of armors
    public static Armor getArmor(List<String> inputItems, List<String> inputShape) {

        Armor armor = null;

        for (Map.Entry<String, Armor> entry : armorMap.entrySet()) {

            boolean armorValid = entry.getValue().isValidRecipe(inputItems, inputShape);

            if (armorValid) { armor = entry.getValue(); break; }

        }

        return armor;
        
    }

    public static Armor getArmor(String armorName) {

        if (validArmor(armorName)) return armorMap.get(armorName);
        else return null;

    }

}
