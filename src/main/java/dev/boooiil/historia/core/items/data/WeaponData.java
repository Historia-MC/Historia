package dev.boooiil.historia.core.items.data;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.ItemData;
import dev.boooiil.historia.core.util.*;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * @param sweeping private String id;
 */
public record WeaponData(float sweeping) implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, WeaponData> DATA_TYPE = new DataType();
    public static final NamespacedKey KEY = HistoriaCore.getNamespacedKey("weapon");

    // String id,
    // this.id = id;

    public static WeaponData fromStack(ItemStack stack) {

        return PDCUtils
                .getFromComplexContainer(stack, WeaponData.KEY, WeaponData.DATA_TYPE)
                .orElse(new WeaponData(0));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    private void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, WeaponData.KEY, WeaponData.DATA_TYPE, this);

        ItemMeta meta = stack.getItemMeta();
        AttributeModifier sweepingAttr = new AttributeModifier(HistoriaCore.getNamespacedKey("weapon-sweeping"),
                this.sweeping,
                AttributeModifier.Operation.ADD_NUMBER);

        meta.addAttributeModifier(Attribute.SWEEPING_DAMAGE_RATIO, sweepingAttr);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);

        // stack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        // ItemAttributeModifiers.itemAttributes()
        // .addModifier(Attribute.SWEEPING_DAMAGE_RATIO, sweepingAttr));
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

            if (KyoriUtils.contains(component, "<weapon-sweeping>")) {

                CoreLogger.debugToConsole(configId, "has sweeping placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "weapon-sweeping", this.sweeping));

                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public String id() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String toString() {

        String sb = "WeaponData" +
                toJSON();

        return sb;
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromValue("sweep", sweeping) +
                "}";

        return sb;
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, WeaponData> {

        private static final NamespacedKey SWEEPING_KEY = HistoriaCore.getNamespacedKey("sweeping");

        @Override
        public WeaponData fromPrimitive(PersistentDataContainer container,
                                        PersistentDataAdapterContext adapterContext) {

            float sweeping = container.get(SWEEPING_KEY, PersistentDataType.FLOAT);

            return new WeaponData(
                    NumberUtils.roundFloat(sweeping, 2));
        }

        @Override
        public Class<WeaponData> getComplexType() {
            return WeaponData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(WeaponData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(SWEEPING_KEY, PersistentDataType.FLOAT, data.sweeping());

            return container;
        }
    }
}
