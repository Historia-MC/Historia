package dev.boooiil.historia.core.proficiency;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.file.FileKeys;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.stats.StatModifiers;

public class ProficiencyRegistryLoader {

    /**
     * Load the proficiency registry.
     */
    public static void load() {

        FileConfiguration config = FileIO.get(FileKeys.PROFICIENCY);

        // Load all proficiencies from the configuration file
        for (String key : config.getKeys(false)) {
            if (key.equals("version"))
                return;

            ConfigurationSection section = config.getConfigurationSection(key);

            ProficiencyName name = ProficiencyName.fromString(key);

            if (name != ProficiencyName.NONE) {

                Proficiency proficiency = new Proficiency(section);
                HistoriaCore.PROFICIENCY_REGISTRY.register(HistoriaCore.getNamespacedKey(name.getKeyLowercase()),
                        proficiency);

                StatModifiers modifiers = new StatModifiers(section.getConfigurationSection("modifiers"));
                HistoriaCore.STAT_MODIFIERS_REGISTRY.register(HistoriaCore.getNamespacedKey(name.getKeyLowercase()),
                        modifiers);

            } else {
                if (!HistoriaCore.PROFICIENCY_REGISTRY.contains(HistoriaCore.getNamespacedKey("none"))) {
                    section = config.getConfigurationSection("none");
                    HistoriaCore.PROFICIENCY_REGISTRY.register(HistoriaCore.getNamespacedKey("none"),
                            new Proficiency(section));

                    StatModifiers modifiers = new StatModifiers(section.getConfigurationSection("modifiers"));
                    HistoriaCore.STAT_MODIFIERS_REGISTRY.register(HistoriaCore.getNamespacedKey("none"), modifiers);
                }
            }
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
