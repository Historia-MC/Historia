package dev.boooiil.historia.classes;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

/**
 * It takes a class and a variable number of arguments and creates a new
 * instance of the class for each
 * key in the set and puts it in the map
 */
public abstract class Configuration<T> {

    protected FileConfiguration configuration;
    protected Set<String> set;
    protected HashMap<String, T> map;

    public void loadConfiguration(String file) {

        this.configuration = FileGetter.get(file);
        this.set = configuration.getKeys(false);
        this.map = new HashMap<>();

        this.populateMap();

    }

    /**
     * It returns the configuration file
     * 
     * @return The configuration.
     */
    public FileConfiguration getConfiguration() {

        return configuration;

    }

    /**
     * Get a set (unordered list) of all keys described in the configuration.
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
     * This function returns a HashMap of String keys and Object values
     * 
     * @return A HashMap
     */
    public HashMap<String, T> getMap() {

        return map;

    }

    /**
     * If the key is in the configuration.
     * 
     * @param key - Name of the ingot to check.
     * @return If the ingot provided is in ingots.yml.
     */
    public boolean isValid(String key) {

        return set.contains(key);

    }

    /**
     * It takes a class and a variable number of arguments and creates a new
     * instance of the class for
     * each key in the set and puts it in the map
     * 
     * @param TargetObject The class of the object you want to create.
     */
    private void populateMap(Object... args) {

        for (String key : set)
            if (!key.equals("version"))
                map.put(key, createNew(key));

    }

    public abstract T createNew(String name);
    public abstract T getObject(String objectName);
}
