package dev.boooiil.historia.items;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.configuration.WeaponConfig;

public class Weapons extends ItemStack {

    private ItemStack weaponItemStack;
    private ItemMeta weaponItemMeta;

    private boolean validWeapon;
    
    private String weaponName;
    private String weaponType;
    
    private List<String> weaponLore;

    private double weaponDamage;
    private double weaponSpeed;
    private double weaponKnockback;
    private double weaponSweep;

    private int weaponDurability;

    //Construct our class

    Weapons(ItemStack playerItem) {

        super(playerItem);

        this.weaponItemStack = playerItem;
        this.weaponItemMeta = weaponItemStack.getItemMeta();
        this.weaponLore = weaponItemMeta.getLore();
        this.weaponName = weaponItemMeta.getLocalizedName();
        this.validWeapon = WeaponConfig.validWeapon(weaponName);

        if (validWeapon) {

            Damageable playerItemDamageable = (Damageable) playerItem;

            WeaponConfig weaponConfig = new WeaponConfig();
            WeaponConfig.Weapon weapon = weaponConfig.new Weapon(weaponName);

            this.weaponType = weapon.weaponType;
            this.weaponDamage = Double.parseDouble(weaponLore.get(4).substring(8));
            this.weaponSpeed = Double.parseDouble(weaponLore.get(6).substring(7));
            this.weaponKnockback = Double.parseDouble(weaponLore.get(5).substring(11));
            //this.weaponSweep = Double.parseDouble(weaponLore.get(7).substring(7));
            this.weaponDurability = playerItemDamageable.getDamage();

        }
    }

    public boolean isValidWeapon() {

        return validWeapon;

    }

    public String getWeaponType() {

        return weaponType;

    }

    public double getWeaponDamage() {

        return weaponDamage;

    }

    public double getWeaponSpeed() {

        return weaponSpeed;

    }

    public double getWeaponKnockback() {

        return weaponKnockback;

    }

    public double getWeaponSweep() {

        return weaponSweep;

    }

    public int getWeaponDurability() {

        return weaponDurability;

    }

    
    
}
