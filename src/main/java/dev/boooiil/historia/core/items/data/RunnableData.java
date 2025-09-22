package dev.boooiil.historia.core.items.data;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.ItemData;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.PDCUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

public class RunnableData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, RunnableData> DATA_TYPE = new RunnableData.DataType();

    public static final NamespacedKey KEY = HistoriaCore.getNamespacedKey("runnable");

    private int ticks;
    private String command;
    private String permission;

    public RunnableData(
            int ticks,
            String command,
            String permission) {

    }

    public static RunnableData fromStack(ItemStack stack) {
        return PDCUtils.getFromComplexContainer(stack, RunnableData.KEY, RunnableData.DATA_TYPE)
                .orElse(new RunnableData(-1, "", ""));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {
        PDCUtils.setInComplexContainer(stack, RunnableData.KEY, RunnableData.DATA_TYPE, this);
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, RunnableData> {

        private static final NamespacedKey TICKS_KEY = HistoriaCore.getNamespacedKey("ticks");
        private static final NamespacedKey COMMAND_KEY = HistoriaCore.getNamespacedKey("command");
        private static final NamespacedKey PERMISSION_KEY = HistoriaCore.getNamespacedKey("permission");

        @Override
        public RunnableData fromPrimitive(PersistentDataContainer container,
                                          PersistentDataAdapterContext adapterContext) {

            int ticks = container.get(TICKS_KEY, PersistentDataType.INTEGER);
            String command = container.get(COMMAND_KEY, PersistentDataType.STRING);
            String permission = container.get(PERMISSION_KEY, PersistentDataType.STRING);

            return new RunnableData(ticks, command, permission);
        }

        @Override
        public Class<RunnableData> getComplexType() {
            return RunnableData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(RunnableData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(TICKS_KEY, PersistentDataType.INTEGER, data.ticks);
            container.set(COMMAND_KEY, PersistentDataType.STRING, data.command);
            container.set(PERMISSION_KEY, PersistentDataType.STRING, data.permission);

            return container;
        }
    }

    @Override
    public String toString() {

        String sb = "RunnableData" +
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
