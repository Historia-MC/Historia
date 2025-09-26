package dev.boooiil.historia.core.expiry.util

import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun potionEffectsFromConfig(section: ConfigurationSection?): MutableList<PotionEffect> {
    val effects: MutableList<PotionEffect> = ArrayList()
    if (section == null) return effects

    for (key in section.getKeys(false)) {
        val effectSection = section.getConfigurationSection(key)

        val type = PotionEffectType.getByName(key)
        if (type == null) {
            CoreLogger.errorToConsole("$key is not a valid potion effect")
            continue
        }

        var duration = 20 * effectSection!!.getInt(".seconds")
        duration += effectSection.getInt(".ticks")

        val amplifier = effectSection.getInt(".amplifier")

        effects.add(PotionEffect(type, duration, amplifier))
    }
    return effects
}

fun PotionEffect.toJson(): String {
    return "{" +
            JSONUtils.fromValue("type", type.name) + ", " +
            JSONUtils.fromValue("duration", duration) + ", " +
            JSONUtils.fromValue("amplifier", amplifier) +
            "}, "
}
