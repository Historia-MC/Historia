package dev.boooiil.historia.core.items.data;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.ItemData;
import dev.boooiil.historia.core.items.types.Qualities;
import dev.boooiil.historia.core.items.types.Weights;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.KyoriUtils;
import dev.boooiil.historia.core.util.PDCUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * @param weight private String id;
 */
public record ModifierData(Weights weight, Qualities quality) implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ModifierData> DATA_TYPE = new DataType();
    public static final NamespacedKey KEY = HistoriaCore.getNamespacedKey("modifier");

    // String id,
    // this.id = id;

    public static ModifierData fromStack(ItemStack stack) {

        return PDCUtils.getFromComplexContainer(stack, ModifierData.KEY, ModifierData.DATA_TYPE)
                .orElse(new ModifierData(Weights.LIGHT, Qualities.POOR));

    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    private void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, ModifierData.KEY,
                ModifierData.DATA_TYPE, this);

    }

    private void writeLore(ItemStack stack) {

        String configId = PDCUtils.getFromContainer(stack,
                HistoriaCore.getNamespacedKey("item-id"), PersistentDataType.STRING).orElse("");

        ItemMeta meta = stack.getItemMeta();

        if (!meta.hasLore() || meta.lore().isEmpty()) {
            CoreLogger.debugToConsole(configId, "has no lore, skipping placeholder.");
            return;
        }

        List<Component> lore = meta.lore();
        List<Component> nLore = new ArrayList<>();

        for (Component component : lore) {

            if (KyoriUtils.contains(component, "<modifier-weight>")) {

                CoreLogger.debugToConsole(configId, "has modifier weight placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "modifier-weight", this.weight.getDisplayName()));

                continue;
            }

            if (KyoriUtils.contains(component, "<modifier-quality>")) {

                CoreLogger.debugToConsole(configId, "has modifier quality placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "modifier-quality", this.quality.getDisplayName()));

                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public String id() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public String toString() {

        String sb = "ModifierData" +
                toJSON();

        return sb;
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromValue("weight", weight.lowercase()) + ", " +
                JSONUtils.fromValue("quality", quality.lowercase()) +
                "}";

        return sb;
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ModifierData> {

        private static final NamespacedKey WEIGHT_KEY = HistoriaCore.getNamespacedKey("weight");
        private static final NamespacedKey QUALITY_KEY = HistoriaCore.getNamespacedKey("quality");

        @Override
        public ModifierData fromPrimitive(PersistentDataContainer container,
                                          PersistentDataAdapterContext adapterContext) {

            Weights weight = Weights
                    .fromString(container.get(WEIGHT_KEY, PersistentDataType.STRING));
            Qualities quality = Qualities
                    .fromString(container.get(QUALITY_KEY, PersistentDataType.STRING));

            return new ModifierData(weight, quality);
        }

        @Override
        public Class<ModifierData> getComplexType() {
            return ModifierData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ModifierData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(WEIGHT_KEY, PersistentDataType.STRING, data.weight.lowercase());
            container.set(QUALITY_KEY, PersistentDataType.STRING, data.quality().lowercase());

            return container;
        }
    }
}
