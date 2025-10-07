package dev.boooiil.historia.core.configuration;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.items.HistoriaItem;
import dev.boooiil.historia.core.registry.RegistryHolder;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * <p>
 * This is a utility class that is used to load the item configurations from the
 * plugin's YAML files and register them in the
 * {@link dev.boooiil.historia.core.registry.RegistryHolder RegistryHolder}. It also provides a method to register
 * other plugins' configurations with the registry.
 * </p>
 */
public class ItemRegistryLoader {

    private static List<YamlConfiguration> configurations;

    /**
     * item configuration registry default constructor
     */
    private ItemRegistryLoader() {

    }

    /**
     * Loads the ItemRegistry with the YamlConfigurations provided by
     * the plugin. Other plugins will have to load their own
     * configurations with
     */
    @ApiStatus.Experimental
    public static void load(Supplier<List<YamlConfiguration>> configSupplier) {
        CoreLogger.verboseToConsole("Initializing ItemRegistryLoader...");
        configurations = configSupplier.get();
        populate(configurations);
    }

    /**
     * Loads the ItemRegistry with the YamlConfigurations provided by
     * the plugin. Other plugins will have to load their own
     * configurations with
     */
    public static void load() {
        CoreLogger.verboseToConsole("Initializing ItemRegistryLoader...");
        configurations = FileIO.loadYamlConfigurationsFromPlugins();
        populate(configurations);
    }

    /**
     * <p>
     * Populates the ItemRegistry with the given YamlConfiguration and
     * type. This method assumes that the YAML provided follows the format of:
     * </p>
     * <blockquote>
     *
     * <pre>{@code
     * str_key_1:
     *  configuration:
     *    ...
     * }</pre>
     *
     * </blockquote>
     * <p>
     * ... where str_key is the unique key that will be registered and configuration
     * is the passed {@link ConfigurationSection} to the
     * {@link HistoriaItem#fromConfig(NamespacedKey, ConfigurationSection)}
     * fromConfigurationSection} method.
     * </p>
     *
     * <p>
     * Assuming the overloaded method
     * {@link HistoriaItem#fromConfig(NamespacedKey, ConfigurationSection)}
     * was correctly implemented, this method effectively runs as shown:
     * </p>
     *
     * <blockquote>
     *
     * <pre>
     * {@code
     *
     * for (key in configuration keys) {
     *   ConfigurationSection section = configuration.getConfigurationSection(key);
     *   ProvidedConfiguration result = ProvidedConfiguration.fromConfigurationSection(section);
     * }
     *
     * }</pre>
     *
     * </blockquote>
     * <p>
     * This method is unsafe and will throw an exception if the method
     * {@link HistoriaItem#fromConfig(NamespacedKey, ConfigurationSection)}
     * does not exist or is not static. It is up to you to provide functionality
     * within your configuration classes to handle the configuration section.
     *
     * @param configurations The YamlConfiguration to populate the registry with.
     */
    public static void populate(List<YamlConfiguration> configurations) {

        for (YamlConfiguration configuration : configurations) {

            Set<String> keys = configuration.getKeys(false);

            for (String key : keys) {

                CoreLogger.verboseToConsole("Item Registry Key", key);

                NamespacedKey namespacedKey = HistoriaCore.getNamespacedKey(key);
                ConfigurationSection section = configuration.getConfigurationSection(key);

                assert section != null;

                CoreLogger.infoToConsole("Item Registry Section", key);
                RegistryHolder.ITEM_REGISTRY.register(namespacedKey,
                        HistoriaItem.fromConfig(namespacedKey, section));

            }

        }
        // throw new Error("Unimplemented method.");

    }

}
