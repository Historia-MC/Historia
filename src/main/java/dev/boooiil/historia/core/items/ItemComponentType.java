package dev.boooiil.historia.core.items;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.items.component.*;
import dev.boooiil.historia.core.items.data.*;
import dev.boooiil.historia.core.registry.RegistryHolder;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemComponentType<T extends ItemComponent> implements JSONSerializable {

    private final Function<ConfigurationSection, T> fromConfig;
    private final Supplier<? extends ItemData> defaultData;

    public ItemComponentType(
            Function<ConfigurationSection, T> fromConfig,
            Supplier<? extends ItemData> defaultData) {
        this.fromConfig = fromConfig;
        this.defaultData = defaultData;
    }

    public T fromConfig(ConfigurationSection section) {
        return fromConfig.apply(section);
    }

    public ItemData getData() {
        return defaultData.get();
    }

    public static void registerComponents() {
        RegistryHolder.COMPONENT_REGISTRY.register(
                HistoriaCore.getNamespacedKey("tool"),
                new ItemComponentType<>(
                        ToolComponent::fromConfig,
                        () -> new ToolData(1, 1, 1, 1)));

        RegistryHolder.COMPONENT_REGISTRY.register(
                HistoriaCore.getNamespacedKey("weapon"),
                new ItemComponentType<>(
                        WeaponComponent::fromConfig,
                        () -> new WeaponData(1)));

        RegistryHolder.COMPONENT_REGISTRY.register(
                HistoriaCore.getNamespacedKey("armor"),
                new ItemComponentType<>(
                        ArmorComponent::fromConfig,
                        () -> new ArmorData(1, 1)));

        RegistryHolder.COMPONENT_REGISTRY.register(
                HistoriaCore.getNamespacedKey("executor"),
                new ItemComponentType<>(
                        ExecutorComponent::fromConfig,
                        () -> new ExecutorData(new HashMap<>())));

        RegistryHolder.COMPONENT_REGISTRY.register(
                HistoriaCore.getNamespacedKey("runnable"),
                new ItemComponentType<>(
                        RunnableComponent::fromConfig,
                        () -> new RunnableData(0, "", "")));

        RegistryHolder.COMPONENT_REGISTRY.register(
                HistoriaCore.getNamespacedKey("enchant"),
                new ItemComponentType<>(
                        EnchantComponent::fromConfig,
                        () -> new EnchantData(new HashMap<>())));
    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromValue("armor", new ArmorData(1, 1)) + "," +
                JSONUtils.fromValue("tool", new ToolData(1, 1, 1, 1)) + "," +
                JSONUtils.fromValue("weapon", new WeaponData(1)) + "," +
                JSONUtils.fromValue("executor", new ExecutorData(new HashMap<>())) + "," +
                JSONUtils.fromValue("runnable", new RunnableData(0, "", "")) + "," +
                JSONUtils.fromValue("enchant", new EnchantData(new HashMap<>())) +
                "}";

        return sb;
    }
}
