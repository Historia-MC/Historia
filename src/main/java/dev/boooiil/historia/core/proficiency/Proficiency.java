package dev.boooiil.historia.core.proficiency;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.skills.Skills;
import dev.boooiil.historia.core.proficiency.stats.Stats;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

/**
 * This class represents a proficiency that a character can have. It contains
 * information about the proficiency's name, stats, and skills.
 */
@NullMarked
public class Proficiency implements JSONSerializable {

    /**
     * Enum of valid proficienies.
     */
    public enum ProficiencyName {

        NONE("None"),
        ARCHER("Archer"),
        WARRIOR("Warrior"),
        FISHERMAN("Fisherman"),
        MINER("Miner"),
        BLACKSMITH("Blacksmith"),
        HUNTSMAN("Huntsman"),
        APOTHECARY("Apothecary"),
        ARCHITECT("Architect"),
        LUMBERJACK("Lumberjack"),
        FARMER("Farmer");

        private final String key;

        ProficiencyName(String key) {

            this.key = key;

        }

        public String getKey() {

            return this.key;

        }

        public String getKeyLowercase() {

            return this.key.toLowerCase();

        }

        public static ProficiencyName fromString(String key) {

            for (ProficiencyName proficiencyName : ProficiencyName.values()) {

                if (proficiencyName.getKey().equalsIgnoreCase(key)) {

                    return proficiencyName;

                }

            }

            return NONE;

        }

    }

    /**
     * The name of the proficiency.
     */
    private ProficiencyName name;

    /**
     * The stats associated with the proficiency.
     */
    private Stats stats;

    /**
     * The skills associated with the proficiency.
     */
    private Skills skills;

    /**
     * Constructs a new proficiency with the given name.
     * 
     * @param proficiencyName the name of the proficiency
     */
    @Deprecated(forRemoval = true)
    public Proficiency(String proficiencyName) {

        NamespacedKey key = HistoriaCore.getNamespacedKey(proficiencyName);

        if (HistoriaCore.proficiencyRegistry.contains(key)) {
            this.name = ProficiencyName.fromString(proficiencyName);
            this.stats = HistoriaCore.proficiencyRegistry.get(key).getStats();
            this.skills = HistoriaCore.proficiencyRegistry.get(key).getSkills();
            return;
        } else {
            key = HistoriaCore.getNamespacedKey("none");
            this.name = ProficiencyName.NONE;
            this.stats = HistoriaCore.proficiencyRegistry.get(key).getStats();
            this.skills = HistoriaCore.proficiencyRegistry.get(key).getSkills();
        }

    }

    public Proficiency(Proficiency proficiency) {
        this.name = proficiency.name;
        this.stats = proficiency.stats;
        this.skills = proficiency.skills;
    }

    /**
     * Constructs a new proficiency with the given name.
     * 
     * @param proficiencyName the name of the proficiency
     */
    public Proficiency(ProficiencyName proficiencyName) {
        this(HistoriaCore.proficiencyRegistry.get(HistoriaCore.getNamespacedKey(proficiencyName.getKey())));
    }

    public Proficiency(ConfigurationSection section) {
        this.name = ProficiencyName.fromString(section.getString("proficiencyName"));
        this.stats = new Stats(section.getConfigurationSection("stats"), this.name);
        this.skills = new Skills(section.getConfigurationSection("skills"));
    }

    /**
     * Returns the name of the proficiency.
     * 
     * @return the name of the proficiency
     */
    public ProficiencyName getName() {
        return name;
    }

    /**
     * Sets the name of the proficiency.
     * 
     * @param proficiencyName the new name of the proficiency
     */
    public void setName(ProficiencyName proficiencyName) {
        this.name = proficiencyName;
    }

    /**
     * Returns the stats associated with the proficiency.
     * 
     * @return the stats associated with the proficiency
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Sets the stats associated with the proficiency.
     * 
     * @param stats the new stats associated with the proficiency
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * Returns the skills associated with the proficiency.
     * 
     * @return the skills associated with the proficiency
     */
    public Skills getSkills() {
        return skills;
    }

    /**
     * Sets the skills associated with the proficiency.
     * 
     * @param skills the new skills associated with the proficiency
     */
    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    /**
     * Returns a string representation of the Proficiency object.
     * 
     * @return a string containing the name, stats, and skills of the Proficiency
     *         object.
     */
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Proficiency");
        sb.append("{");
        sb.append(JSONUtils.fromValue("proficiencyName", name.name().toLowerCase()) + ", ");
        sb.append("\"stats\":" + stats.toString() + ", ");
        sb.append("\"skills\":" + skills.toString());
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("proficiencyName", name.name().toLowerCase()) + ", ");
        sb.append("\"stats\":" + stats.toJSON() + ", ");
        sb.append("\"skills\":" + skills.toJSON());
        sb.append("}");

        return sb.toString();
    }

}
