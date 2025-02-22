
package dev.boooiil.historia.core.configuration;

import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.core.configuration.specific.GeneralConfig;

/**
 * Configuration storage class.
 * 
 */
@NullMarked
public class ConfigurationLoader {

    private static final GeneralConfig generalConfig = new GeneralConfig();

    /**
     * Get the {@link GeneralConfig}.
     * 
     * @return The generalConfig object.
     */
    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    /**
     * Initialize the configuration objects.
     */
    public static void init() {

    }

    /**
     * Reload the configuration objects.
     */
    public static void reload() {

        init();

    }

}
