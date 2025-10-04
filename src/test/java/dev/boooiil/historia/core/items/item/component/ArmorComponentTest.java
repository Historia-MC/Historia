package dev.boooiil.historia.core.items.item.component;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.items.component.ArmorComponent;
import dev.boooiil.historia.core.items.data.ArmorData;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ArmorComponentTest extends BaseTest {

    YamlConfiguration configuration = FileIO.findYamlConfiguration("bronze_boots.yml");
    ConfigurationSection item_root = configuration.getConfigurationSection("Light_Bronze_Boots");
    ConfigurationSection component_root = item_root.getConfigurationSection("armor");
    ArmorComponent component = ArmorComponent.fromConfig(component_root);

    @Test
    void testApply() {
        ArmorData data = component.data();

        Float min = component.defenseRange().get(0);
        Float max = component.defenseRange().get(1);
        float actual = data.defense();

        CoreLogger.debugToConsole(component.defenseRange().toString(), "" + actual);

        Assertions.assertTrue(actual >= min, "Actual value " + actual + " is not greater than min " + min);
        Assertions.assertTrue(actual <= max, "Actual value " + actual + " is not less than max " + max);

    }

    @Test
    void testApply2() {
        ArmorData data = component.data();

        Float min = component.defenseRange().get(0);
        Float max = component.defenseRange().get(1);
        float actual = data.defense();

        Assertions.assertTrue(actual >= min, "Actual value " + actual + " is not greater than min " + min);
        Assertions.assertTrue(actual <= max, "Actual value " + actual + " is not less than max " + max);
    }

    @Test
    void testDefenseRange() {
        Assertions.assertEquals(component_root.getFloatList("defense"), component.defenseRange());
    }

    @Test
    void testDurabilityRange() {
        Assertions.assertEquals(component_root.getIntegerList("durability"), component.durabilityRange());
    }

    @Test
    void testGetKey() {
        Assertions.assertEquals("armor", component.getKey());
    }

    @Test
    void testToJSON() {

        String sb = "{" +
                "\"defenseRange\":" +
                "[" +
                component.defenseRange().get(0) + ", " +
                component.defenseRange().get(1) + "], " +
                "\"durabilityRange\":" +
                "[" +
                component.durabilityRange().get(0) + ", " +
                component.durabilityRange().get(1) + "]" +
                "}";

        Assertions.assertEquals(sb, component.toJSON());

    }

    @Test
    void testToString() {

        String sb = "ArmorComponent{" +
                "\"defenseRange\":" +
                "[" +
                component.defenseRange().get(0) + ", " +
                component.defenseRange().get(1) + "], " +
                "\"durabilityRange\":" +
                "[" +
                component.durabilityRange().get(0) + ", " +
                component.durabilityRange().get(1) + "]" +
                "}";

        Assertions.assertEquals(sb, component.toString());

    }
}
