package dev.boooiil.historia.core.registry;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class Registry<T> {

    /** The registry holder. */
    private HashMap<NamespacedKey, T> registry = new HashMap<>();

    /** registry default constructor */
    public Registry() {
    }

    /**
     * Register a new value to the registry.
     * 
     * @param key           The key to register the value under.
     * @param configuration The value to register.
     */
    public void register(NamespacedKey key, T value) {
        registry.put(key, value);
    }

    /**
     * Deregister an item configuration from the registry.
     * 
     * @param key The key to deregister.
     */
    public void deregister(NamespacedKey key) {
        registry.remove(key);
    }

    /**
     * 
     * @param key
     * @param value
     */

    /**
     * Update a value in the registry.
     * 
     * @param key           The key to update.
     * @param configuration The new value.
     */
    public void update(NamespacedKey key, T value) {
        registry.put(key, value);
    }

    /**
     * Check if the registry contains a value with the given key.
     * 
     * @param key The key to check for.
     * @return True if the registry contains the key, false otherwise.
     */
    public boolean contains(NamespacedKey key) {
        return registry.containsKey(key);
    }

    /**
     * Get a value from the registry.
     * 
     * @param key The key of the value.
     * @return The value.
     */
    @Nullable
    public T get(NamespacedKey key) {
        return registry.get(key);
    }

}