package dev.boooiil.historia.core.configuration;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.file.FileIO;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jspecify.annotations.NullMarked;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

/**
 * Super class of the Configuration classes.
 */
@NullMarked
public abstract class BaseConfiguration<T> {

    /**
     * The {@link YamlConfiguration} file of this configuration.
     */
    protected YamlConfiguration configuration;

    /*
     * The set of keys in the file.
     */
    protected Set<String> set;

    /**
     * A map of keys and their objects.
     */
    protected HashMap<String, T> map;

    /**
     * Load the provided YAML file and populate the map with its values.
     * 
     * @param fileName The name of the file you want to load.
     */
    public void loadConfiguration(String fileName) {

        // @sonatype-lift ignore
        this.configuration = FileIO.yamlFromSource(new File(HistoriaCore.Companion.getInstance().getDataFolder(), fileName));
        this.set = configuration.getKeys(false);
        this.map = new HashMap<>();

        this.populateMap();

    }

    /**
     * Get the {@link YamlConfiguration} for this configuration.
     * 
     * @return The configuration.
     */
    public YamlConfiguration getConfiguration() {

        return configuration;

    }

    /**
     * Get a {@link Set} of all top-level keys for this configuration.
     * 
     * 
     * @return Set of all keys described in the configuration.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    public Set<String> getSet() {

        return set;

    }

    /**
     * Get the HashMap representation of this configuration.
     * <p>
     * The objects T of this configuration are stored by the top level key provided
     * by the {@link #getSet()}.
     * 
     * 
     * @return A HashMap
     */
    public HashMap<String, T> getMap() {

        return map;

    }

    /**
     * Check if the key is within the configuration's set.
     * 
     * @param key - Name of the object to check.
     * @return If the object is provided in the configuration.
     */
    public boolean isValid(String key) {

        return set.contains(key);

    }

    /**
     * Populate the current map with the objects provided by the configuration file.
     *
     */
    private void populateMap() {

        for (String key : set)
            if (!key.equals("version"))
                map.put(key, createNew(key));

    }

    /**
     * Create a new object T for the given key.
     * 
     * @param name - The key to use for the new object.
     * @return The newly created object.
     */
    public abstract T createNew(String name);

    /**
     * Get an object from the configuration by its key.
     * 
     * @param objectName - The name of the object to retrieve.
     * @return The retrieved object.
     */

    public abstract T getObject(String objectName);
}
