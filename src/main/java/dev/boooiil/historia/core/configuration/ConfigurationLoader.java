package dev.boooiil.historia.core.configuration;

import dev.boooiil.historia.core.configuration.specific.GeneralConfig;
import dev.boooiil.historia.core.configuration.specific.TemperatureConfig;
import dev.boooiil.historia.core.util.CoreLogger;
import org.jspecify.annotations.NullMarked;

/**
 * Configuration storage class.
 *
 */
@NullMarked
public class ConfigurationLoader {

    private static final GeneralConfig generalConfig = new GeneralConfig();
    private static TemperatureConfig temperatureConfig;

    /**
     * Get the {@link GeneralConfig}.
     *
     * @return The generalConfig object.
     */
    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }


    public static TemperatureConfig getTemperatureConfig() {
        if (temperatureConfig == null) {
            CoreLogger.errorToConsole("Temperature config was null, stopping the plugin.");
            return null;
        }
        return temperatureConfig;
    }

    /**
     * Initialize the configuration objects.
     */
    public static void init() {
        try {
            temperatureConfig = new TemperatureConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload the configuration objects.
     */
    public static void reload() {

        init();

    }

}
