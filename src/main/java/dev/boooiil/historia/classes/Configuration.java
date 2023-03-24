package dev.boooiil.historia.classes;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

public class Configuration {

    protected FileConfiguration configuration;
    protected Set<String> set;
    protected HashMap<String, Object> map;

    // Abstracts aren't technically supposed to have constructors
    // I think, oh well.
    public <T> Configuration(String file, Class<T> targetObject) {

        this.configuration = FileGetter.get(file);
        this.set = configuration.getKeys(false);
        this.map = new HashMap<>();

        this.populateMap(targetObject);

    }

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

    private <T> void populateMap(Class<T> TargetObject, Object... args) {

        for (String key : set) {

            if (!key.equals("version")) {

                try {

                    map.put(key, TargetObject.getDeclaredConstructor().newInstance(args));

                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


        }

    }
}
