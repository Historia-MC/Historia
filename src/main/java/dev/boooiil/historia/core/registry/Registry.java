package dev.boooiil.historia.core.registry;

import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@NullMarked
public class Registry<T> extends AbstractMap<NamespacedKey, T> implements JSONSerializable {

    /**
     * The registry holder.
     */
    private final HashMap<NamespacedKey, T> registry = new HashMap<>();
    /**
     * The type of the registry values.
     */
    private final Type type;

    /**
     * registry default constructor
     */
    public Registry(Type type) {
        CoreLogger.traceToConsole("Creating registry of type " + type.getTypeName());
        this.type = type;
    }

    /**
     * Register a new value to the registry.
     *
     * @param key   The key to register the value under.
     * @param value The value to register.
     */
    public Registry<T> register(NamespacedKey key, T value) {
        CoreLogger.traceToConsole("Registering " + key + " to registry of type " + type.getTypeName());
        registry.put(key, value);
        return this;
    }

    /**
     * Deregister an item configuration from the registry.
     *
     * @param key The key to deregister.
     */
    public Registry<T> deregister(NamespacedKey key) {
        CoreLogger.traceToConsole("Deregistering " + key + " from registry of type " + type.getTypeName());
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
        CoreLogger.traceToConsole("Updating " + key + " in registry of type " + type.getTypeName());
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
        CoreLogger.traceToConsole("Checking if registry of type " + type.getTypeName() + " contains " + key);
        return registry.containsKey(key);
    }

    @Override
    public T put(NamespacedKey key, T value) {
        CoreLogger.traceToConsole("Putting " + key + " into registry of type " + type.getTypeName());
        return registry.put(key, value);
    }

    @Override
    public Set<Map.Entry<NamespacedKey, T>> entrySet() {
        CoreLogger.traceToConsole("Getting entry set of registry of type " + type.getTypeName());
        return registry.entrySet();
    }

    public Type getType() {
        CoreLogger.traceToConsole("Getting type of registry of type " + type.getTypeName());
        return type;
    }

    /**
     * Create a new registry with the specified type.
     *
     * @param <T> The type of the registry.
     * @return A new registry instance.
     */
    public static <T> Registry<T> of(TypeToken<T> token) {
        CoreLogger.traceToConsole("Creating registry of type " + token.getType().getTypeName());
        return new Registry<>(token.getType());
    }

    @Override
    public String toJSON() {

        return "{" +
                JSONUtils.fromMap(this.type.getTypeName(), this.registry) +
                "}";
    }
}