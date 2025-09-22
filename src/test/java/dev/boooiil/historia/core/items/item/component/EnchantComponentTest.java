package dev.boooiil.historia.core.items.item.component;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.items.component.EnchantComponent;
import dev.boooiil.historia.core.items.component.ExecutorComponent;
import dev.boooiil.historia.core.items.data.EnchantData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.HashMap;
import java.util.Map.Entry;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnchantComponentTest {
    static ServerMock server;
    static HistoriaCore plugin;
    YamlConfiguration configuration = FileIO.findYamlConfiguration("bronze_leggings.yml");
    ConfigurationSection item_root = configuration.getConfigurationSection("Light_Bronze_Leggings");
    ConfigurationSection component_root = item_root.getConfigurationSection("enchant");
    EnchantComponent component = EnchantComponent.fromConfig(component_root);

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            plugin = MockBukkit.load(HistoriaCore.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished setup.");

    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    void testData() {
        EnchantData data = component.data();

        assertEquals(data.enchantments(), component.enchantments());
    }

    @Test
    void testData2() {
        EnchantData data = component.data(1f);

        assertEquals(data.enchantments(), component.enchantments());
    }

    @Test
    void testEnchantments() {
        for (String key : component_root.getKeys(false)) {
            Enchantment enchantment = Enchantment.getByName(key);

            assertNotNull(component.enchantments().get(enchantment));
            assertEquals(component_root.getInt(key), component.enchantments().get(enchantment));
        }
        assertEquals(component.enchantments(), component.enchantments());
    }

    @Test
    void testGetKey() {
        assertEquals("enchant", component.getKey());
    }

    @Test
    void testToJSON() {
        assertEquals("{}", new ExecutorComponent(new HashMap<>()).toJSON());

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("\"enchantments\":");
        sb.append("{");

        for (Entry<Enchantment, Integer> enchants : component.enchantments().entrySet()) {

            sb.append("\"" + enchants.getKey().getKey().getKey() + "\":" + enchants.getValue() + ", ");

        }

        sb.setLength(sb.length() - 2);
        sb.append("}");
        sb.append("}");

        assertEquals(sb.toString(), component.toJSON());
    }

    @Test
    void testToString() {
        assertEquals("{}", new ExecutorComponent(new HashMap<>()).toJSON());

        StringBuilder sb = new StringBuilder();

        sb.append("EnchantComponent{");
        sb.append("\"enchantments\":");
        sb.append("{");

        for (Entry<Enchantment, Integer> enchants : component.enchantments().entrySet()) {

            sb.append("\"" + enchants.getKey().getKey().getKey() + "\":" + enchants.getValue() + ", ");

        }

        sb.setLength(sb.length() - 2);
        sb.append("}");
        sb.append("}");

        assertEquals(sb.toString(), component.toString());
    }
}
