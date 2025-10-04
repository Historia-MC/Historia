package dev.boooiil.historia.core.configuration.specific

import dev.boooiil.historia.core.file.FileIO
import dev.boooiil.historia.core.file.FileKeys
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import kotlin.math.abs

object DateConfig {
    private val configuration: YamlConfiguration = FileIO.get(FileKeys.DATE)

    /** Custom day length in ticks. */
    val CUSTOM_DAY_LENGTH: Int = configuration.getInt("day-length", 24000)
    /** Weight of day in day-night ratio. Determines relative day length. */
    val DAY_WEIGHT: Double
    /** Weight of night in day-night ratio. Determines relative night length. */
    val NIGHT_WEIGHT: Double

    init {
        val raw = configuration.get("day-night-ratio", "1:1")!!
        val dayNightRatio: String = raw.toString()
        val split = dayNightRatio.split(":").map { it.trim() }

        val invalidRatioException = InvalidConfigurationException(
            "Invalid value for 'day-night-ratio': '$dayNightRatio'. Expected format is '<day>:<night>', e.g. '3:1'."
        )

        if (split.size != 2) throw invalidRatioException

        DAY_WEIGHT = split[0].toDoubleOrNull()?.let { abs(it) } ?: throw invalidRatioException
        NIGHT_WEIGHT = split[1].toDoubleOrNull()?.let { abs(it) } ?: throw invalidRatioException
    }
}