package dev.boooiil.historia.configuration;

import java.util.List;
import java.util.Map;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.classes.Weapon;

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

    public Weapon getWeapon(List<String> inputItems, List<String> inputShape) {

        Weapon weapon = null;

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            boolean armorValid = ((Weapon) entry.getValue()).isValidRecipe(inputItems, inputShape);

            if (armorValid) { weapon = (Weapon) entry.getValue(); break; }

        }

        return weapon;
        
    }

    public Weapon getWeapon(String weaponName) {

        if (isValid(weaponName)) return (Weapon) map.get(weaponName);
        else return null;

    }
    
    
}
