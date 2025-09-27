package dev.boooiil.historia.core.configuration.specific

import dev.boooiil.historia.core.file.FileIO
import dev.boooiil.historia.core.file.FileKeys
import org.bukkit.configuration.file.YamlConfiguration


object ExpiryConfig {
    private val configuration: YamlConfiguration = FileIO.get(FileKeys.EXPIRY)

    var DEBUG: Boolean = configuration.getBoolean("debug")
    var CONSUMABLE_UPDATE_TICKS: Long = configuration.getLong("consumable-update-ticks", 20)
    var EXPIRED_HUNGER_MODIFIER: Float = configuration.getDouble("expired-hunger-modifier").toFloat()
}