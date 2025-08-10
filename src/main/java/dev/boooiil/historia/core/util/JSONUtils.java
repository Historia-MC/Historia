package dev.boooiil.historia.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NullMarked;

import net.kyori.adventure.text.Component;

/**
 * Utility class for converting various data types to JSON format.
 */
@NullMarked
public class JSONUtils {

    private static final Map<Class<?>, BiFunction<String, List<?>, String>> TYPE_HANDLERS = new HashMap<>();

    static {
        TYPE_HANDLERS.put(Integer.class, castAndHandle(JSONUtils::fromIntegerList));
        TYPE_HANDLERS.put(Float.class, castAndHandle(JSONUtils::fromFloatList));
        TYPE_HANDLERS.put(Long.class, castAndHandle(JSONUtils::fromLongList));
        TYPE_HANDLERS.put(Double.class, castAndHandle(JSONUtils::fromDoubleList));
        TYPE_HANDLERS.put(Boolean.class, castAndHandle(JSONUtils::fromBooleanList));
        TYPE_HANDLERS.put(String.class, castAndHandle(JSONUtils::fromStringList));
        TYPE_HANDLERS.put(Component.class, castAndHandle(JSONUtils::fromComponentList));
        TYPE_HANDLERS.put(PotionEffect.class, castAndHandle(JSONUtils::fromPotionEffectList));
        TYPE_HANDLERS.put(JSONSerializable.class, castAndHandle(JSONUtils::handleJSONSerializableList));
    }

    @SuppressWarnings("unchecked")
    private static <T> BiFunction<String, List<?>, String> castAndHandle(
            BiFunction<String, List<T>, String> handler) {
        return (key, list) -> handler.apply(key, (List<T>) list);
    }

    /** idfk */
    private static String handleJSONSerializableList(String key, List<JSONSerializable> values) {
        return fromJSONSerializableList(key, values);
    }

    /**
     * Convert into a valid "key": value JSON pair.
     * 
     * @param key   the key of the pair.
     * @param value the value of the pair.
     * @return a "key": value pair.
     */
    public static String fromValue(String key, Integer value) {
        return "\"" + key + "\":" + value;
    }

    /**
     * Convert into a valid "key": value JSON pair.
     * 
     * @param key   the key of the pair.
     * @param value the value of the pair.
     * @return a "key": value pair.
     */
    public static String fromValue(String key, Float value) {
        return "\"" + key + "\":" + value;
    }

    /**
     * Convert into a valid "key": value JSON pair.
     * 
     * @param key   the key of the pair.
     * @param value the value of the pair.
     * @return a "key": value pair.
     */
    public static String fromValue(String key, Long value) {
        return "\"" + key + "\":" + value;
    }

    /**
     * Convert into a valid "key": value JSON pair.
     * 
     * @param key   the key of the pair.
     * @param value the value of the pair.
     * @return a "key": value pair.
     */
    public static String fromValue(String key, Double value) {
        return "\"" + key + "\":" + value;
    }

    /**
     * Convert into a valid "key": value JSON pair.
     * 
     * @param key   the key of the pair.
     * @param value the value of the pair.
     * @return a "key": value pair.
     */
    public static String fromValue(String key, Boolean value) {
        return "\"" + key + "\":" + value;
    }

    /**
     * Convert into a valid "key": value JSON pair.
     * 
     * @param key   the key of the pair.
     * @param value the value of the pair.
     * @return a "key": value pair.
     */
    public static String fromValue(String key, String value) {
        return "\"" + key + "\":" + "\"" + value + "\"";
    }

    public static String fromValue(String key, JSONSerializable value) {
        return fromValue(key, value, false);
    }

    public static String fromValue(String key, JSONSerializable value, boolean asString) {
        return asString ? "\"" + key + "\":" + value.toString() : "\"" + key + "\":" + value.toJSON();
    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param T      The type of the item in the list.
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static <T> String fromList(String key, List<T> values) {
        if (values.isEmpty()) {
            return "\"" + key + "\": []";
        }

        Class<?> type = values.get(0).getClass();
        BiFunction<String, List<?>, String> handler = null;

        if (values.get(0) instanceof JSONSerializable) {
            handler = TYPE_HANDLERS.get(JSONSerializable.class);
        } else {
            handler = TYPE_HANDLERS.get(type);
        }

        if (handler != null) {
            return handler.apply(key, values);
        }

        throw new IllegalArgumentException("Key " + key + " provided a list type of " + type.getName()
                + " which does not have a configured handler.");
    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromIntegerList(String key, List<Integer> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromFloatList(String key, List<Float> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromLongList(String key, List<Long> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromDoubleList(String key, List<Double> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromBooleanList(String key, List<Boolean> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromStringList(String key, List<String> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append("\"" + values.get(i) + "\", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromComponentList(String key, List<Component> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append("\"" + KyoriUtils.content(values.get(i)) + "\", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromPotionEffectList(String key, List<PotionEffect> values) {
        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append("{");
            sb.append(fromValue("type", values.get(i).getType().getKey().toString()) + ", ");
            sb.append(fromValue("duration", values.get(i).getAmplifier()) + ", ");
            sb.append(fromValue("amplifier", values.get(i).getDuration()));
            sb.append("}, ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromJSONSerializableList(String key, List<JSONSerializable> values) {
        return fromJSONSerializableList(key, values, false);
    }

    /**
     * Convert a list into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromJSONSerializableList(String key, List<JSONSerializable> values, boolean asString) {
        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            JSONSerializable j_value = values.get(i);

            if (!asString)
                sb.append(j_value.getClass().getSimpleName() + j_value.toJSON() + ", ");
            else
                sb.append(j_value.toJSON() + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }

    public static <T> String fromSet(String key, Set<T> values) {
        return fromList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromIntegerSet(String key, Set<Integer> values) {
        return fromIntegerList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromFloatSet(String key, Set<Float> values) {
        return fromFloatList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromLongSet(String key, Set<Long> values) {
        return fromLongList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromDoubleSet(String key, Set<Double> values) {
        return fromDoubleList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromBooleanSet(String key, Set<Boolean> values) {
        return fromBooleanList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromStringSet(String key, Set<String> values) {
        return fromStringList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromComponentSet(String key, Set<Component> values) {
        return fromComponentList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromPotionEffectSet(String key, Set<PotionEffect> values) {
        return fromPotionEffectList(key, new ArrayList<>(values));
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromJSONSerializableSet(String key, Set<JSONSerializable> values) {
        return fromJSONSerializableSet(key, values, false);
    }

    /**
     * Convert a set into a valid "key": [ ...value ] JSON pair.
     * 
     * <pre>
     * "SampleKey": [ v1, v2, ... ]
     * </pre>
     * 
     * @param key    the key of the pair.
     * @param values the value(s) of the pair.
     * @return a "key": [ ...value ] pair.
     */
    public static String fromJSONSerializableSet(String key, Set<JSONSerializable> values, boolean asString) {
        return fromJSONSerializableList(key, new ArrayList<>(values), asString);
    }

    /**
     * Convert a map into a valid "key": { "key1": value, "key2": value }
     * 
     * @param <K>    - Name of the key.
     * @param <V>    - Value to append to the key.
     * @param key    - Key to set this object to.
     * @param values - Values to serialize.
     * @return - a "key": { "key1": value, "key2": value } pair
     */
    public static <K, V> String fromMap(String key, Map<K, V> values) {
        return fromMap(key, values, false);
    }

    /**
     * Convert a map into a valid "key": { "key1": value, "key2": value }
     * 
     * @param <K>    - Name of the key.
     * @param <V>    - Value to append to the key.
     * @param key    - Key to set this object to.
     * @param values - Values to serialize.
     * @return - a "key": { "key1": value, "key2": value } pair
     */
    public static <K, V> String fromMap(String key, Map<K, V> values, boolean asString) {

        if (values.isEmpty()) {
            return "\"" + key + "\":{}";
        }

        V t_value = values.get(values.keySet().toArray()[0]);
        Boolean isSerializable = (t_value instanceof JSONSerializable);

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":{");

        for (Entry<K, V> entry : values.entrySet()) {


            final List<Class<?>> CLASS_KEY_WHITELIST = List.of(String.class, Enum.class, NamespacedKey.class);
            boolean allowed = CLASS_KEY_WHITELIST.stream()
                    .anyMatch(clazz -> clazz.isAssignableFrom(key.getClass()));

            if (!allowed) {
                throw new IllegalArgumentException("Key value in map " + key
                        + " should have type Enum, Keyed, or String, but has type: "
                        + entry.getKey().getClass().getName());
            }

            // if map inside map, recursively call
            if (entry.getValue() instanceof Map) {
                sb.append(fromMap(entry.getKey().toString(), (Map<?, ?>) entry.getValue(), asString));
            }
            // if map contains list value
            else if (entry.getValue() instanceof List) {
                sb.append(fromList(entry.getKey().toString(), (List<?>) entry.getValue()));
            }
            // if map contains set value
            else if (entry.getValue() instanceof Set) {
                sb.append(fromSet(entry.getKey().toString(), (Set<?>) entry.getValue()));
            } else {
                if (isKeyed) {
                    sb.append("\"" + ((Keyed) entry.getKey()).getKey().getKey() + "\":");
                } else {
                    sb.append("\"" + entry.getKey().toString() + "\":");
                }

                if (!asString && isSerializable) {
                    JSONSerializable j_value = (JSONSerializable) entry.getValue();
                    sb.append(j_value.toJSON());
                } else {
                    sb.append(entry.getValue().toString());
                }
            }
            sb.append(", ");
        }

        sb.setLength(sb.length() - 2);

        sb.append("}");

        return sb.toString();
    }

    /**
     * Convert a map into a valid "key": { "key1": value, "key2": value }
     * 
     * @param <K>    - Name of the key.
     * @param <V>    - Value to append to the key.
     * @param key    - Key to set this object to.
     * @param values - Values to serialize.
     * @return - a "key": { "key1": value, "key2": value } pair
     */
    public static <K, V> String fromMap(String key, HashMap<K, V> values) {
        return fromMap(key, (Map<K, V>) values, false);
    }

    /**
     * Convert a map into a valid "key": { "key1": value, "key2": value }
     * 
     * @param <K>    - Name of the key.
     * @param <V>    - Value to append to the key.
     * @param key    - Key to set this object to.
     * @param values - Values to serialize.
     * @return - a "key": { "key1": value, "key2": value } pair
     */
    public static <K, V> String fromMap(String key, HashMap<K, V> values, boolean asString) {
        return fromMap(key, (Map<K, V>) values, asString);
    }

}
