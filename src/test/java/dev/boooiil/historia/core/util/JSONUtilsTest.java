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
    void testFromJSONSerializableList() {

        JSONListTestClass listTest = new JSONListTestClass();

        assertEquals(listTest.toJSON(),
                "{\"test\":[{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, {\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}");

        assertEquals(listTest.toString(),
                "JSONListTestClass{\"test\":[JSONTestClass{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, JSONTestClass{\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}");

    }

    @Test
    void testFromJSONSerializableSet() {

        JSONSetTestClass listTest = new JSONSetTestClass();

        assertEquals(listTest.toJSON(),
                "{\"test\":[{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, {\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}");

        assertEquals(listTest.toString(),
                "JSONSetTestClass{\"test\":[JSONTestClass{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, JSONTestClass{\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}");

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
    void testfromMap() {

        HashMap<String, Integer> simlpeMap = new HashMap<>();
        HashMap<String, List<Integer>> listMap = new HashMap<>();
        HashMap<String, HashMap<String, List<Integer>>> complexMap = new HashMap<>();

        simlpeMap.put("one", 1);
        simlpeMap.put("two", 2);

        listMap.put("one", List.of(1));
        listMap.put("two", List.of(2));

        complexMap.put("one", listMap);
        complexMap.put("two", listMap);

        String smr = JSONUtils.fromMap("map", simlpeMap);

        assertEquals(smr, "\"map\":{\"one\":1, \"two\":2}");

        String lmr = JSONUtils.fromMap("map", listMap);

        assertEquals(lmr, "\"map\":{\"one\":[1], \"two\":[2]}");

        String cmr = JSONUtils.fromMap("map", complexMap);

        assertEquals(cmr, "\"map\":{\"one\":{\"one\":[1], \"two\":[2]}, \"two\":{\"one\":[1], \"two\":[2]}}");

        JSONMapTestClass mapTest = new JSONMapTestClass();

        assertEquals(mapTest.toJSON(),
                "{\"test\":{\"set\":{\"test\":[{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, {\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}, \"test\":{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, \"list\":{\"test\":[{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, {\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}, \"test1\":{\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}}}");

        assertEquals(mapTest.toString(),
                "JSONMapTestClass{\"test\":{\"set\":JSONSetTestClass{\"test\":[JSONTestClass{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, JSONTestClass{\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}, \"test\":JSONTestClass{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, \"list\":JSONSetTestClass{\"test\":[JSONTestClass{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}, JSONTestClass{\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}]}, \"test1\":JSONTestClass{\"i\":2, \"f\":2.0, \"b\":false, \"s\":\"test1\"}}}");
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

        String smr = JSONUtils.fromMap("map", simlpeMap, true);

        assertEquals(smr, "\"map\":{\"one\":1, \"two\":2}");

        String lmr = JSONUtils.fromMap("map", listMap, true);

        assertEquals(lmr, "\"map\":{\"one\":[1], \"two\":[2]}");

        String cmr = JSONUtils.fromMap("map", complexMap, true);

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

    @Test
    void testFromValue7() {
        JSONTestClass testClass = new JSONTestClass();

        assertEquals(JSONUtils.fromValue("test", testClass),
                "\"test\":{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}");

        assertEquals(JSONUtils.fromValue("test", testClass, true),
                "\"test\":JSONTestClass{\"i\":1, \"f\":1.0, \"b\":true, \"s\":\"test\"}");
    }

    @Test
    void testFromEmptyList() {
        List<Integer> emptyList = new ArrayList<>();
        String result = JSONUtils.fromList("empty", emptyList);
        assertEquals("\"empty\": []", result);
    }

    @Test
    void testFromEmptySet() {
        Set<String> emptySet = new LinkedHashSet<>();
        String result = JSONUtils.fromSet("empty", emptySet);
        assertEquals("\"empty\": []", result);
    }

    @Test
    void testFromEmptyMap() {
        HashMap<String, Integer> emptyMap = new HashMap<>();
        String result = JSONUtils.fromMap("empty", emptyMap);
        assertEquals("\"empty\":{}", result);
    }

    @Test
    void testFromStringListWithSpecialCharacters() {
        List<String> list = Arrays.asList("a", "b", "c\"d", "e\\f");
        String result = JSONUtils.fromStringList("special", list);
        assertEquals("\"special\":[\"a\", \"b\", \"c\"d\", \"e\\f\"]", result);
    }

    @Test
    void testFromStringSetWithSpecialCharacters() {
        Set<String> set = new LinkedHashSet<>(Arrays.asList("a", "b", "c\"d", "e\\f"));
        String result = JSONUtils.fromStringSet("special", set);
        assertEquals("\"special\":[\"a\", \"b\", \"c\"d\", \"e\\f\"]", result);
    }

    @Test
    void testFromListWithUnsupportedTypeThrows() {
        class Dummy {
        }
        List<Dummy> dummyList = Arrays.asList(new Dummy());
        try {
            JSONUtils.fromList("dummy", dummyList);
        } catch (IllegalArgumentException e) {
            assertEquals("Key dummy provided a list type of " + Dummy.class.getName()
                    + " which does not have a configured handler.", e.getMessage());
        }
    }

    @Test
    void testFromMapWithNonStringKeyThrows() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        try {
            JSONUtils.fromMap("badmap", map);
        } catch (IllegalArgumentException e) {
            assertEquals("Key value in map badmap should have type Enum or String, but has type: java.lang.Integer",
                    e.getMessage());
        }
    }

    private class JSONTestClass implements JSONSerializable {

        int i = 1;
        float f = 1f;
        boolean b = true;
        String s = "test";

        JSONTestClass() {
        }

        JSONTestClass(int i, float f, boolean b, String s) {
            this.i = i;
            this.f = f;
            this.b = b;
            this.s = s;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(this.getClass().getSimpleName() + "{");
            sb.append(JSONUtils.fromValue("i", i) + ", ");
            sb.append(JSONUtils.fromValue("f", f) + ", ");
            sb.append(JSONUtils.fromValue("b", b) + ", ");
            sb.append(JSONUtils.fromValue("s", s));
            sb.append("}");

            return sb.toString();
        }

        @Override
        public String toJSON() {
            StringBuilder sb = new StringBuilder();

            sb.append("{");
            sb.append(JSONUtils.fromValue("i", i) + ", ");
            sb.append(JSONUtils.fromValue("f", f) + ", ");
            sb.append(JSONUtils.fromValue("b", b) + ", ");
            sb.append(JSONUtils.fromValue("s", s));
            sb.append("}");

            return sb.toString();
        }

    }

    private class JSONMapTestClass implements JSONSerializable {

        HashMap<String, JSONSerializable> map = new HashMap<>();

        JSONMapTestClass() {
            map.put("test", new JSONTestClass());
            map.put("test1", new JSONTestClass(2, 2f, false, "test1"));
            map.put("list", new JSONSetTestClass());
            map.put("set", new JSONSetTestClass());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(this.getClass().getSimpleName() + "{");
            sb.append(JSONUtils.fromMap("test", map, true));
            sb.append("}");

            return sb.toString();
        }

        @Override
        public String toJSON() {
            StringBuilder sb = new StringBuilder();

            sb.append("{");
            sb.append(JSONUtils.fromMap("test", map));
            sb.append("}");

            return sb.toString();
        }

    }

    private class JSONListTestClass implements JSONSerializable {

        List<JSONSerializable> list = new ArrayList<>();

        JSONListTestClass() {
            list.add(new JSONTestClass());
            list.add(new JSONTestClass(2, 2f, false, "test1"));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(this.getClass().getSimpleName() + "{");
            sb.append(JSONUtils.fromList("test", list));
            sb.append("}");

            return sb.toString();
        }

        @Override
        public String toJSON() {
            StringBuilder sb = new StringBuilder();

            sb.append("{");
            sb.append(JSONUtils.fromJSONSerializableList("test", list, true));
            sb.append("}");

            return sb.toString();
        }
    }

    private class JSONSetTestClass implements JSONSerializable {

        Set<JSONSerializable> set = new LinkedHashSet<>();

        JSONSetTestClass() {
            set.add(new JSONTestClass());
            set.add(new JSONTestClass(2, 2f, false, "test1"));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(this.getClass().getSimpleName() + "{");
            sb.append(JSONUtils.fromSet("test", set));
            sb.append("}");

            return sb.toString();
        }

        @Override
        public String toJSON() {
            StringBuilder sb = new StringBuilder();

            sb.append("{");
            sb.append(JSONUtils.fromJSONSerializableSet("test", set, true));
            sb.append("}");

            return sb.toString();
        }
    }
}
