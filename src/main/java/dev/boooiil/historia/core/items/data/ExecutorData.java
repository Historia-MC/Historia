package dev.boooiil.historia.core.items.data;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.ItemData;
import dev.boooiil.historia.core.items.executor.ItemExecutable;
import dev.boooiil.historia.core.items.types.Triggers;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.KyoriUtils;
import dev.boooiil.historia.core.util.PDCUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;

/**
 * @param executables private String id;
 */
public record ExecutorData(HashMap<Triggers, ItemExecutable> executables) implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ExecutorData> DATA_TYPE = new DataType();
    public static final NamespacedKey KEY = HistoriaCore.getNamespacedKey("executor");

    // String id,

    public static ExecutorData fromStack(ItemStack stack) {

        return PDCUtils.getFromComplexContainer(stack, ExecutorData.KEY,
                ExecutorData.DATA_TYPE).orElse(new ExecutorData(new HashMap<>()));

    }

    public static ExecutorData defaults() {
        return new ExecutorData(new HashMap<>());
    }

    public void execute(HumanEntity humanEntity, Integer slot, ItemStack item, Triggers trigger) {

        CoreLogger.debugToConsole("Found executor item in slot " + slot + " for player " + humanEntity.getName());

        // returns if no trigger
        if (!executables.containsKey(trigger)) {
            CoreLogger.errorToConsole(
                    "Player " + humanEntity.getName() + " tried to execute trigger " + trigger + " on item "
                            + KyoriUtils.content(item.getItemMeta().displayName()) + " but no executable was found.");
            CoreLogger.errorToConsole("Possible executables: " + executables.keySet());
            return;
        }

        ItemExecutable itemExecutable = executables.get(trigger);

        // if not on cooldown
        if (!itemExecutable.hasCooldown()) {

            itemExecutable.execute(humanEntity, item);

            // returns if keys are empty
            if (itemExecutable.uses() <= 0) {
                CoreLogger.debugToConsole("Removing trigger " + trigger + " from item "
                        + KyoriUtils.content(item.getItemMeta().displayName()));
                executables.remove(trigger);

                // returns
                if (executables.isEmpty()) {
                    CoreLogger.debugToConsole("Removing item "
                            + KyoriUtils.content(item.getItemMeta().displayName()));
                    humanEntity.getInventory().remove(item);

                    return; // data does not need to be written
                }
            }

            // set cooldown
            if (itemExecutable.uses() > 0) {
                CoreLogger.debugToConsole(
                        "Setting cooldown for item " + KyoriUtils.content(item.getItemMeta().displayName())
                                + " to " + itemExecutable.cooldown());

                // MockBukkit@1.21.4 Unimplemented
                if (!HistoriaCore.isTesting) {
                    humanEntity.setCooldown(item, itemExecutable.cooldown());
                }
                System.out.println(this);
            }

            CoreLogger.debugToConsole("before",
                    item.getItemMeta().getPersistentDataContainer().get(HistoriaCore.getNamespacedKey("executor"),
                            ExecutorData.DATA_TYPE).toString());

            writeData(item);

            humanEntity.getInventory().setItem(slot, item);

            CoreLogger.debugToConsole("after",
                    item.getItemMeta().getPersistentDataContainer().get(HistoriaCore.getNamespacedKey("executor"),
                            ExecutorData.DATA_TYPE).toString());
        }

    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, ExecutorData.KEY,
                ExecutorData.DATA_TYPE, this);
    }

    public String id() {
        throw new UnsupportedOperationException("Not implemented");
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

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ExecutorData> {

        private static final NamespacedKey EXECUTABLES_KEY = HistoriaCore.getNamespacedKey("executables");

        @Override
        public ExecutorData fromPrimitive(PersistentDataContainer container,
                                          PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer executablesContainer = container.get(EXECUTABLES_KEY,
                    PersistentDataType.TAG_CONTAINER);

            HashMap<Triggers, ItemExecutable> executables = new HashMap<>();
            for (NamespacedKey key : executablesContainer.getKeys()) {
                Triggers trigger = Triggers.fromString(key.getKey());

                CoreLogger.debugToConsole("loading key: " + key);
                ItemExecutable executable = executablesContainer.get(key, ItemExecutable.DATA_TYPE);

                executables.put(trigger, executable);
            }

            return new ExecutorData(executables);
        }

        @Override
        public Class<ExecutorData> getComplexType() {
            return ExecutorData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ExecutorData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();
            PersistentDataContainer executablesContainer = adapterContext.newPersistentDataContainer();

            for (Triggers trigger : data.executables().keySet()) {
                executablesContainer.set(HistoriaCore.getNamespacedKey(trigger.getLowercase()),
                        ItemExecutable.DATA_TYPE, data.executables.get(trigger));
            }

            container.set(EXECUTABLES_KEY, PersistentDataType.TAG_CONTAINER, executablesContainer);

            return container;
        }
    }
}
