package dev.boooiil.historia.core.registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.bukkit.NamespacedKey;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class RegistryTest {

    private Registry<String> registry;
    private NamespacedKey key1;
    private NamespacedKey key2;

    @BeforeEach
    void setUp() {
        registry = new Registry<>(String.class);
        key1 = new NamespacedKey("test", "key1");
        key2 = new NamespacedKey("test", "key2");
    }

    @Test
    void testRegisterAndGet() {
        registry.register(key1, "value1");
        assertEquals("value1", registry.get(key1));
    }

    @Test
    void testDeregister() {
        registry.register(key1, "value1");
        registry.deregister(key1);
        assertNull(registry.get(key1));
    }

    @Test
    void testUpdate() {
        registry.register(key1, "value1");
        registry.update(key1, "value2");
        assertEquals("value2", registry.get(key1));
    }

    @Test
    void testContains() {
        registry.register(key1, "value1");
        assertTrue(registry.contains(key1));
        assertFalse(registry.contains(key2));
    }

    @Test
    void testAllKeys() {
        registry.register(key1, "value1");
        registry.register(key2, "value2");
        Set<NamespacedKey> keys = registry.allKeys();
        assertTrue(keys.contains(key1));
        assertTrue(keys.contains(key2));
        assertEquals(2, keys.size());
    }

    @Test
    void testStaticGetReturnsNullIfNotFound() {
        NamespacedKey unknownKey = new NamespacedKey("unknown", "notfound");
        assertNull(Registry.get(unknownKey, Registry.class));
    }

    @Test
    void testStaticGetReturnsRegistryIfFound() {
        NamespacedKey regStrKey = new NamespacedKey("test", "mystrregistry");
        NamespacedKey regIntKey = new NamespacedKey("test", "myintregistry");

        Registry<String> myStrRegistry = new Registry<>(String.class);
        Registry<Integer> myIntRegistry = new Registry<>(Integer.class);

        Registry.registryHolder.register(regStrKey, myStrRegistry);
        Registry.registryHolder.register(regIntKey, myIntRegistry);

        Registry<String> foundStrRegistry = Registry.get(regStrKey, String.class);
        Registry<Integer> foundIntRegistry = Registry.get(regIntKey, Integer.class);

        assertThrows(IllegalArgumentException.class, () -> {
            Registry.get(regStrKey, Integer.class);
        });

        assertNotNull(foundStrRegistry);
        assertSame(myStrRegistry, foundStrRegistry);

        assertNotNull(foundIntRegistry);
        assertSame(myIntRegistry, foundIntRegistry);

        foundStrRegistry.register(new NamespacedKey("test", "newstrkey"), "newvalue");
    }

    @Test
    void testStaticGetReturnsNullIfNotFound1() {
        Registry<Registry<?>> registryHolder = Registry.generateHolder();
        NamespacedKey unknownKey = new NamespacedKey("unknown", "notfound");
        assertNull(Registry.get(registryHolder, unknownKey, Registry.class));
    }

    @Test
    void testStaticGetReturnsRegistryIfFound1() {

        Registry<Registry<?>> registryHolder = Registry.generateHolder();

        NamespacedKey regStrKey = new NamespacedKey("test", "mystrregistry");
        NamespacedKey regIntKey = new NamespacedKey("test", "myintregistry");

        Registry<String> myStrRegistry = new Registry<>(String.class);
        Registry<Integer> myIntRegistry = new Registry<>(Integer.class);

        registryHolder.register(regStrKey, myStrRegistry);
        registryHolder.register(regIntKey, myIntRegistry);

        Registry<String> foundStrRegistry = Registry.get(registryHolder, regStrKey, String.class);
        Registry<Integer> foundIntRegistry = Registry.get(registryHolder, regIntKey, Integer.class);

        assertThrows(IllegalArgumentException.class, () -> {
            Registry.get(registryHolder, regStrKey, Integer.class);
        });

        assertNotNull(foundStrRegistry);
        assertSame(myStrRegistry, foundStrRegistry);

        assertNotNull(foundIntRegistry);
        assertSame(myIntRegistry, foundIntRegistry);

        foundStrRegistry.register(new NamespacedKey("test", "newstrkey"), "newvalue");
    }

}