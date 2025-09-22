package dev.boooiil.historia.core.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
public class PDCUtils {

    public static Optional<PersistentDataContainer> getContainer(ItemStack item) {
        if (item.hasItemMeta()) {
            return Optional.of(getContainer(item.getItemMeta()));
        } else {
            CoreLogger.debugToConsole("Tried to obtain the data container for", item.getType().name(),
                    "but it did not have any meta.");

            // cannot guarantee that every item passed through here is going to have a meta
            return Optional.empty();
        }
    }

    public static PersistentDataContainer getContainer(ItemMeta itemMeta) {
        return itemMeta.getPersistentDataContainer();
    }

    public static <T> Optional<T> getFromContainer(PersistentDataContainer container, NamespacedKey key,
                                                   PersistentDataType<T, T> type) {
        if (container.has(key)) {
            return Optional.ofNullable(container.get(key, type));
        } else
            return Optional.empty();
    }

    public static <T> Optional<T> getFromContainer(ItemMeta itemMeta, NamespacedKey key,
                                                   PersistentDataType<T, T> type) {
        return getFromContainer(getContainer(itemMeta), key, type);
    }

    public static <T> Optional<T> getFromContainer(ItemStack item, NamespacedKey key,
                                                   PersistentDataType<T, T> type) {

        if (item.hasItemMeta()) {
            return getFromContainer(getContainer(item).get(), key, type);
        } else {
            CoreLogger.debugToConsole("Tried to obtain data from the data container for", item.getType().name(),
                    "but it did not have any meta.");
            return Optional.empty();
        }

    }

    public static <C, T> Optional<T> getFromComplexContainer(PersistentDataContainer container, NamespacedKey key,
                                                             PersistentDataType<C, T> type) {
        if (container.has(key)) {
            return Optional.ofNullable(container.get(key, type));
        } else
            return Optional.empty();
    }

    public static <C, T> Optional<T> getFromComplexContainer(ItemMeta itemMeta, NamespacedKey key,
                                                             PersistentDataType<C, T> type) {
        return getFromComplexContainer(getContainer(itemMeta), key, type);
    }

    public static <C, T> Optional<T> getFromComplexContainer(ItemStack item, NamespacedKey key,
                                                             PersistentDataType<C, T> type) {

        if (item.hasItemMeta()) {
            return getFromComplexContainer(getContainer(item).get(), key, type);
        } else {
            CoreLogger.debugToConsole("Tried to obtain data from the data container for", item.getType().name(),
                    "but it did not have any meta.");
            return Optional.empty();
        }
    }

    public static <T> void setInContainer(PersistentDataContainer container, NamespacedKey key,
                                          PersistentDataType<T, T> type, T value) {

        CoreLogger.debugToConsole("Setting into container:", "" + key, "" + type.getPrimitiveType(), "" + value);
        container.set(key, type, value);
    }

    public static <T> void setInContainer(ItemMeta itemMeta, NamespacedKey key,
                                          PersistentDataType<T, T> type, T value) {
        setInContainer(getContainer(itemMeta), key, type, value);
    }

    public static <T> void setInContainer(ItemStack item, NamespacedKey key,
                                          PersistentDataType<T, T> type, T value) {

        ItemMeta meta = item.getItemMeta();

        setInContainer(meta, key, type, value);

        item.setItemMeta(meta);
    }

    public static <C, T> void setInComplexContainer(PersistentDataContainer container, NamespacedKey key,
                                                    PersistentDataType<C, T> type, T value) {

        CoreLogger.debugToConsole("Setting into complex container:", "" + key, "" + type.getPrimitiveType(), "" + value);
        container.set(key, type, value);
    }

    public static <C, T> void setInComplexContainer(ItemMeta itemMeta, NamespacedKey key,
                                                    PersistentDataType<C, T> type, T value) {
        setInComplexContainer(getContainer(itemMeta), key, type, value);
    }

    public static <C, T> void setInComplexContainer(ItemStack item, NamespacedKey key,
                                                    PersistentDataType<C, T> type, T value) {

        ItemMeta meta = item.getItemMeta();

        setInComplexContainer(meta, key, type, value);

        item.setItemMeta(meta);
    }

}
