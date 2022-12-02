package dev.boooiil.historia.abstractions;

import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {
    
    protected static FileConfiguration configuration;

    protected static Set<String> keySet;

    public static FileConfiguration getConfiguration() {

        return configuration;

    }

}
