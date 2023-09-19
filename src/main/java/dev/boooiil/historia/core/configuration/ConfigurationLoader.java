/**
 * The ConfigurationLoader class loads and provides access to various configuration files for the Historia plugin.
 * It contains static methods to retrieve instances of specific configuration classes, such as ArmorConfig,
 * IngotConfig, OreConfig, WeaponConfig, GeneralConfig, CustomItemConfig, and CropConfig. It also provides
 * methods to initialize and reload the configuration files.
 * The configuration files are loaded from the plugin's resources folder and are in YAML format.
 * 
 * @see ArmorConfig
 * @see IngotConfig
 * @see OreConfig
 * @see WeaponConfig
 * @see GeneralConfig
 * @see CustomItemConfig
 * @see CropConfig
 */
package dev.boooiil.historia.core.configuration;

import dev.boooiil.historia.core.configuration.specific.GeneralConfig;

/**
 * It loads the configuration files.
 */
public class ConfigurationLoader {
    
    private static final GeneralConfig generalConfig = new GeneralConfig();

    /**
     * This function returns the generalConfig object
     * 
     * @return The generalConfig object.
     */
    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    /**
     * It loads the configuration files
     */
    public static void init() {
        
    }
    
    /**
     * It's a function that reloads the config file
     */
    public static void reload() {

        init();

    }
    
}
