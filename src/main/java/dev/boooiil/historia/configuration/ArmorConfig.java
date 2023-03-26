package dev.boooiil.historia.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.boooiil.historia.classes.Armor;
import dev.boooiil.historia.classes.Configuration;

/**
 * It's a configuration class that extends the Configuration class and adds a
 * method to get armor based
 * on recipe items and shape.
 */
public class ArmorConfig extends Configuration<Armor> {

    public Armor createNew(String armourName) {
        return new Armor(armourName);
    }

    /**
     * Get armor based on recipe items and shape.
     * 
     * @param inputItems List of recipe items.
     * @param inputShape Recipe shape.
     * @return Armor object.
     */
    public Armor getArmor(List<String> inputItems, List<String> inputShape) {

        Armor armor = null;

        for (Map.Entry<String, Armor> entry : map.entrySet()) {

            boolean armorValid = ((Armor) entry.getValue()).isValidRecipe(inputItems, inputShape);

            if (armorValid) {
                armor = (Armor) entry.getValue();
                break;
            }

        }

        return armor;

    }

    /**
     * Get armor based on armor name.
     * 
     * @param armorName Armor name.
     * @return Armor object.
     */
    public Armor getObject(String armorName) {

        if (isValid(armorName))
            return (Armor) map.get(armorName);
        else
            return null;

    }

    /**
     * It checks if the shape of the recipe is valid
     * 
     * @param shape The shape of the recipe.
     * @return A boolean value.
     */
    public boolean validShape(List<String> shape) {

        boolean found = false;

        for(Armor armor : map.values()) {

            if (armor.getRecipeShape().equals(shape)) {

                found = true;
                break;

            }
        }

        return found;

    }

    /**
     * It returns a list of all the recipes for the armor
     * 
     * @return A list of lists of strings.
     */
    public List<List<String>> getAllShapes() {

        List<List<String>> set = new ArrayList<>();

        for(Armor armor : map.values()) {

            set.add(armor.getRecipeShape());
        }

        return set;

    }
}
