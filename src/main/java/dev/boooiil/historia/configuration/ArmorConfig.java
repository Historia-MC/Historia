package dev.boooiil.historia.configuration;

import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.FileGetter;
import dev.boooiil.historia.util.Construct;

public class ArmorConfig {

    private static FileConfiguration configuration = FileGetter.get("amror.yml");

    static final Set<String> armorSet = configuration.getKeys(false);

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
        private String localizedName;
        private String displayName;
        private List<String> lore;
        private int amount;

        public String armorType;

        public ItemStack armorItemStack;

        public List<String> armorCraftingShape;
        public List<String> armorCreaftingRecipe;

        public boolean validarmor;

        public double armorValue;

        public Armor(String armorName) {

            this.validarmor = validArmor(armorName);

            if (validarmor) {

                String root = armorName;
                String itemRoot = armorName + ".item";

                this.type = configuration.getString(itemRoot + ".type");
                this.localizedName = configuration.getString(itemRoot + ".loc-name");
                this.displayName = configuration.getString(itemRoot + ".display-name");
                this.lore = configuration.getStringList(itemRoot + ".lore");
                this.amount = configuration.getInt(itemRoot + ".amount");

                this.armorItemStack = Construct.itemStack(Material.getMaterial(type), amount, displayName, localizedName, lore);

                this.armorValue = configuration.getDouble(root + ".armor");
                this.armorType = configuration.getString(root + ".type");
                this.armorCraftingShape = configuration.getStringList(root + ".recipe-shape");
                this.armorCreaftingRecipe = configuration.getStringList(root + ".recipe-items");

            }

        }
    }

    public static Set<String> getArmorSet() {

        return armorSet;

    }

    public static boolean validArmor(String armorName) {

        return armorSet.contains(armorName);

    }

}
