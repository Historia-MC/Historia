package dev.boooiil.historia.configuration;

import java.util.List;
import java.util.Map;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.classes.Weapon;

/**
 * It's a class that gets information from a configuration file.
 */
public class WeaponConfig extends Configuration {
    
    // private static FileConfiguration configuration = FileGetter.get("ingots.yml");

    // static final Set<String> weaponSet = configuration.getKeys(false);
    
    /**
     *
     * Constructs specific information from a given weapon.
     *
     */

    public WeaponConfig() {

        super("ores.yml", Weapon.class);

    }

    /**
     * It takes a list of items and a list of shapes, and returns a weapon if the items and shapes
     * match a weapon recipe
     * 
     * @param inputItems A list of strings that represent the items used to craft the weapon.
     * @param inputShape A list of strings that represent the shape of the weapon.
     * @return A Weapon object.
     */
    public Weapon getWeapon(List<String> inputItems, List<String> inputShape) {

        Weapon weapon = null;

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            boolean armorValid = ((Weapon) entry.getValue()).isValidRecipe(inputItems, inputShape);

            if (armorValid) { weapon = (Weapon) entry.getValue(); break; }

        }

        return weapon;
        
    }

    /**
     * If the weapon name is valid, return the weapon object from the map. Otherwise, return null
     * 
     * @param weaponName The name of the weapon you want to get.
     * @return A Weapon object.
     */
    public Weapon getWeapon(String weaponName) {

        if (isValid(weaponName)) return (Weapon) map.get(weaponName);
        else return null;

    }
    
    
}
