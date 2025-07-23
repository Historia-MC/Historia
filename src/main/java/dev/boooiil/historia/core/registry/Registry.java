package dev.boooiil.historia.core.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import dev.boooiil.historia.core.util.CoreLogger;

@NullMarked
public class Registry<T> {

    /** The registry holder. */
    private HashMap<NamespacedKey, T> registry = new HashMap<>();
    /** The type of the registry values. */
    private final Type type;

    public static final Registry<Registry<?>> registryHolder = generateHolder();

    /** registry default constructor */
    public Registry(Type type) {
        this.type = type;
    }

    /**
     * Register a new value to the registry.
     * 
     * @param key           The key to register the value under.
     * @param configuration The value to register.
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

    /**
     * Get a registry from the internal registry holder.
     * 
     * This value will be bound to the plugin through its namespace key.
     * 
     * @param <T>  The type of the registry.
     * @param key  The namespaced key of the registry. ie: 'plugin:crafting_recipes'
     * @param type The class of the registry value.
     * @return Registry<T> | null if not found.
     */
    @Nullable
    public static <T> Registry<T> get(NamespacedKey key, Type type) {
        return get(registryHolder, key, type);
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
                    "The registry with key " + key + " is not a Registry<" + type.getTypeName() + "> type.");
        }

        return (Registry<T>) raw;
    }

    public Type getType() {
        return type;
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

    public Set<NamespacedKey> allKeys() {
        return registry.keySet();
    }

    public static <T> Registry<T> of(Registry.TypeToken<T> token) {
        return new Registry<>(token.getType());
    }

    public static abstract class TypeToken<T> {
        private final Type type;

        protected TypeToken() {
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof ParameterizedType) {
                this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
            } else {
                throw new IllegalArgumentException("Missing type parameter for TypeToken");
            }
        }

        public Type getType() {
            return type;
        }

        @Override
        public String toString() {
            return "TypeToken{" + "type=" + type + '}';
        }
    }

}