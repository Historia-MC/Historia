package dev.boooiil.historia.configuration;

import java.util.List;
import java.util.Map;

import dev.boooiil.historia.classes.Armor;
import dev.boooiil.historia.classes.Configuration;

public class ArmorConfig extends Configuration {

    public ArmorConfig() {

        super("armor.yml", Armor.class);

    }

    public Armor getArmor(List<String> inputItems, List<String> inputShape) {

        Armor armor = null;

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            boolean armorValid = ((Armor) entry.getValue()).isValidRecipe(inputItems, inputShape);

            if (armorValid) { armor = (Armor) entry.getValue(); break; }

        }

        return armor;
        
    }

    public Armor getArmor(String armorName) {

        if (isValid(armorName)) return (Armor) map.get(armorName);
        else return null;

    }

}
