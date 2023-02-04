package dev.boooiil.historia.configuration;

import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.util.FileGetter;
import dev.boooiil.historia.util.Logging;

public class ExpiryConfig {

    private static FileConfiguration configuration = FileGetter.get("expiry.yml");

    static final Set<String> foodSet = configuration.getKeys(false);

    /**
     * If the food is valid.
     */
    static boolean validFood;
    
    /**
     * Date of the given food.
     */
    static int expiryDate;

    /**
     * Array of potion effect values. 
     */
    static PotionEffect[] expiryEffect;

    /**
     * Food constructor.
     * 
     * @param foodName - Name of the food to get.
     */
    ExpiryConfig(String foodName) {

        ExpiryConfig.validFood = ExpiryConfig.isValidFood(foodName);

        if (validFood) {

            ExpiryConfig.expiryDate = configuration.getInt(foodName);

        } else {

            ExpiryConfig.expiryDate = configuration.getInt("not-listed");

        }

        for (String effect : configuration.getStringList("effects")) {
 
            Logging.infoToConsole(effect);

        }

    }

    /**
     * Get a set (unordered list) of all foods described in expiry.yml.
     * 
     * 
     * @return Set of all foods described in expiry.yml.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    public static Set<String> getFoodSet() {

        return foodSet;
    }

    /**
     * If the food provided is in the config.yml.
     * 
     * @param foodName - Name of the food to check.
     * @return If the food provided is in the config.yml.
     */
    public static boolean isValidFood(String foodName) {

        return foodSet.contains(foodName);

    }

}
