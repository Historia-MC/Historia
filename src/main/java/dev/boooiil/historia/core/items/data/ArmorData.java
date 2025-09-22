package dev.boooiil.historia.core.items.data;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.ItemData;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.KyoriUtils;
import dev.boooiil.historia.core.util.PDCUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * @param defense private String id;
 */
public record ArmorData(float defense, int maxDurability) implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ArmorData> DATA_TYPE = new DataType();
    public static final NamespacedKey KEY = HistoriaCore.getNamespacedKey("armor");

    // String id,
    // this.id = id;

    public static ArmorData fromStack(ItemStack stack) {

        return PDCUtils
                .getFromComplexContainer(stack, ArmorData.KEY, ArmorData.DATA_TYPE)
                .orElse(new ArmorData(0, 1));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    private void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, ArmorData.KEY, ArmorData.DATA_TYPE, this);

        AttributeModifier defenseAttr = new AttributeModifier(HistoriaCore.getNamespacedKey("armor-defense"),
                this.defense, AttributeModifier.Operation.ADD_NUMBER);

        ItemMeta meta = stack.getItemMeta();
        Damageable damageable = (Damageable) meta;

        damageable.addAttributeModifier(Attribute.ARMOR, defenseAttr);

        damageable.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        damageable.setMaxDamage(this.maxDurability);

        assert maxDurability > 1;

        stack.setItemMeta(damageable);

        // stack.setData(DataComponentTypes.MAX_DAMAGE, this.maxDurability);

        // stack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        // ItemAttributeModifiers.itemAttributes()
        // .addModifier(Attribute.SWEEPING_DAMAGE_RATIO, sweepingAttr));
    }

    private void writeLore(ItemStack stack) {

        ItemMeta meta = stack.getItemMeta();

        if (!meta.hasLore() || meta.lore().isEmpty()) {
            CoreLogger.debugToConsole("Armor has no lore, skipping placeholder.");
            return;
        }

        List<Component> lore = meta.lore();
        List<Component> nLore = new ArrayList<>();

        for (Component component : lore) {

            if (KyoriUtils.contains(component, "<armor-defense>")) {

                CoreLogger.debugToConsole("Armor has defense placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "armor-defense", this.defense));

                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public String id() {
        throw new UnsupportedOperationException("Not imlpemented.");
    }

    public float defense() {
        return this.defense;
    }

    public int maxDurability() {
        return this.maxDurability;
    }

    @Override
    public String toString() {

        String sb = "ArmorData" +
                toJSON();

        return sb;
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromValue("defense", defense) + ", " +
                JSONUtils.fromValue("maxDurability", maxDurability) +
                "}";

        return sb;
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ArmorData> {

        private static final NamespacedKey DEFENSE_KEY = HistoriaCore.getNamespacedKey("defense");
        private static final NamespacedKey DURABILITY_KEY = HistoriaCore.getNamespacedKey("durability");

        @Override
        public ArmorData fromPrimitive(PersistentDataContainer container, PersistentDataAdapterContext adapterContext) {

            float defense = container.get(DEFENSE_KEY, PersistentDataType.FLOAT);
            int maxDurability = container.get(DURABILITY_KEY, PersistentDataType.INTEGER);

            return new ArmorData(defense, maxDurability);
        }

        @Override
        public Class<ArmorData> getComplexType() {
            return ArmorData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ArmorData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(DEFENSE_KEY, PersistentDataType.FLOAT, data.defense());
            container.set(DURABILITY_KEY, PersistentDataType.INTEGER, data.maxDurability());

            return container;
        }
    }
}
