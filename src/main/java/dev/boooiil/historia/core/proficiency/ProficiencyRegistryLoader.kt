package dev.boooiil.historia.core.proficiency

import dev.boooiil.historia.core.file.FileIO
import dev.boooiil.historia.core.file.FileKeys
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.CoreLogger
import org.bukkit.configuration.file.FileConfiguration

object ProficiencyRegistryLoader {
    /**
     * Load the proficiency registry.
     */
    fun load() {
        val config: FileConfiguration = FileIO.get(FileKeys.PROFICIENCY)

        config.getKeys(false)
            .filter { !it.contains("version") }
            .forEach { key ->
                CoreLogger.debugToConsole("Found proficiency key: $key")

                config.getConfigurationSection(key)?.let { section ->
                    val proficiency = Proficiency(section)
                    RegistryHolder.PROFICIENCY_REGISTRY.register(proficiency.key, proficiency)
                } ?: run {
                    CoreLogger.errorToConsole("Configuration section is null for key: $key")
                }
            }
    }

    /**
     * Reload the proficiency registry.
     */
    fun reload() {
        // This method is intentionally left empty as the registry is reloaded
        // through the ProficiencyRegistry class.
    }
}
