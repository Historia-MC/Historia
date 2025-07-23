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
