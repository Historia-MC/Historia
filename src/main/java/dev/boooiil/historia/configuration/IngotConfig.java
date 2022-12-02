package dev.boooiil.historia.configuration;

import java.util.HashMap;
import java.util.Set;

import dev.boooiil.historia.abstractions.Configuration;
import dev.boooiil.historia.classes.Ingot;
import dev.boooiil.historia.util.FileGetter;

public class IngotConfig extends Configuration {

    private static final HashMap<String, Ingot> ingotMap = new HashMap<>();

    public static void init() {

        configuration = FileGetter.get("ingots.yml");
        keySet = configuration.getKeys(false);

        for (String ingot : keySet) {

            if (!ingot.equals("version")) {

                ingotMap.put(ingot, new Ingot(ingot));

            }

        }

    }

    /*
     * ************************** STATIC METHODS **************************
     */

    public static Ingot getIngot(String ingotLocName) {

        if (isValidIngot(ingotLocName)) {

            return ingotMap.get(ingotLocName);

        }

        else
            return null;

    }

    /**
     * Get a set (unordered list) of all items described in ingots.yml.
     * 
     * 
     * @return Set of all ingots described in ingots.yml.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    public static Set<String> getIngotSet() {

        return keySet;

    }

    /**
     * If the ingot provided is in ingots.yml.
     * 
     * @param blockName - Name of the ingot to check.
     * @return If the ingot provided is in ingots.yml.
     */
    public static boolean isValidIngot(String ingotName) {

        return keySet.contains(ingotName);

    }

}
