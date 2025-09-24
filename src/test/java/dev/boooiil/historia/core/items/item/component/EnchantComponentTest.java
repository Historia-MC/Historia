package dev.boooiil.historia.core.items.item.component;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.items.component.EnchantComponent;
import dev.boooiil.historia.core.items.component.ExecutorComponent;
import dev.boooiil.historia.core.items.data.EnchantData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map.Entry;

public class EnchantComponentTest extends BaseTest {

    YamlConfiguration configuration = FileIO.findYamlConfiguration("bronze_leggings.yml");
    ConfigurationSection item_root = configuration.getConfigurationSection("Light_Bronze_Leggings");
    ConfigurationSection component_root = item_root.getConfigurationSection("enchant");
    EnchantComponent component = EnchantComponent.fromConfig(component_root);

    @Test
    void testData() {
        EnchantData data = component.data();

        Assertions.assertEquals(data.enchantments(), component.enchantments());
    }

    @Test
    void testData2() {
        EnchantData data = component.data(1f);

        Assertions.assertEquals(data.enchantments(), component.enchantments());
    }

    @Test
    void testEnchantments() {
        for (String key : component_root.getKeys(false)) {
            Enchantment enchantment = Enchantment.getByName(key);

            Assertions.assertNotNull(component.enchantments().get(enchantment));
            Assertions.assertEquals(component_root.getInt(key), component.enchantments().get(enchantment));
        }
    }

    @Test
    void testGetKey() {
        Assertions.assertEquals("enchant", component.getKey());
    }

    @Test
    void testToJSON() {
        Assertions.assertEquals("{}", new ExecutorComponent(new HashMap<>()).toJSON());

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

        Assertions.assertEquals(sb.toString(), component.toJSON());
    }

    @Test
    void testToString() {
        Assertions.assertEquals("{}", new ExecutorComponent(new HashMap<>()).toJSON());

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

        Assertions.assertEquals(sb.toString(), component.toString());
    }
}
