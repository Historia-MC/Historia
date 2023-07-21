package dev.boooiil.historia.configuration.specific;

import dev.boooiil.historia.classes.items.generic.Ingot;
import dev.boooiil.historia.configuration.BaseConfiguration;

/**
 * It's a configuration class that loads ingots.yml and allows you to get an ingot from it
 */
public class IngotConfig extends BaseConfiguration<Ingot> {

    /**
     * Used to create a new instance of Ingot.
     * 
     * @param ingotName - Name of the ingot to check.
     * @return Returns an Ingot object.
     */
    @Override
    public Ingot createNew(String ingotName) {

        return new Ingot(ingotName);

    }

    /*
     * ************************** STATIC METHODS **************************
     */

    public Ingot getObject(String ingotLocName) {

        if (isValidIngot(ingotLocName)) {

            return (Ingot) map.get(ingotLocName);

        }

        else
            return null;

    }

    /**
     * If the ingot provided is in ingots.yml.
     * 
     * @param ingotName - Name of the ingot to check.
     * @return If the ingot provided is in ingots.yml.
     */
    public boolean isValidIngot(String ingotName) {

        return set.contains(ingotName);

    }

}
