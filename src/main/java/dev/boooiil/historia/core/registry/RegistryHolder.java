package dev.boooiil.historia.core.registry;

import java.lang.reflect.Type;

import org.bukkit.NamespacedKey;
import org.jspecify.annotations.Nullable;

import dev.boooiil.historia.core.util.CoreLogger;

/**
 * This class serves as a holder for any number of registries to be utilized
 * within the suite of plugins.
 */
public class RegistryHolder {

    private final Registry<Registry<?>> holder = RegistryHolder.generateHolder();

    /**
     * Register a new value to the registry holder.
     * 
     * @param key   The key to register the value under.
     * @param value The value to register.
     */
    public RegistryHolder register(NamespacedKey key, Registry<?> value) {
        holder.register(key, value);
        return this;
    }

    /**
     * Deregister an item configuration from the registry holder.
     * 
     * @param key The key to deregister.
     */
    public RegistryHolder deregister(NamespacedKey key) {
        holder.deregister(key);
        return this;
    }

    /**
     * Update a value in the registry holder.
     * 
     * @param key   The key to update.
     * @param value The new value.
     */
    public RegistryHolder update(NamespacedKey key, Registry<?> value) {
        holder.update(key, value);
        return this;
    }

    /**
     * Get a registry from the registry holder.
     * 
     * This value will be bound to the plugin through its namespace key.
     * 
     * @param <T>  The type of the registry.
     * @param key  The namespaced key of the registry. ie: 'plugin:crafting_recipes'
     * @param type The class of the registry value.
     * @return Registry<T> | null if not found.
     */
    @Nullable
    public <T> Registry<T> get(NamespacedKey key, Type type) {
        return get(holder, key, type);
    }

    /**
     * Get a registry from a provided registry holder.
     * 
     * This value will be bound to the plugin through its namespace key.
     * 
     * @param <T>      The type of the registry.
     * @param registry The registry holder.
     * @param key      The namespaced key of the registry. ie:
     *                 'plugin:crafting_recipes'
     * @param type     The class of the registry value.
     * @return Registry<T> | null if not found.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> Registry<T> get(RegistryHolder registryHolder, NamespacedKey key, Type type) {

        Registry<?> raw = registryHolder.get(key, type);

        if (raw == null) {
            CoreLogger.errorToConsole("The registry with key " + key + " does not exist.");
            return null;
        }

        if (!raw.getType().equals(type)) {
            throw new IllegalArgumentException(
                    "The registry with key " + key + " is not a Registry<" + type.getTypeName()
                            + "> type but is a Registry<"
                            + raw.getType().getTypeName() + ">.");
        }

        return (Registry<T>) raw;
    }

    /**
     * Get a registry from a provided registry of registries.
     * 
     * This value will be bound to the plugin through its namespace key.
     * 
     * @param <T>      The type of the registry.
     * @param registry The registry of registries.
     * @param key      The namespaced key of the registry. ie:
     *                 'plugin:crafting_recipes'
     * @param type     The class of the registry value.
     * @return Registry<T> | null if not found.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> Registry<T> get(Registry<Registry<?>> registry, NamespacedKey key, Type type) {

        Registry<?> raw = registry.get(key);

        if (raw == null) {
            CoreLogger.errorToConsole("The registry with key " + key + " does not exist.");
            return null;
        }

        if (!raw.getType().equals(type)) {
            throw new IllegalArgumentException(
                    "The registry with key " + key + " is not a Registry<" + type.getTypeName()
                            + "> type but is a Registry<"
                            + raw.getType().getTypeName() + ">.");
        }

        return (Registry<T>) raw;
    }

    /**
     * Get the registry holder.
     * 
     * @return The internal registry holder.
     */
    public Registry<Registry<?>> getHolder() {
        return holder;
    }

    /**
     * Generate a new registry holder.
     * 
     * @return A new registry holder.
     */
    @SuppressWarnings("unchecked")
    public static Registry<Registry<?>> generateHolder() {
        return new Registry<>(
                (Class<Registry<?>>) (Class<?>) Registry.class);
    }

}
