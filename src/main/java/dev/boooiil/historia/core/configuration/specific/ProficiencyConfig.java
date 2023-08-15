package dev.boooiil.historia.core.configuration.specific;

import dev.boooiil.historia.core.classes.proficiency.Proficiency;

import java.util.HashMap;
import java.util.Map;

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
