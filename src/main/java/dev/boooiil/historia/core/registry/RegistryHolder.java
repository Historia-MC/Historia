package dev.boooiil.historia.core.registry;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import dev.boooiil.historia.core.util.CoreLogger;

/**
 * This class serves as a holder for any number of registries to be utilized
 * within the suite of plugins.
 */
@NullMarked
public class RegistryHolder extends AbstractMap<NamespacedKey, Registry<?>> {

    private final Registry<Registry<?>> holder = RegistryHolder.generateHolder();

    /**
     * Register a new value to the registry holder.
     * 
     * @param key   The key to register the value under.s
     * @param value The value to register.
     */
    public <T> Registry<T> register(NamespacedKey key, Registry<T> value) {
        CoreLogger.traceToConsole(
                "Registering " + key + " to registry holder with type " + value.getType().getTypeName());
        holder.register(key, value);
        return value;
    }

    @SuppressWarnings("unchecked")
    public <T> Registry<T> put(NamespacedKey key, Registry<T> value) {
        CoreLogger.traceToConsole("Putting " + key + " into registry of type " + value.getType());
        return (Registry<T>) holder.put(key, value);
    }


    /**
     * Deregister an item configuration from the registry holder.
     * 
     * @param key The key to deregister.
     */
    public RegistryHolder deregister(NamespacedKey key) {
        CoreLogger.traceToConsole("Deregistering " + key + " from registry holder");
        holder.deregister(key);
        return this;
    }

    /**
     * Update a value in the registry holder.
     * 
     * @param key   The key to update.
     * @param value The new value.
     */
    public <T> RegistryHolder update(NamespacedKey key, Registry<T> value) {
        CoreLogger.traceToConsole(
                "Updating " + key + " in registry holder with type " + value.getType().getTypeName());
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
        CoreLogger.traceToConsole("Getting " + key + " from registry holder with type " + type.getTypeName());
        return get(holder, key, type);
    }

    /**
     * Get a registry from a provided registry holder.
     * 
     * This value will be bound to the plugin through its namespace key.
     * 
     * @param <T>            The type of the registry.
     * @param registryHolder The registry holder.
     * @param key            The namespaced key of the registry. ie:
     *                       'plugin:crafting_recipes'
     * @param type           The class of the registry value.
     * @return Registry<T> | null if not found.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> Registry<T> get(RegistryHolder registryHolder, NamespacedKey key, Type type) {

        CoreLogger.traceToConsole("Getting " + key + " from registry holder with type " + type.getTypeName());

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

        return (Registry<@NotNull T>) raw;
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

        CoreLogger.traceToConsole("Getting " + key + " from registry of registries with type " + type.getTypeName());

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

        return (Registry<@NotNull T>) raw;
    }

    /**
     * Get the registry holder.
     * 
     * @return The internal registry holder.
     */
    public Registry<Registry<?>> getHolder() {
        CoreLogger.traceToConsole("Getting internal registry holder");
        return holder;
    }

    /**
     * Generate a new registry holder.
     * 
     * @return A new registry holder.
     */
    @SuppressWarnings("unchecked")
    public static Registry<Registry<?>> generateHolder() {
        CoreLogger.traceToConsole("Generating new registry holder");
        return new Registry<>(
                (Class<Registry<?>>) (Class<?>) Registry.class);
    }

    @Override
    public Set<Entry<NamespacedKey, Registry<?>>> entrySet() {
        return this.holder.entrySet();
    }
}
