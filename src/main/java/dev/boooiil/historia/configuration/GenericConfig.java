package dev.boooiil.historia.configuration;

import dev.boooiil.historia.classes.Configuration;

public abstract class GenericConfig<T> {
    
    // In this case, T is Armor


    public static GenericConfiguration<T> config;
    protected String configName;

    public void init() {
        config = new Config(configName);
    }

    public static T getObject(String armorName) {
        return config.getArmor(armorName);
    }

    public static FileConfiguration getConfiguration() {

        return config.getConfiguration();

    }










    public T getObject(String objectName) {

    }


}
