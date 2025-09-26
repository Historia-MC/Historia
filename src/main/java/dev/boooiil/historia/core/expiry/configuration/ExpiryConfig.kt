package dev.boooiil.historia.core.expiry.configuration

import dev.boooiil.historia.core.expiry.configuration.FileMap.ResourceKeys
import dev.boooiil.historia.core.expiry.util.FileGetter.get
import org.bukkit.configuration.file.FileConfiguration

object ExpiryConfig {
    private val configuration: FileConfiguration = get(ResourceKeys.CONFIG)

    var DEBUG: Boolean = configuration.getBoolean("debug")
    var CONSUMABLE_UPDATE_TICKS: Long = configuration.getLong("consumable-update-ticks")
    var EXPIRED_HUNGER_MODIFIER: Float = configuration.getDouble("expired-hunger-modifier").toFloat()
}
