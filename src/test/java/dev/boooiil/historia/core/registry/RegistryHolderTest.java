package dev.boooiil.historia.core.registry;

import dev.boooiil.historia.core.BaseTest;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistryHolderTest extends BaseTest {

    private RegistryHolder registryHolder;

    // Dummy registry for testing
    static class Dummy {
    }

    @BeforeEach
    void setUp() {
        registryHolder = new RegistryHolder();
    }

    @Test
    void testGenerateHolderReturnsNonNullRegistry() {
        Registry<Registry<?>> holder = RegistryHolder.generateHolder();
        assertNotNull(holder);
        assertEquals(Registry.class, holder.getType());
    }

    @Test
    void testGetHolderReturnsSameInstance() {
        Registry<Registry<?>> holder1 = registryHolder.getHolder();
        Registry<Registry<?>> holder2 = registryHolder.getHolder();
        assertSame(holder1, holder2);
    }

    @Test
    void testGetReturnsNullIfRegistryNotFound() {
        NamespacedKey key = new NamespacedKey("plugin", "not_found");
        Registry<Dummy> result = registryHolder.get(key, Dummy.class);
        assertNull(result);
    }

    @Test
    void testGetReturnsRegistryIfFoundAndTypeMatches() {
        NamespacedKey key = new NamespacedKey("plugin", "dummy");
        Registry<Dummy> dummyRegistry = new Registry<>(Dummy.class);
        registryHolder.getHolder().register(key, dummyRegistry);

        Registry<Dummy> result = registryHolder.get(key, Dummy.class);
        assertNotNull(result);
        assertSame(dummyRegistry, result);
    }

    @Test
    void testGetThrowsIfTypeDoesNotMatch() {
        NamespacedKey key = new NamespacedKey("plugin", "dummy");
        Registry<Dummy> dummyRegistry = new Registry<>(Dummy.class);
        registryHolder.getHolder().register(key, dummyRegistry);

        assertThrows(IllegalArgumentException.class, () -> {
            registryHolder.get(key, String.class);
        });
    }

    @Test
    void testStaticGetReturnsNullIfRegistryNotFound() {
        Registry<Registry<?>> holder = RegistryHolder.generateHolder();
        NamespacedKey key = new NamespacedKey("plugin", "missing");
        Registry<?> result = RegistryHolder.get(holder, key, Dummy.class);
        assertNull(result);
    }

    @Test
    void testStaticGetReturnsRegistryIfFoundAndTypeMatches() {
        Registry<Registry<?>> holder = RegistryHolder.generateHolder();
        NamespacedKey key = new NamespacedKey("plugin", "dummy");
        Registry<Dummy> dummyRegistry = new Registry<>(Dummy.class);
        holder.register(key, dummyRegistry);

        Registry<Dummy> result = RegistryHolder.get(holder, key, Dummy.class);
        assertNotNull(result);
        assertSame(dummyRegistry, result);
    }

    @Test
    void testStaticGetThrowsIfTypeDoesNotMatch() {
        Registry<Registry<?>> holder = RegistryHolder.generateHolder();
        NamespacedKey key = new NamespacedKey("plugin", "dummy");
        Registry<Dummy> dummyRegistry = new Registry<>(Dummy.class);
        holder.register(key, dummyRegistry);

        assertThrows(IllegalArgumentException.class, () -> {
            RegistryHolder.get(holder, key, String.class);
        });
    }
}