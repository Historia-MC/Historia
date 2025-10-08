package dev.boooiil.historia.core.items.component;

import dev.boooiil.historia.core.items.ItemComponent;
import dev.boooiil.historia.core.items.data.RunnableData;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.configuration.ConfigurationSection;

public class RunnableComponent implements ItemComponent {

    private final int ticks;
    private final String command;
    private final String permission;

    public RunnableComponent(int ticks, String command, String permission) {
        this.ticks = ticks;
        this.command = command;
        this.permission = permission;
    }

    public static RunnableComponent fromConfig(ConfigurationSection section) {

        int ticks = section.getInt("ticks");
        String command = section.getString("command");
        String permission = section.getString("permission");

        return new RunnableComponent(ticks, command, permission);

    }

    @Override
    public RunnableData data() {
        return new RunnableData(this.ticks, this.command, this.permission);
    }

    @Override
    public RunnableData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "runnable";
    }

    @Override
    public String toString() {

        String sb = "RunnableComponent" +
                "{" +
                JSONUtils.fromValue("ticks", ticks) + ", " +
                JSONUtils.fromValue("command", command) + ", " +
                JSONUtils.fromValue("permission", permission) +
                "}";

        return sb;

    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromValue("ticks", ticks) + ", " +
                JSONUtils.fromValue("command", command) + ", " +
                JSONUtils.fromValue("permission", permission) +
                "}";

        return sb;

    }

}
