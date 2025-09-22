package dev.boooiil.historia.core.items.component;

import dev.boooiil.historia.core.items.ItemComponent;
import dev.boooiil.historia.core.items.data.ExecutorData;
import dev.boooiil.historia.core.items.executor.ItemExecutable;
import dev.boooiil.historia.core.items.types.Triggers;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NullMarked
public record ExecutorComponent(HashMap<Triggers, ItemExecutable> executables) implements ItemComponent {

    public static ExecutorComponent fromConfig(ConfigurationSection section) {
        HashMap<Triggers, ItemExecutable> executables = new HashMap<>();
        List<Triggers> triggers = new ArrayList<>();

        // get valid triggers from the config
        for (String sTrigger : section.getKeys(false)) {

            Triggers trigger = Triggers.fromString(sTrigger);

            if (trigger == Triggers.UNKNOWN) {
                CoreLogger.errorToConsole("Tried to get trigger", sTrigger, "from executor but it does not exist.");
                continue;
            }

            triggers.add(trigger);

        }

        for (Triggers trigger : triggers) {

            ConfigurationSection triggerSection = section.getConfigurationSection(trigger.getLowercase());

            List<String> commands = triggerSection.getStringList("commands");
            Integer cooldown = triggerSection.getInt("cooldown");
            Integer uses = triggerSection.getInt("uses");
            boolean hasElevation = triggerSection.getBoolean("elevation");

            executables.put(trigger, new ItemExecutable(commands, cooldown, uses, hasElevation, false));

        }

        return new ExecutorComponent(executables);
    }

    @Override
    public ExecutorData data() {
        return new ExecutorData(executables);
    }

    @Override
    public ExecutorData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "executor";
    }

    @Override
    public String toString() {

        if (executables.size() == 0)
            return "ExecutorComponent{}";

        String sb = "ExecutorComponent" +
                "{" +
                JSONUtils.fromMap("executables", executables, true) +
                "}";

        return sb;

    }

    @Override
    public String toJSON() {

        if (executables.size() == 0)
            return "{}";

        String sb = "{" +
                JSONUtils.fromMap("executables", executables) +
                "}";

        return sb;
    }

}
