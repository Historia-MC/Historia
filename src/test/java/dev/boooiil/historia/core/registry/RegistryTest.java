package dev.boooiil.historia.core.registry;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.bukkit.NamespacedKey;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class RegistryTest {

    private Registry<String> registry;
    private NamespacedKey key1;
    private NamespacedKey key2;

    @Before
    public void setUp() {
        // Use String.class as the type for simplicity
        registry = new Registry<>(String.class);
        key1 = new NamespacedKey("test", "key1");
        key2 = new NamespacedKey("test", "key2");
    }

    @Test
    public void testRegisterAndGet() {
        registry.register(key1, "value1");
        assertEquals("value1", registry.get(key1));
    }

    @Test
    public void testDeregister() {
        registry.register(key1, "value1");
        registry.deregister(key1);
        assertNull(registry.get(key1));
    }

    @Test
    public void testUpdate() {
        registry.register(key1, "value1");
        registry.update(key1, "value2");
        assertEquals("value2", registry.get(key1));
    }

    @Test
    public void testContains() {
        registry.register(key1, "value1");
        assertTrue(registry.contains(key1));
        assertFalse(registry.contains(key2));
    }

    @Test
    public void testAllKeys() {
        registry.register(key1, "value1");
        registry.register(key2, "value2");
        Set<NamespacedKey> keys = registry.allKeys();
        assertTrue(keys.contains(key1));
        assertTrue(keys.contains(key2));
        assertEquals(2, keys.size());
    }

    @Test
    public void testGetType() {
        Type type = registry.getType();
        assertEquals(String.class, type);
    }

    @Test
    public void testGetNonExistentKeyReturnsNull() {
        assertNull(registry.get(new NamespacedKey("test", "nonexistent")));
    }

    @Test
    public void testOf() {
        Registry<List<String>> newRegistry = Registry.of(new TypeToken<List<String>>() {
        });
        assertNotNull(newRegistry);
        assertEquals(new TypeToken<List<String>>() {
        }.getType(), newRegistry.getType());
    }
}
