package dev.boooiil.historia.core.items

import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.items.component.*
import dev.boooiil.historia.core.items.data.*
import dev.boooiil.historia.core.items.data.ModifierData.Companion.KEY
import dev.boooiil.historia.core.items.executor.ItemExecutable
import dev.boooiil.historia.core.items.types.Triggers
import dev.boooiil.historia.core.items.types.Weights
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.JSONSerializable
import dev.boooiil.historia.core.util.JSONUtils
import io.papermc.paper.datacomponent.item.Consumable
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffect
import java.util.function.Function
import java.util.function.Supplier

class ItemComponentType<T : ItemComponent>(
    private val fromConfig: Function<ConfigurationSection, T>,
    private val defaultData: Supplier<out ItemData>
) : JSONSerializable {

    fun fromConfig(section: ConfigurationSection): T {
        return fromConfig.apply(section)
    }

    val data: ItemData
        get() = defaultData.get()

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromValue("armor", ArmorData(1f, 1)) + "," +
                JSONUtils.fromValue("tool", ToolData(1f, 1f, 1f, 1)) + "," +
                JSONUtils.fromValue("weapon", WeaponData(1f)) + "," +
                JSONUtils.fromValue("executor", ExecutorData(HashMap<Triggers?, ItemExecutable?>())) + "," +
                JSONUtils.fromValue("runnable", RunnableData(0, "", "")) + "," +
                JSONUtils.fromValue("enchant", EnchantData(HashMap<Enchantment?, Int?>())) +
                "}"

        return sb
    }

    companion object {
        fun registerComponents() {
            RegistryHolder.COMPONENT_REGISTRY.register(
                KEY,
                ItemComponentType(
                    ModifierComponent::fromConfig
                ) { ModifierData(Weights.LIGHT, null) }
            )

            RegistryHolder.COMPONENT_REGISTRY.register(
                getNamespacedKey("tool"),
                ItemComponentType(
                    ToolComponent::fromConfig
                ) { ToolData(1f, 1f, 1f, 1) }
            )

            RegistryHolder.COMPONENT_REGISTRY.register(
                getNamespacedKey("weapon"),
                ItemComponentType(
                    WeaponComponent::fromConfig
                ) { WeaponData(1f) }
            )

            RegistryHolder.COMPONENT_REGISTRY.register(
                getNamespacedKey("armor"),
                ItemComponentType(
                    ArmorComponent::fromConfig
                ) { ArmorData(1f, 1) }
            )

            RegistryHolder.COMPONENT_REGISTRY.register(
                getNamespacedKey("executor"),
                ItemComponentType(
                    ExecutorComponent::fromConfig
                ) { ExecutorData(HashMap<Triggers, ItemExecutable>()) }
            )

            RegistryHolder.COMPONENT_REGISTRY.register(
                getNamespacedKey("runnable"),
                ItemComponentType(
                    RunnableComponent::fromConfig
                ) { RunnableData(0, "", "") }
            )

            RegistryHolder.COMPONENT_REGISTRY.register(
                getNamespacedKey("enchant"),
                ItemComponentType(
                    EnchantComponent::fromConfig
                ) { EnchantData(HashMap<Enchantment, Int>()) }
            )

            RegistryHolder.COMPONENT_REGISTRY.register(
                getNamespacedKey("consumable"),
                ItemComponentType(
                    ConsumableComponent::fromConfig
                ) { ConsumableData(0, 1f, 0, mutableListOf()) }
            )
        }
    }
}
