package dev.boooiil.historia.core.registry;

import dev.boooiil.historia.core.BaseTest;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class RegistryTest extends BaseTest {

    private static Registry<String> registry;
    private static NamespacedKey key1;
    private static NamespacedKey key2;

    @BeforeAll
    public static void setUp() {
        // Use String.class as the type for simplicity
        registry = new Registry<>(String.class);
        key1 = new NamespacedKey("test", "key1");
        key2 = new NamespacedKey("test", "key2");
    }

    @Test
    public void testRegisterAndGet() {
        registry.register(key1, "value1");
        Assertions.assertEquals("value1", registry.get(key1));
    }

    @Test
    public void testDeregister() {
        registry.register(key1, "value1");
        registry.deregister(key1);
        Assertions.assertNull(registry.get(key1));
    }

    @Test
    public void testUpdate() {
        registry.register(key1, "value1");
        registry.update(key1, "value2");
        Assertions.assertEquals("value2", registry.get(key1));
    }

    @Test
    public void testContains() {
        registry.register(key1, "value1");
        Assertions.assertTrue(registry.contains(key1));
        Assertions.assertFalse(registry.contains(key2));
    }

    @Test
    public void testAllKeys() {
        registry.register(key1, "value1");
        registry.register(key2, "value2");
        Set<NamespacedKey> keys = registry.keySet();
        Assertions.assertTrue(keys.contains(key1));
        Assertions.assertTrue(keys.contains(key2));
        Assertions.assertEquals(2, keys.size());
    }

    @Test
    public void testGetType() {
        Type type = registry.getType();
        Assertions.assertEquals(String.class, type);
    }

    @Test
    public void testGetNonExistentKeyReturnsNull() {
        Assertions.assertNull(registry.get(new NamespacedKey("test", "nonexistent")));
    }

    @Test
    public void testOf() {
        Registry<List<String>> newRegistry = Registry.of(new TypeToken<List<String>>() {
        });
        Assertions.assertNotNull(newRegistry);
        Assertions.assertEquals(new TypeToken<List<String>>() {
        }.getType(), newRegistry.getType());
    }
}
