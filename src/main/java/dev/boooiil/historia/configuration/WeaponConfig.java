package dev.boooiil.historia.configuration;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.util.Construct;
import dev.boooiil.historia.util.FileGetter;

public class WeaponConfig {
    private static FileConfiguration configuration = FileGetter.get("ingots.yml");

    static final Set<String> weaponSet = configuration.getKeys(false);
    
    /**
     *
     * Constructs specific information from a given weapon.
     * 
     * To access the constructor in Weapon:
     * 
     * <pre>
     * {
     *   WeaponConfig weaponConfig = new WeaponConfig();
     *   WeaponConfig.Weapon weapon = weaponConfig.new Weapon(weaponName);
     * }
     * </pre>
     *
     */
    public class Weapon {
        
        private String type;
        private String localizedName;
        private String displayName;
        private List<String> lore;
        private int amount;

        public String weaponType;

        public ItemStack weaponItemStack;

        public List<String> weaponCraftingShape;
        public List<String> weaponCreaftingRecipe;

        public boolean valid;

        public List<Double> weaponDamageRange;
        public List<Double> weaponSpeedRange;
        public List<Double> weaponKnockbackRange;
        public List<Double> weaponSweepRange;

        public List<Integer> weaponDurabilityRange;


        public Weapon(String weaponName) {

            this.valid = validWeapon(weaponName);

            if (valid) {

                String root = weaponName;
                String itemRoot = weaponName + ".item";

                this.type = configuration.getString(itemRoot + ".type");
                this.localizedName = configuration.getString(itemRoot + ".loc-name");
                this.displayName = configuration.getString(itemRoot + ".display-name");
                this.lore = configuration.getStringList(itemRoot + ".lore");
                this.amount = configuration.getInt(itemRoot + ".amount");

                this.weaponItemStack = Construct.itemStack(type, amount, displayName, localizedName, lore);
                this.weaponDamageRange = configuration.getDoubleList(root + ".damage");
                this.weaponKnockbackRange = configuration.getDoubleList(root + ".knockback");
                this.weaponSweepRange = configuration.getDoubleList(root + ".sweeping");
                this.weaponDurabilityRange = configuration.getIntegerList(root + ".durability");

                this.weaponType = configuration.getString(root + ".type");
                this.weaponCraftingShape = configuration.getStringList(root + ".recipe-shape");
                this.weaponCreaftingRecipe = configuration.getStringList(root + ".recipe-items");

            }


        }
    }

    public static Set<String> getWeaponSet() {

        return weaponSet;

    }

    public static boolean validWeapon(String weaponName) {

        return weaponSet.contains(weaponName);

    }
    
}
