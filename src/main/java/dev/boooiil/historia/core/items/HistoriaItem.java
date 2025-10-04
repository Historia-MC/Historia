package dev.boooiil.historia.core.items;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.configuration.specific.LoreConfiguration;
import dev.boooiil.historia.core.registry.RegistryHolder;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public class HistoriaItem implements JSONSerializable {

    private final NamespacedKey id;
    private final String displayName;
    private final Material baseMaterial;
    private final List<Component> lore;

    /**
     * The weight of the item in KG. We are a metric society, damn the imperialists.
     */
    private final double weight;

    private final Map<NamespacedKey, ItemComponent> components;

    public HistoriaItem(
            NamespacedKey id,
            String displayName,
            Material baseMaterial,
            List<Component> lore,
            double weight,
            Map<NamespacedKey, ItemComponent> components) {
        this.id = id;
        this.displayName = displayName;
        this.baseMaterial = baseMaterial;
        this.lore = lore;
        this.weight = weight;
        this.components = components;
    }

    public static HistoriaItem fromConfig(NamespacedKey id, ConfigurationSection section) {
        Material baseMaterial = Material.valueOf(section.getString("material"));
        String displayName = section.getString("display-name");
        Double weight = section.getDouble("weight");

        CoreLogger.verboseToConsole(baseMaterial.toString(), displayName, weight.toString(),
                section.getKeys(false).toString());

        CoreLogger.verboseToConsole("COMPONENT_REGISTRY KEYS:", RegistryHolder.COMPONENT_REGISTRY.keySet().toString());

        Map<NamespacedKey, ItemComponent> components = new HashMap<>();
        for (Map.Entry<NamespacedKey, ItemComponentType<? extends ItemComponent>> entry : RegistryHolder.COMPONENT_REGISTRY.entrySet()) {
            NamespacedKey key = entry.getKey();
            CoreLogger.verboseToConsole("Checking", id.getKey(), " for component:", key.getKey());
            if (section.contains(key.getKey())) {
                CoreLogger.verboseToConsole(displayName, "has a component of type", key.getKey());
                ItemComponentType<?> type = entry.getValue();
                ConfigurationSection componentSection = section.getConfigurationSection(key.getKey());
                components.put(key, type.fromConfig(componentSection));
            }
        }

        List<Component> lore = new ArrayList<>();
        if (section.contains("lore")) {
            List<String> loreList = section.getStringList("lore");
            for (String sLore : loreList) {
                lore.add(Component.text(sLore));
            }
        }
        if (!components.isEmpty()) {

            for (NamespacedKey key : components.keySet()) {

                String s_key = key.getKey();

                if (LoreConfiguration.contains(s_key)) {
                    lore.add(Component.text("[" + s_key.toUpperCase() + "]"));

                    HashMap<String, List<String>> cLore = LoreConfiguration.get(s_key);

                    for (String sLore : cLore.get("head")) {
                        lore.add(Component.text(sLore));
                    }

                    lore.add(Component.text(""));

                    for (String sLore : cLore.get("attribute")) {
                        lore.add(Component.text(sLore));
                    }

                    lore.add(Component.text(""));

                }
            }

            List<String> loreList = LoreConfiguration.get("weight").get("attribute");
            for (String sLore : loreList) {
                lore.add(Component.text(sLore));
            }

        }

        return new HistoriaItem(id, displayName, baseMaterial, lore, weight, components);
    }

    public void putComponent(NamespacedKey key, ItemComponent components) {
        this.components.put(key, components);
    }

    public void putComponents(HashMap<NamespacedKey, ItemComponent> components) {
        this.components.putAll(components);
    }

    public NamespacedKey getConfigurationId() {
        return this.id;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the baseMaterial
     */
    public Material getBaseMaterial() {
        return baseMaterial;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the components
     */
    public Map<NamespacedKey, ItemComponent> getComponentHolder() {
        return this.components;
    }

    /**
     * Creates a default {@link ItemStack} of this configuration.
     *
     * @return the created {@link ItemStack}.
     */
    public ItemStack createItemStack() {
        return createItemStack(1);
    }

    /**
     * Creates a default {@link ItemStack} of this configuration with the specified amount.
     *
     * @return the created {@link ItemStack}.
     */
    public ItemStack createItemStack(int amount) {

        // invalid material
        assert (baseMaterial != null && baseMaterial != Material.AIR);

        ItemStack stack = new ItemStack(baseMaterial, amount);
        ItemMeta meta = stack.getItemMeta();
        TextComponent textComponent = Component.text(displayName);

        PDCUtils.setInContainer(meta, HistoriaCore.getNamespacedKey("item-id"),
                PersistentDataType.STRING, id.getKey());

        meta.displayName(textComponent);
        meta.lore(lore);
        stack.setItemMeta(meta);

        for (ItemComponent component : this.components.values()) {
            ItemData data = component.data();
            data.apply(stack);
        }

        return stack;

        // for (ItemComponent component : componentHolder.values()) {
        // component.setDefaultsToMeta(item);
        // }

        // thoughts on applying lore:
        // %placeholder%
        // %weapon.sweeping% where "weapon" is the component and can be found through
        // HistoriaItem.getValue(weapon.sweeping)

        // return item;

    }

    @Override
    public String toString() {

        String sb = "HistoriaItem" +
                "{" +
                JSONUtils.fromValue("id", id.getKey()) + ", " +
                JSONUtils.fromValue("displayName", displayName) + ", " +
                JSONUtils.fromValue("baseMaterial", baseMaterial.name().toLowerCase()) + ", " +
                JSONUtils.fromValue("weight", weight) + ", " +
                JSONUtils.fromComponentList("lore", lore) + ", " +
                JSONUtils.fromMap("components", components, true) +
                "}";

        return sb;

    }

    @Override
    public String toJSON() {

        String sb = "{" +
                JSONUtils.fromValue("id", id.getKey()) + ", " +
                JSONUtils.fromValue("displayName", displayName) + ", " +
                JSONUtils.fromValue("baseMaterial", baseMaterial.name().toLowerCase()) + ", " +
                JSONUtils.fromValue("weight", weight) + ", " +
                JSONUtils.fromComponentList("lore", lore) + ", " +
                JSONUtils.fromMap("components", components) +
                "}";

        return sb;
    }

}
