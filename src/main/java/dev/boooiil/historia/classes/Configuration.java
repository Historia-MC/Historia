package dev.boooiil.historia.classes;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.Main;
import dev.boooiil.historia.util.ConfigUtil;

/**
 * It takes a class and a variable number of arguments and creates a new
 * instance of the class for each
 * key in the set and puts it in the map
 */
public abstract class Configuration<T> {

    protected YamlConfiguration configuration;
    protected Set<String> set;
    protected HashMap<String, Object> map;

    /**
     * It loads a YAML file from the plugin's data folder, and then populates a HashMap with the keys
     * and values from the YAML file
     * 
     * @param fileName The name of the file you want to load.
     */
    public void loadConfiguration(String fileName) {
        
        //@sonatype-lift ignore
        this.configuration = ConfigUtil.yamlFromSource(new File(Main.plugin().getDataFolder(), fileName));
        this.set = configuration.getKeys(false);
        this.map = new HashMap<>();

        this.populateMap();

    }

    /**
     * It returns the configuration file
     * 
     * @return The configuration.
     */
    public YamlConfiguration getConfiguration() {

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
    public HashMap<String, Object> getMap() {

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
