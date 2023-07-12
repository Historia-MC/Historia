package dev.boooiil.historia.configuration;

import java.util.HashMap;
import java.util.Map;

import dev.boooiil.historia.classes.historia.proficiency.Proficiency;

public class ProficiencyConfig {

    private static Map<String, Proficiency> proficiencyMap = new HashMap<String, Proficiency>();

    /**
     * Return a preloaded configuration.
     * @param proficiencyName Provided proficiency name.
     * @return BaseClass associated with the class.
     */
    public static Proficiency getConfig(String proficiencyName) {

        if (proficiencyMap.containsKey(proficiencyName)) return proficiencyMap.get(proficiencyName);
        else {
            
            proficiencyMap.put(proficiencyName, new Proficiency(proficiencyName));
            return proficiencyMap.get(proficiencyName);

        }
    }

    public static void reloadConfig() {

        proficiencyMap.clear();

    }
}
