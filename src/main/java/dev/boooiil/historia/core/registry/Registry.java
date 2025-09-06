package dev.boooiil.historia.core.registry;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class Registry<T> extends AbstractMap<NamespacedKey, T> {

    /** The registry holder. */
    private final HashMap<NamespacedKey, T> registry = new HashMap<>();
    /** The type of the registry values. */
    private final Type type;

    /** registry default constructor */
    public Registry(Type type) {
        this.type = type;
    }

    /**
     * Register a new value to the registry.
     * 
     * @param key   The key to register the value under.
     * @param value The value to register.
     */
    public Registry<T> register(NamespacedKey key, T value) {
        registry.put(key, value);
        return this;
    }

    /**
     * Deregister an item configuration from the registry.
     * 
     * @param key The key to deregister.
     */
    public Registry<T> deregister(NamespacedKey key) {
        registry.remove(key);
        return this;
    }

    /**
     * Update a value in the registry.
     * 
     * @param key   The key to update.
     * @param value The new value.
     */
    public Registry<T> update(NamespacedKey key, T value) {
        registry.put(key, value);
        return this;
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

    @Nullable
    @Override
    public T put(NamespacedKey key, T value) {
        return registry.put(key, value);
    }

    @Override
    public Set<Map.Entry<NamespacedKey, T>> entrySet() {
        return registry.entrySet();
    }

    public Type getType() {
        return type;
    }

    /**
     * Create a new registry with the specified type.
     * 
     * @param <T> The type of the registry.
     * @return A new registry instance.
     */
    public static <T> Registry<T> of(TypeToken<T> token) {
        return new Registry<>(token.getType());
    }

}