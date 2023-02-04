package dev.boooiil.historia.configuration;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.classes.Ingot;

public class IngotConfig extends Configuration {

    public IngotConfig() {

        super("ingots.yml", Ingot.class);

    }

    /*
     * ************************** STATIC METHODS **************************
     */

    public Ingot getIngot(String ingotLocName) {

        if (isValidIngot(ingotLocName)) {

            return (Ingot) map.get(ingotLocName);

        }

        else
            return null;

    }

    /**
     * If the ingot provided is in ingots.yml.
     * 
     * @param blockName - Name of the ingot to check.
     * @return If the ingot provided is in ingots.yml.
     */
    public boolean isValidIngot(String ingotName) {

        return set.contains(ingotName);

    }

}
