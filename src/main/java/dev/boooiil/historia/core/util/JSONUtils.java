package dev.boooiil.historia.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.bukkit.Warning;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NullMarked;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

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
    }

    @SuppressWarnings("unchecked")
    private static <T> BiFunction<String, List<?>, String> castAndHandle(
            BiFunction<String, List<T>, String> handler) {
        return (key, list) -> handler.apply(key, (List<T>) list);
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

    public static <T> String fromList(String key, List<T> values) {
        if (values.isEmpty()) {
            return "\"" + key + "\": []";
        }

        Class<?> type = values.get(0).getClass();
        BiFunction<String, List<?>, String> handler = TYPE_HANDLERS.get(type);

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
     * Convert a map into a valid "key": { "key1": value, "key2": value }
     * 
     * @param <K>    - Name of the key.
     * @param <V>    - Value to append to the key.
     * @param key    - Key to set this object to.
     * @param values - Values to serialize.
     * @return - a "key": { "key1": value, "key2": value } pair
     */
    @Warning(reason = "Use at your own risk. If the value is formatted with our implementation it will not output a correctly formatted JSON.")
    public static <K, V> String fromMapAsJSON(String key, Map<K, V> values) {

        if (values.isEmpty()) {
            return "\"" + key + "\":{}";
        }

        V t_value = values.get(values.keySet().toArray()[0]);
        Boolean isSerializable = (t_value instanceof JSONSerializable);

        CoreLogger.debugToConsole(t_value.getClass().getName(), "" + (t_value instanceof JSONSerializable),
                isSerializable.toString());

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":{");

        for (Entry<K, V> entry : values.entrySet()) {

            if (!(entry.getKey() instanceof String) && !entry.getKey().getClass().isEnum()) {
                throw new IllegalArgumentException("Key value in map " + key
                        + " should have type Enum or String, but has type: " + entry.getKey().getClass().getName());
            }

            // if (isSerializable) {
            // sb.append("\"" + entry.getKey().toString() + "\":");
            // sb.append(((JSONSerializable) entry.getValue()).toJSON() + ", ");
            // } else {

            // if map inside map, recursively call
            if (entry.getValue() instanceof Map) {
                sb.append(fromMapAsJSON(entry.getKey().toString(), (Map<?, ?>) entry.getValue()));
            }
            // if map contains list value
            else if (entry.getValue() instanceof List) {
                sb.append(fromList(entry.getKey().toString(), (List<?>) entry.getValue()));
            }
            // if map contains set value
            else if (entry.getValue() instanceof Set) {
                sb.append(fromSet(entry.getKey().toString(), (Set<?>) entry.getValue()));
            } else {
                sb.append("\"" + entry.getKey().toString() + "\":");
                sb.append(entry.getValue().toString());
            }

            sb.append(", ");
            // }

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
    public static <K, V> String fromMapAsJSON(String key, HashMap<K, V> values) {
        return fromMapAsJSON(key, (Map<K, V>) values);
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
    public static <K, V> String fromMapAsString(String key, Map<K, V> values) {

        // TODO: find difference between these methods
        if (true) {

            return fromMapAsJSON(key, values);
        }

        if (values.isEmpty()) {
            return "\"" + key + "\":{}";
        }

        V t_value = values.get(values.keySet().toArray()[0]);
        Boolean isSerializable = (t_value instanceof JSONSerializable);

        CoreLogger.debugToConsole(t_value.getClass().getName(), "" + (t_value instanceof JSONSerializable),
                isSerializable.toString());

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":{");

        for (Entry<K, V> entry : values.entrySet()) {

            // if (isSerializable) {
            // sb.append("\"" + entry.getKey().toString() + "\":");
            // sb.append((entry.getValue()).toString() + ", ");
            // } else {

            // if map inside map, recursively call
            if (entry.getValue() instanceof Map) {
                sb.append(fromMapAsString(entry.getKey().toString(), (Map<?, ?>) entry.getValue()));
            } else {
                sb.append("\"" + entry.getKey().toString() + "\":");
                sb.append(entry.getValue().toString());
                sb.append(", ");
            }
            // }

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
    @Warning(reason = "Use at your own risk. If the value is formatted with our implementation it will not output a correctly formatted JSON.")
    public static <K, V> String fromMapAsString(String key, HashMap<K, V> values) {
        return fromMapAsString(key, (Map<K, V>) values);
    }

}
