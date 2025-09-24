package dev.boooiil.historia.core.items.item.component;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.items.component.ExecutorComponent;
import dev.boooiil.historia.core.items.data.ExecutorData;
import dev.boooiil.historia.core.items.executor.ItemExecutable;
import dev.boooiil.historia.core.items.types.Triggers;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import static org.junit.Assert.assertNotNull;

public class ExecutorComponentTest extends BaseTest {

    YamlConfiguration configuration = FileIO.findYamlConfiguration("bronze_leggings.yml");
    ConfigurationSection item_root = configuration.getConfigurationSection("Light_Bronze_Leggings");
    ConfigurationSection component_root = item_root.getConfigurationSection("executor");
    ExecutorComponent component = ExecutorComponent.fromConfig(component_root);

    @Test
    void testApply() {
        ExecutorData data = component.data();
        CoreLogger.debugToConsole("ExecutorComponentTest", "data", data.toJSON());
        Assertions.assertEquals(data.executables(), component.executables());
    }

    @Test
    void testApply2() {
        ExecutorData data = component.data(1f);

        Assertions.assertEquals(data.executables(), component.executables());
    }

    @Test
    void testExecutables() {

        for (String key : component_root.getKeys(false)) {
            ConfigurationSection section = component_root.getConfigurationSection(key);

            Triggers action = Triggers.fromString(key);
            List<String> commands = section.getStringList("commands");
            int uses = section.getInt("uses");
            int cooldown = section.getInt("cooldown");

            assertNotNull(component.executables().get(action));

            ItemExecutable executable = component.executables().get(action);

            Assertions.assertEquals(commands, executable.commands());
            Assertions.assertEquals(uses, executable.uses());
            Assertions.assertEquals(cooldown, executable.cooldown());

        }

    }

    @Test
    void testGetKey() {
        Assertions.assertEquals("executor", component.getKey());
    }

    @Test
    void testToJSON() {

        Assertions.assertEquals("{}", new ExecutorComponent(new HashMap<>()).toJSON());

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("\"executables\":");
        sb.append("{");

        for (Entry<Triggers, ItemExecutable> executables : component.executables().entrySet()) {

            sb.append("\"" + executables.getKey().getLowercase() + "\":{");
            sb.append("\"commands\":[");

            for (String command : executables.getValue().commands()) {
                sb.append("\"" + command + "\", ");
            }

            sb.setLength(sb.length() - 2);
            sb.append("], ");
            sb.append("\"uses\":" + executables.getValue().uses() + ", ");
            sb.append("\"cooldown\":" + executables.getValue().cooldown() + ", ");
            sb.append("\"elevated\":" + executables.getValue().hasElevation() + ", ");
            sb.append("\"hasCooldown\":" + executables.getValue().hasCooldown());
            sb.append("}, ");

        }

        sb.setLength(sb.length() - 2);
        sb.append("}");
        sb.append("}");

        Assertions.assertEquals(sb.toString(), component.toJSON());

    }

    @Test
    void testToString() {

        Assertions.assertEquals("ExecutorComponent{}", new ExecutorComponent(new HashMap<>()).toString());

        StringBuilder sb = new StringBuilder();

        sb.append("ExecutorComponent{");
        sb.append("\"executables\":");
        sb.append("{");

        for (Entry<Triggers, ItemExecutable> executables : component.executables().entrySet()) {

            sb.append("\"" + executables.getKey().getLowercase() + "\":ItemExecutable{");
            sb.append("\"commands\":[");

            for (String command : executables.getValue().commands()) {
                sb.append("\"" + command + "\", ");
            }

            sb.setLength(sb.length() - 2);
            sb.append("], ");
            sb.append("\"uses\":" + executables.getValue().uses() + ", ");
            sb.append("\"cooldown\":" + executables.getValue().cooldown() + ", ");
            sb.append("\"elevated\":" + executables.getValue().hasElevation() + ", ");
            sb.append("\"hasCooldown\":" + executables.getValue().hasCooldown());
            sb.append("}, ");

        }

        sb.setLength(sb.length() - 2);
        sb.append("}");
        sb.append("}");

        Assertions.assertEquals(sb.toString(), component.toString());
    }
}
