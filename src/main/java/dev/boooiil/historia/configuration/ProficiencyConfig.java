package dev.boooiil.historia.configuration;

import java.util.HashMap;
import java.util.Map;

import dev.boooiil.historia.classes.historia.proficiency.BaseProficiency;

public class ProficiencyConfig {


    //These are here for testing purposes.
    //Static reference will use less memory than creating a new instance in each HistoriaPlayer

    private static Map<String, BaseProficiency> classConfigMap = new HashMap<String, BaseProficiency>();

    /**
     * Return a preloaded configuration.
     * @param className Provided class name.
     * @return BaseClass associated with the class.
     */
    public static BaseProficiency getConfig(String className) {

        if (classConfigMap.containsKey(className)) return classConfigMap.get(className);
        else {
            
            classConfigMap.put(className, new BaseProficiency(className));
            return classConfigMap.get(className);

        }
    }

    public static void reloadConfig() {

        classConfigMap.clear();

    }
}
