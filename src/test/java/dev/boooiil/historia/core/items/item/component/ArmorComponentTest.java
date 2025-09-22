package dev.boooiil.historia.core.items.item.component;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.items.component.ArmorComponent;
import dev.boooiil.historia.core.items.data.ArmorData;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArmorComponentTest {

    static ServerMock server;
    static HistoriaCore plugin;
    YamlConfiguration configuration = FileIO.findYamlConfiguration("bronze_boots.yml");
    ConfigurationSection item_root = configuration.getConfigurationSection("Light_Bronze_Boots");
    ConfigurationSection component_root = item_root.getConfigurationSection("armor");
    ArmorComponent component = ArmorComponent.fromConfig(component_root);

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
    void testApply() {
        ArmorData data = component.data();

        CoreLogger.debugToConsole(component.defenseRange().toString(), "" + data.defense());

        assertTrue(data.defense() > component.defenseRange().get(0)
                && data.defense() < component.defenseRange().get(1));

    }

    @Test
    void testApply2() {
        ArmorData data = component.data(1f);

        assertTrue(data.defense() > component.defenseRange().get(0)
                && data.defense() < component.defenseRange().get(1));

    }

    @Test
    void testDefenseRange() {
        assertEquals(component_root.getFloatList("defense"), component.defenseRange());
    }

    @Test
    void testDurabilityRange() {
        assertEquals(component_root.getIntegerList("durability"), component.durabilityRange());
    }

    @Test
    void testGetKey() {
        assertEquals("armor", component.getKey());
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

        assertEquals(sb, component.toJSON());

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

        assertEquals(sb, component.toString());

    }
}
