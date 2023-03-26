package dev.boooiil.historia.configuration;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.classes.Armor;
import dev.boooiil.historia.classes.Configuration;

/**
 * It's a configuration class that extends the Configuration class and adds a method to get armor based
 * on recipe items and shape.
 */
public class ArmorConfig {

    public static Config config;

    public void init() {
        config = new Config();
    }

    public static Armor getArmor(String armorName) {
        return config.getArmor(armorName);
    }

    public static FileConfiguration getConfiguration() {

        return config.getConfiguration();

    }

    protected class Config extends Configuration<Armor> {

        public Config() {

            super("armor.yml");
    
        }
    
        public Armor createNew(String armourName) {
            return new Armor(armourName);
        }
    
        /**
         * Get armor based on recipe items and shape.
         * @param inputItems List of recipe items.
         * @param inputShape Recipe shape.
         * @return Armor object.
         */
        public Armor getArmor(List<String> inputItems, List<String> inputShape) {
    
            Armor armor = null;
    
            for (Map.Entry<String, Object> entry : map.entrySet()) {
    
                boolean armorValid = ((Armor) entry.getValue()).isValidRecipe(inputItems, inputShape);
    
                if (armorValid) { armor = (Armor) entry.getValue(); break; }
    
            }
    
            return armor;
            
        }
        
        /**
         * Get armor based on armor name.
         * @param armorName Armor name.
         * @return Armor object.
         */
        public Armor getArmor(String armorName) {
    
            if (isValid(armorName)) return (Armor) map.get(armorName);
            else return null;
    
        }
    }

}

