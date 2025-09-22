package dev.boooiil.historia.core.items.data;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.ItemData;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.PDCUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;

public record EnchantData(HashMap<Enchantment, Integer> enchantments) implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, EnchantData> DATA_TYPE = new DataType();
    public static final NamespacedKey KEY = HistoriaCore.getNamespacedKey("enchant");

    public static EnchantData fromStack(ItemStack stack) {
        return PDCUtils.getFromComplexContainer(stack, EnchantData.KEY, EnchantData.DATA_TYPE)
                .orElse(new EnchantData(new HashMap<>()));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {
        PDCUtils.setInComplexContainer(stack, EnchantData.KEY, EnchantData.DATA_TYPE, this);
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, EnchantData> {

        @Override
        public EnchantData fromPrimitive(PersistentDataContainer container,
                                         PersistentDataAdapterContext adapterContext) {

            HashMap<Enchantment, Integer> enchantments = new HashMap<>();

            for (NamespacedKey enchant : container.getKeys()) {
                Enchantment enchantment = Enchantment.getByName(enchant.getKey());

                if (enchantment == null) {
                    continue;
                }

                enchantments.put(enchantment, container.get(enchant, PersistentDataType.INTEGER));
            }

            return new EnchantData(enchantments);
        }

        @Override
        public Class<EnchantData> getComplexType() {
            return EnchantData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(EnchantData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            for (Enchantment enchant : data.enchantments.keySet()) {
                container.set(enchant.getKey(), PersistentDataType.INTEGER, data.enchantments.get(enchant));
            }

            return container;
        }
    }

    @Override
    public String toString() {

        String sb = "EnchantData" +
                "{" +
                JSONUtils.fromMap("enchantments", enchantments, true) +
                "}";

        return sb;

    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromMap("enchantments", enchantments) +
                "}";

        return sb;

    }

}
