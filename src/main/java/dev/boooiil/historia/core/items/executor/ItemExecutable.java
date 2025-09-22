package dev.boooiil.historia.core.items.executor;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.KyoriUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class ItemExecutable implements JSONSerializable {

    public static final PersistentDataType<PersistentDataContainer, ItemExecutable> DATA_TYPE = new ItemExecutable.DataType();

    private final List<String> commands;
    // ticks
    private final int cooldown;
    private int uses;
    private boolean hasElevation;
    private final boolean hasCooldown;

    public ItemExecutable(
            List<String> commands,
            int cooldown,
            int uses,
            boolean hasElevation,
            boolean hasCooldown) {
        this.commands = commands;
        this.cooldown = cooldown;
        this.uses = uses;
        this.hasCooldown = hasCooldown;

    }

    public ItemStack execute(HumanEntity humanEntity, ItemStack item) {

        CoreLogger.debugToConsole("Executing commands for " + humanEntity.getName(),
                "with item " + KyoriUtils.content(item.getItemMeta().displayName()));

        for (String command : applyCommandPlaceholder(humanEntity)) {

            if (hasElevation || !(humanEntity instanceof Player))
                HistoriaCore.Companion.getServer().dispatchCommand(HistoriaCore.Companion.getServer().getConsoleSender(), command);
            else
                ((Player) humanEntity).performCommand(command);
        }

        this.uses -= 1;

        return item;
    }

    private List<String> applyCommandPlaceholder(HumanEntity humanEntity) {
        List<String> nCommands = new ArrayList<>();

        for (String command : commands) {

            String nCommand = KyoriUtils.replace(command, "player", humanEntity.getName());

            nCommands.add(nCommand);
        }

        return nCommands;
    }

    public List<String> commands() {
        return this.commands;
    }

    public int cooldown() {
        return this.cooldown;
    }

    public int uses() {
        return this.uses;
    }

    public boolean hasElevation() {
        return this.hasElevation;
    }

    public boolean hasCooldown() {
        return this.hasCooldown;
    }

    @Override
    public String toString() {

        String sb = "ItemExecutable" +
                toJSON();

        return sb;
    }

    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromList("commands", commands) + ", " +
                JSONUtils.fromValue("uses", this.uses) + ", " +
                JSONUtils.fromValue("cooldown", this.cooldown) + ", " +
                JSONUtils.fromValue("elevated", this.hasElevation) + ", " +
                JSONUtils.fromValue("hasCooldown", this.hasCooldown) +
                "}";

        return sb;
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ItemExecutable> {

        private static final NamespacedKey COMMANDS_KEY = HistoriaCore.getNamespacedKey("commands");
        private static final NamespacedKey USES_KEY = HistoriaCore.getNamespacedKey("uses");
        private static final NamespacedKey COOLDOWN_KEY = HistoriaCore.getNamespacedKey("cooldown");
        private static final NamespacedKey ELEVATED_KEY = HistoriaCore.getNamespacedKey("elevated");

        @Override
        public ItemExecutable fromPrimitive(PersistentDataContainer container,
                                            PersistentDataAdapterContext adapterContext) {

            List<String> commands = container.get(COMMANDS_KEY, PersistentDataType.LIST.listTypeFrom(STRING));
            int uses = container.get(USES_KEY, PersistentDataType.INTEGER);
            int cooldown = container.get(COOLDOWN_KEY, PersistentDataType.INTEGER);
            boolean elevated = container.get(ELEVATED_KEY, PersistentDataType.BOOLEAN);

            return new ItemExecutable(commands, cooldown, uses, elevated, false);
        }

        @Override
        public Class<ItemExecutable> getComplexType() {
            return ItemExecutable.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ItemExecutable data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(COMMANDS_KEY, PersistentDataType.LIST.listTypeFrom(STRING), data.commands);
            container.set(USES_KEY, PersistentDataType.INTEGER, data.uses());
            container.set(COOLDOWN_KEY, PersistentDataType.INTEGER, data.cooldown());
            container.set(ELEVATED_KEY, PersistentDataType.BOOLEAN, data.hasElevation());

            return container;
        }
    }
}