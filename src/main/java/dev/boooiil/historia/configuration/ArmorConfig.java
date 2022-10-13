package dev.boooiil.historia.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;
import dev.boooiil.historia.abstractions.Configuration;
import dev.boooiil.historia.classes.Armor;

public class ArmorConfig extends Configuration {

    private static FileConfiguration configuration = FileGetter.get("amror.yml");

    private static final Set<String> armorSet = configuration.getKeys(false);
    private static final HashMap<String, Armor> armorMap = new HashMap<>();

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
