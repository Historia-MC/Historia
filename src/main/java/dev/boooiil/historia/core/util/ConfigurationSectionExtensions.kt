package dev.boooiil.historia.core.util

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException

fun ConfigurationSection.getDoubleRange(path: String): ClosedRange<Double> {
    val bounds = this.getDoubleList(path)
    if (bounds.size != 2)
        throw InvalidConfigurationException("Range value should have exactly 2 elements")
    return bounds[0]..bounds[1]
}

fun ConfigurationSection.getIntRange(path: String): IntRange {
    val bounds = this.getIntegerList(path)
    if (bounds.size != 2)
        throw InvalidConfigurationException("Range value should have exactly 2 elements")
    return bounds[0]..bounds[1]
}