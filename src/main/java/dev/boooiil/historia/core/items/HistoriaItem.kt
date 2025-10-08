package dev.boooiil.historia.core.items

import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.configuration.specific.LoreConfiguration
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONSerializable
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.PDCUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*

class HistoriaItem(
    val configurationId: NamespacedKey,
    /**
     * @return the displayName
     */
    val displayName: String,
    /**
     * @return the baseMaterial
     */
    val baseMaterial: Material,
    private val lore: MutableList<Component>,
    /**
     * The weight of the item in KG. We are a metric society, damn the imperialists.
     */
    val weight: Double,
    /**
     * @return the components
     */
    val componentHolder: MutableMap<NamespacedKey, ItemComponent>
) : JSONSerializable {
    /**
     * @return the weight
     */

    fun putComponent(key: NamespacedKey, components: ItemComponent) {
        this.componentHolder.put(key, components)
    }

    fun putComponents(components: HashMap<NamespacedKey, ItemComponent>) {
        this.componentHolder.putAll(components)
    }

    /**
     * Creates a default [ItemStack] of this configuration with the specified amount.
     *
     * @return the created [ItemStack].
     */
    /**
     * Creates a default [ItemStack] of this configuration.
     *
     * @return the created [ItemStack].
     */
    @JvmOverloads
    fun createItemStack(amount: Int = 1, qualityModifier: Double? = null): ItemStack {
        // invalid material

        assert(baseMaterial != Material.AIR)

        val stack = ItemStack(baseMaterial, amount)
        val meta = stack.itemMeta
        val textComponent = Component.text(displayName)

        PDCUtils.setInContainer<String>(
            meta, getNamespacedKey("item-id"),
            PersistentDataType.STRING, configurationId.key
        )

        meta.displayName(textComponent)
        meta.lore(lore)
        stack.setItemMeta(meta)

        for (component in this.componentHolder.values) {
            val data = component.data(qualityModifier)
            data.apply(stack)
        }

        return stack

        // for (ItemComponent component : componentHolder.values()) {
        // component.setDefaultsToMeta(item);
        // }

        // thoughts on applying lore:
        // %placeholder%
        // %weapon.sweeping% where "weapon" is the component and can be found through
        // HistoriaItem.getValue(weapon.sweeping)

        // return item;
    }

    override fun toString(): String {
        val sb = "HistoriaItem" +
                "{" +
                JSONUtils.fromValue("id", configurationId.key) + ", " +
                JSONUtils.fromValue("displayName", displayName) + ", " +
                JSONUtils.fromValue("baseMaterial", baseMaterial.name.lowercase(Locale.getDefault())) + ", " +
                JSONUtils.fromValue("weight", weight) + ", " +
                JSONUtils.fromComponentList("lore", lore) + ", " +
                JSONUtils.fromMap<NamespacedKey, ItemComponent>("components", this.componentHolder, true) +
                "}"

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromValue("id", configurationId.key) + ", " +
                JSONUtils.fromValue("displayName", displayName) + ", " +
                JSONUtils.fromValue("baseMaterial", baseMaterial.name.lowercase(Locale.getDefault())) + ", " +
                JSONUtils.fromValue("weight", weight) + ", " +
                JSONUtils.fromComponentList("lore", lore) + ", " +
                JSONUtils.fromMap<NamespacedKey, ItemComponent>("components", this.componentHolder) +
                "}"

        return sb
    }

    companion object {
        @JvmStatic
        fun fromConfig(id: NamespacedKey, section: ConfigurationSection): HistoriaItem {
            val baseMaterial = Material.valueOf(section.getString("material")!!)
            val displayName = section.getString("display-name")
            val weight = section.getDouble("weight")

            CoreLogger.verboseToConsole(
                baseMaterial.toString(), displayName!!, weight.toString(),
                section.getKeys(false).toString()
            )

            CoreLogger.verboseToConsole("COMPONENT_REGISTRY KEYS:", RegistryHolder.COMPONENT_REGISTRY.keys.toString())

            val components: MutableMap<NamespacedKey, ItemComponent> = HashMap<NamespacedKey, ItemComponent>()
            for (entry in RegistryHolder.COMPONENT_REGISTRY.entries) {
                val key = entry.key
                CoreLogger.verboseToConsole("Checking", id.key, " for component:", key.key)
                if (section.contains(key.key)) {
                    CoreLogger.verboseToConsole(displayName, "has a component of type", key.key)
                    val type = entry.value
                    val componentSection = section.getConfigurationSection(key.key)
                    components.put(key, type.fromConfig(componentSection!!))
                }
            }

            val lore: MutableList<Component> = ArrayList<Component>()
            if (section.contains("lore")) {
                val loreList = section.getStringList("lore")
                for (sLore in loreList) {
                    lore.add(Component.text(sLore))
                }
            }
            if (!components.isEmpty()) {
                for (key in components.keys) {
                    val sKey = key.key

                    if (LoreConfiguration.contains(sKey)) {
                        lore.add(Component.text("[${sKey.uppercase()}]"))

                        val cLore = LoreConfiguration.get(sKey)

                        for (sLore in cLore!!.get("head")!!) {
                            lore.add(Component.text(sLore))
                        }

                        lore.add(Component.text(""))

                        for (sLore in cLore.get("attribute")!!) {
                            lore.add(Component.text(sLore))
                        }

                        lore.add(Component.text(""))
                    }
                }

                val loreList: MutableList<String> = LoreConfiguration.get("weight").get("attribute")!!
                for (sLore in loreList) {
                    lore.add(Component.text(sLore))
                }
            }

            return HistoriaItem(id, displayName, baseMaterial, lore, weight, components)
        }
    }
}
