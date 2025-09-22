package dev.boooiil.historia.core.items;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class HistoriaItemData {

    private final NamespacedKey id;
    private final List<NamespacedKey> itemData;
    private final ItemStack stack;

    public HistoriaItemData(
            NamespacedKey id,
            List<NamespacedKey> itemData,
            ItemStack stack) {
        this.id = id;
        this.itemData = itemData;
        this.stack = stack;
    }

    public static HistoriaItemData fromStack(ItemStack stack) {

        if (stack == null || stack.getItemMeta() == null) {
            return new HistoriaItemData(HistoriaCore.getNamespacedKey("invalid"), new ArrayList<>(), stack);
        }

        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        String id = "";
        List<NamespacedKey> itemData = new ArrayList<>();

        for (NamespacedKey key : container.getKeys()) {
            String s_key = key.getKey().toLowerCase();

            if (s_key.equals("item-id")) {
                id = container.get(key, PersistentDataType.STRING);
            }

            // else if (s_key.endsWith("-data")) {
            // itemData.add(key);
            // }
            else {
                itemData.add(key);
            }
        }

        return new HistoriaItemData(HistoriaCore.getNamespacedKey(id), itemData, stack);
    }

    public boolean isHistoriaItem() {
        return this.id != null && this.id != HistoriaCore.getNamespacedKey("invalid");
    }

    public boolean hasData(NamespacedKey key) {
        return itemData.contains(key);
    }

    public HistoriaItem getHistoriaItem() {
        return HistoriaCore.Companion.getITEM_REGISTRY().get(id);
    }

    public ItemStack getStack() {
        return stack;
    }

    public <C, T extends ItemData> T getData(
            NamespacedKey key,
            PersistentDataType<C, T> type) {

        if (!hasData(key)) {
            CoreLogger.debugToConsole("No data found for key " + key, "creating one...");
            String s_regKey = key.getKey();
            NamespacedKey regKey = new NamespacedKey(key.getNamespace(), s_regKey);
            Object itemData = HistoriaCore.Companion.getCOMPONENT_REGISTRY().get(regKey).getData();
            return type.getComplexType().cast(itemData);
        }

        return stack.getItemMeta().getPersistentDataContainer().get(key, type);
    }

}
