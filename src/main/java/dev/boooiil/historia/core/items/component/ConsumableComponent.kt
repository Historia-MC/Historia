package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.ItemData
import dev.boooiil.historia.core.items.data.ConsumableData
import dev.boooiil.historia.core.date.ServerCalendar
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.potionEffectsFromConfig
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

class ConsumableComponent(
    val hunger: Int,
    val saturationModifier: Float,
    val expireDays: Int,
    val effects: MutableList<PotionEffect>
) : ItemComponent {

    override val key = "consumable"

    override fun data(): ItemData {
        val expireDay = when {
            expireDays < 0 -> -1
            expireDays == 0 -> 0
            else -> ServerCalendar.daysSinceStart + expireDays
        }
        return ConsumableData(this.hunger, this.saturationModifier, expireDay, this.effects)
    }

    override fun toJSON(): String {
        return "{" +
                JSONUtils.fromValue("hunger", this.hunger) + ", " +
                JSONUtils.fromValue("saturation", this.saturationModifier) + ", " +
                JSONUtils.fromValue("expireDays", this.expireDays) + ", " +
                JSONUtils.fromPotionEffectList("effects", this.effects) +
                "}"
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): ConsumableComponent {
            val hunger = section.getInt("hunger")
            val saturationModifier = section.getDouble("saturation-modifier").toFloat()
            val expireDays = section.getInt("expire-days", -1)
            val effects = potionEffectsFromConfig(section.getConfigurationSection("effects"))

            return ConsumableComponent(hunger, saturationModifier, expireDays, effects)
        }

        fun update(stack: ItemStack): ItemStack {
            val consumable = ConsumableData.fromStack(stack) ?: return stack
            if (consumable.isExpired) {
                consumable.expireDay = 0
                return stack.withType(Material.ROTTEN_FLESH)
            }
            return stack
        }
    }
}