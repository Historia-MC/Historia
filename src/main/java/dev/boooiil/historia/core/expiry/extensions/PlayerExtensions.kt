package dev.boooiil.historia.core.expiry.extensions

import dev.boooiil.historia.core.configuration.specific.ExpiryConfig
import dev.boooiil.historia.core.items.data.ConsumableData
import org.bukkit.entity.Player
import kotlin.math.ceil

fun Player.consume(consumable: ConsumableData) {
    val expiredModifier = if (consumable.isExpired) ExpiryConfig.EXPIRED_HUNGER_MODIFIER else 1.0f

    this.foodLevel += ceil((consumable.hunger * expiredModifier).toDouble()).toInt()
    this.saturation += consumable.hunger * consumable.saturation * expiredModifier

    consumable.effects.forEach(this::addPotionEffect)
}