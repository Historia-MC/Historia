package dev.boooiil.historia.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.classes.Weapon;

/**
 * It's a class that gets information from a configuration file.
 */
public class WeaponConfig extends Configuration<Weapon> {
    
    // private static FileConfiguration configuration = FileGetter.get("ingots.yml");

    // static final Set<String> weaponSet = configuration.getKeys(false);

    /**
     * Used to create a new instance of Weapon.
     * 
     * @param weaponName - Name of the weapon to create.
     * @return Returns an Weapon object.
     */
    public Weapon createNew(String weaponName) {

        return new Weapon(weaponName);

    }



    /**
     * It takes a list of items and a list of shapes, and returns a weapon if the items and shapes
     * match a weapon recipe
     * 
     * @param inputItems A list of strings that represent the items used to craft the weapon.
     * @param inputShape A list of strings that represent the shape of the weapon.
     * @return A Weapon object.
     */
    public Weapon getObject(List<String> inputItems, List<String> inputShape) {

        Weapon weapon = null;

        for (Map.Entry<String, Weapon> entry : map.entrySet()) {

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
    public Weapon getObject(String weaponName) {

        if (isValid(weaponName)) return (Weapon) map.get(weaponName);
        else return null;

    }

        /**
     * It checks if the shape of the recipe is valid
     * 
     * @param shape The shape of the recipe.
     * @return A boolean value.
     */
    public boolean validShape(List<String> shape) {

        boolean found = false;

        for(Weapon weapon : map.values()) {

            if (weapon.getRecipeShape().equals(shape)) {

                found = true;
                break;

            }
        }

        return found;

    }

    /**
     * It returns a list of all the recipes for the weapons
     * 
     * @return A list of lists of strings.
     */
    public List<List<String>> getAllShapes() {

        List<List<String>> set = new ArrayList<>();

        for(Weapon weapon : map.values()) {

            set.add(weapon.getRecipeShape());
        }

        return set;

    }

    /**
     * It takes a list of strings, and returns a list of weapons that have the same recipe shape as the
     * list of strings
     * 
     * @param shape List<String>
     * @return A list of weapons that match the shape.
     */
    public List<Weapon> getAllMatchingShape(List<String> shape) {

        List<Weapon> set = new ArrayList<>();

        for(Weapon armor : map.values()) {

            if (armor.getRecipeShape().equals(shape)) {

                set.add(armor);

            }
        }

        return set;

    }
}