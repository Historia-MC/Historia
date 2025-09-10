package dev.boooiil.historia.core.proficiency;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.file.FileKeys;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.stats.StatModifiers;
import dev.boooiil.historia.core.util.CoreLogger;

public class ProficiencyRegistryLoader {

    /**
     * Load the proficiency registry.
     */
    public static void load() {

        FileConfiguration config = FileIO.get(FileKeys.PROFICIENCY);

        // Load all proficiencies from the configuration file
        for (String key : config.getKeys(false)) {

            CoreLogger.debugToConsole("Found proficiency key: " + key);

            if (key.equals("version"))
                continue;

            ConfigurationSection section = config.getConfigurationSection(key);

            Proficiency proficiency = new Proficiency(section);
            HistoriaCore.PROFICIENCY_REGISTRY.register(proficiency.getName(), proficiency);

            // StatModifiers modifiers = new
            // StatModifiers(section.getConfigurationSection("modifiers"));
            // HistoriaCore.STAT_MODIFIERS_REGISTRY.register(proficiency.getName(),
            // modifiers);

        }
    }

    /**
     * Reload the proficiency registry.
     */
    public static void reload() {
        // This method is intentionally left empty as the registry is reloaded
        // through the ProficiencyRegistry class.
    }

}
