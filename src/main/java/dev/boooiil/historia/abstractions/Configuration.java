package dev.boooiil.historia.abstractions;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class Configuration {
    
    private static FileConfiguration configuration;

    public static FileConfiguration getConfiguration() {

        return configuration;

    }

}
