package dev.boooiil.historia.core.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.kyori.adventure.text.Component;

public class JSONUtilsTest {

    private static final HashMap<Class<?>, List<?>> listMappings = new HashMap<>();
    private static final HashMap<Class<?>, Set<?>> setMappings = new HashMap<>();

    @BeforeAll
    static void initalizeMaps() {

        Set<Integer> intSet = new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4));
        Set<Float> floatSet = new LinkedHashSet<>(Arrays.asList(1f, 2f, 3f, 4f));
        Set<Double> doubleSet = new LinkedHashSet<>(Arrays.asList(1d, 2d, 3d, 4d));
        Set<Long> longSet = new LinkedHashSet<>(Arrays.asList(1l, 2l, 3l, 4l));
        Set<Boolean> booleanSet = new LinkedHashSet<>(Arrays.asList(false, true));
        Set<String> stringSet = new LinkedHashSet<>(Arrays.asList("one", "two", "three", "four"));
        Set<Component> componentSet = new LinkedHashSet<>(Arrays.asList(
                KyoriUtils.textComponent("one"),
                KyoriUtils.textComponent("two"),
                KyoriUtils.textComponent("three"),
                KyoriUtils.textComponent("four")));
        Set<PotionEffect> effectSet = new LinkedHashSet<>(
                Arrays.asList(
                        new PotionEffect(PotionEffectType.ABSORPTION, 1, 0),
                        new PotionEffect(PotionEffectType.STRENGTH, 21, 0)));

        // Set Mappings
        setMappings.put(Integer.class, intSet);
        setMappings.put(Float.class, floatSet);
        setMappings.put(Double.class, doubleSet);
        setMappings.put(Long.class, longSet);
        setMappings.put(Boolean.class, booleanSet);
        setMappings.put(String.class, stringSet);
        setMappings.put(Component.class, componentSet);
        setMappings.put(PotionEffect.class, effectSet);

        listMappings.put(Integer.class, new ArrayList<>(intSet));
        listMappings.put(Float.class, new ArrayList<>(floatSet));
        listMappings.put(Double.class, new ArrayList<>(doubleSet));
        listMappings.put(Long.class, new ArrayList<>(longSet));
        listMappings.put(Boolean.class, new ArrayList<>(booleanSet));
        listMappings.put(String.class, new ArrayList<>(stringSet));
        listMappings.put(Component.class, new ArrayList<>(componentSet));
        listMappings.put(PotionEffect.class, new ArrayList<>(effectSet));

    }

    @Test
    void testFromBooleanList() {

        List<?> l = listMappings.get(Boolean.class);

        String r = JSONUtils.fromList("list", l);

        assertEquals(r, "\"list\":[false, true]");

    }

    @Test
    void testFromBooleanSet() {

        Set<?> l = setMappings.get(Boolean.class);

        String r = JSONUtils.fromSet("list", l);

        assertEquals(r, "\"list\":[false, true]");

    }

    @Test
    void testFromComponentList() {
        // TODO: figure out how to implement this
    }

    @Test
    void testFromComponentSet() {
        // TODO: figure out how to implement this
    }

    @Test
    void testFromDoubleList() {

        List<?> l = listMappings.get(Double.class);

        String r = JSONUtils.fromList("list", l);

        assertEquals(r, "\"list\":[1.0, 2.0, 3.0, 4.0]");

    }

    @Test
    void testFromDoubleSet() {

        Set<?> l = setMappings.get(Double.class);

        String r = JSONUtils.fromSet("list", l);

        assertEquals(r, "\"list\":[1.0, 2.0, 3.0, 4.0]");
    }

    @Test
    void testFromFloatList() {

        List<?> l = listMappings.get(Float.class);

        String r = JSONUtils.fromList("list", l);

        assertEquals(r, "\"list\":[1.0, 2.0, 3.0, 4.0]");

    }

    @Test
    void testFromFloatSet() {

        Set<?> l = setMappings.get(Float.class);

        String r = JSONUtils.fromSet("list", l);

        assertEquals(r, "\"list\":[1.0, 2.0, 3.0, 4.0]");
    }

    @Test
    void testFromIntegerList() {

        List<?> l = listMappings.get(Integer.class);

        String r = JSONUtils.fromList("list", l);

        assertEquals(r, "\"list\":[1, 2, 3, 4]");

    }

    @Test
    void testFromIntegerSet() {

        Set<?> l = setMappings.get(Integer.class);

        String r = JSONUtils.fromSet("list", l);

        assertEquals(r, "\"list\":[1, 2, 3, 4]");
    }

    @Test
    void testFromLongList() {

        List<?> l = listMappings.get(Long.class);

        String r = JSONUtils.fromList("list", l);

        assertEquals(r, "\"list\":[1, 2, 3, 4]");

    }

    @Test
    void testFromLongSet() {

        Set<?> l = setMappings.get(Long.class);

        String r = JSONUtils.fromSet("list", l);

        assertEquals(r, "\"list\":[1, 2, 3, 4]");
    }

    @Test
    void testFromMapAsJSON() {

        HashMap<String, Integer> simlpeMap = new HashMap<>();
        HashMap<String, List<Integer>> listMap = new HashMap<>();
        HashMap<String, HashMap<String, List<Integer>>> complexMap = new HashMap<>();

        simlpeMap.put("one", 1);
        simlpeMap.put("two", 2);

        listMap.put("one", List.of(1));
        listMap.put("two", List.of(2));

        complexMap.put("one", listMap);
        complexMap.put("two", listMap);

        String smr = JSONUtils.fromMapAsJSON("map", simlpeMap);

        assertEquals(smr, "\"map\":{\"one\":1, \"two\":2}");

        String lmr = JSONUtils.fromMapAsJSON("map", listMap);

        assertEquals(lmr, "\"map\":{\"one\":[1], \"two\":[2]}");

        String cmr = JSONUtils.fromMapAsJSON("map", complexMap);

        assertEquals(cmr, "\"map\":{\"one\":{\"one\":[1], \"two\":[2]}, \"two\":{\"one\":[1], \"two\":[2]}}");

    }

    @Test
    void testFromMapAsString() {

        HashMap<String, Integer> simlpeMap = new HashMap<>();
        HashMap<String, List<Integer>> listMap = new HashMap<>();
        HashMap<String, HashMap<String, List<Integer>>> complexMap = new HashMap<>();

        simlpeMap.put("one", 1);
        simlpeMap.put("two", 2);

        listMap.put("one", List.of(1));
        listMap.put("two", List.of(2));

        complexMap.put("one", listMap);
        complexMap.put("two", listMap);

        String smr = JSONUtils.fromMapAsString("map", simlpeMap);

        assertEquals(smr, "\"map\":{\"one\":1, \"two\":2}");

        String lmr = JSONUtils.fromMapAsString("map", listMap);

        assertEquals(lmr, "\"map\":{\"one\":[1], \"two\":[2]}");

        String cmr = JSONUtils.fromMapAsString("map", complexMap);

        assertEquals(cmr, "\"map\":{\"one\":{\"one\":[1], \"two\":[2]}, \"two\":{\"one\":[1], \"two\":[2]}}");

    }

    @Test
    void testFromPotionEffectList() {

        List<?> l = listMappings.get(PotionEffect.class);

        String r = JSONUtils.fromList("list", l);

        assertEquals(r,
                "\"list\":[{\"type\":\"minecraft:absorption\", \"duration\":0, \"amplifier\":1}, {\"type\":\"minecraft:strength\", \"duration\":0, \"amplifier\":21}]");

    }

    @Test
    void testFromPotionEffectSet() {
        Set<?> l = setMappings.get(PotionEffect.class);

        String r = JSONUtils.fromSet("list", l);

        assertEquals(r,
                "\"list\":[{\"type\":\"minecraft:absorption\", \"duration\":0, \"amplifier\":1}, {\"type\":\"minecraft:strength\", \"duration\":0, \"amplifier\":21}]");

    }

    @Test
    void testFromStringList() {

        List<?> l = listMappings.get(String.class);

        String r = JSONUtils.fromList("list", l);

        assertEquals(r, "\"list\":[\"one\", \"two\", \"three\", \"four\"]");
    }

    @Test
    void testFromStringSet() {

        Set<?> l = setMappings.get(String.class);

        String r = JSONUtils.fromSet("list", l);

        assertEquals(r, "\"list\":[\"one\", \"two\", \"three\", \"four\"]");
    }

    @Test
    void testFromValue() {
        String r = JSONUtils.fromValue("value", 1);

        assertEquals(r, "\"value\":1");
    }

    @Test
    void testFromValue2() {

        String r = JSONUtils.fromValue("value", 1f);

        assertEquals(r, "\"value\":1.0");
    }

    @Test
    void testFromValue3() {

        String r = JSONUtils.fromValue("value", 1d);

        assertEquals(r, "\"value\":1.0");
    }

    @Test
    void testFromValue4() {

        String r = JSONUtils.fromValue("value", 1l);

        assertEquals(r, "\"value\":1");
    }

    @Test
    void testFromValue5() {

        String r = JSONUtils.fromValue("value", false);

        assertEquals(r, "\"value\":false");
    }

    @Test
    void testFromValue6() {

        String r = JSONUtils.fromValue("value", "one");

        assertEquals(r, "\"value\":\"one\"");
    }

}
