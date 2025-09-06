package dev.boooiil.historia.core.proficiency;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.skills.ISkill;
import dev.boooiil.historia.core.proficiency.skills.Skills;
import dev.boooiil.historia.core.proficiency.stats.Stats;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Set;

/**
 * This class represents a proficiency that a character can have. It contains
 * information about the proficiency's name, stats, and skills.
 */
@NullMarked
public class Proficiency implements JSONSerializable {

    /*
     * This is now a configuration class.
     * HistoriaCore -> Init -> Registry<Proficiency> -> Proficiency.register()
     *
     *
     */

    /**
     * Enum of valid proficiencies.
     */
    public enum ProficiencyName {

        NONE(HistoriaCore.getNamespacedKey("none")),
        ARCHER(HistoriaCore.getNamespacedKey("archer")),
        WARRIOR(HistoriaCore.getNamespacedKey("warrior")),
        FISHERMAN(HistoriaCore.getNamespacedKey("fisherman")),
        MINER(HistoriaCore.getNamespacedKey("miner")),
        BLACKSMITH(HistoriaCore.getNamespacedKey("blacksmith")),
        HUNTSMAN(HistoriaCore.getNamespacedKey("huntsman")),
        APOTHECARY(HistoriaCore.getNamespacedKey("apothecary")),
        ARCHITECT(HistoriaCore.getNamespacedKey("architect")),
        LUMBERJACK(HistoriaCore.getNamespacedKey("lumberjack")),
        FARMER(HistoriaCore.getNamespacedKey("farmer"));

        private final NamespacedKey key;

        ProficiencyName(NamespacedKey key) {

            this.key = key;

        }

        public NamespacedKey getKey() {

            return this.key;

        }

        public String getKeyLowercase() {

            return this.key.getKey().toLowerCase();

        }

        public static ProficiencyName fromString(String key) {

            ProficiencyName proficiency = NONE;

            for (ProficiencyName proficiencyName : ProficiencyName.values()) {

                if (proficiencyName.key.getKey().equalsIgnoreCase(key)) {

                    proficiency = proficiencyName;

                }

            }

            return proficiency;

        }

    }

    /**
     * The name of the proficiency.
     */
    private final NamespacedKey name;

    /**
     * The skills associated with the proficiency.
     */
    private final HashMap<ISkill, Integer> skills = new HashMap<>();

    /**
     * Constructs a new proficiency with the given name.
     * 
     * @param proficiencyName the name of the proficiency
     */
    @Deprecated(forRemoval = true)
    public Proficiency(String proficiencyName) {

        NamespacedKey  key = HistoriaCore.getNamespacedKey("none");

        if (HistoriaCore.PROFICIENCY_REGISTRY.contains(HistoriaCore.getNamespacedKey(proficiencyName))) {
            key = HistoriaCore.getNamespacedKey(proficiencyName);
        }

        this.name = key;
        this.skills.clear();
        this.skills.putAll(HistoriaCore.PROFICIENCY_REGISTRY.get(key).getSkills());

    }

    public Proficiency(Proficiency proficiency) {
        this.name = proficiency.name;
        this.skills.clear();
        this.skills.putAll(proficiency.skills);
    }

    /**
     * Constructs a new proficiency with the given name.
     * 
     * @param name the name of the proficiency
     */
    public Proficiency(NamespacedKey name) {
        this(HistoriaCore.PROFICIENCY_REGISTRY.get(name));
    }

    public Proficiency(ConfigurationSection section) {

        String sName = section.getString("proficiencyName");

        if (sName == null) {
            throw new IllegalArgumentException("Key 'proficiencyName' must be specified.");
        }

        this.name = HistoriaCore.getNamespacedKey(sName);

        ConfigurationSection skillSection = section.getConfigurationSection("skills");

        if (skillSection == null) {
            throw new IllegalArgumentException("Key 'skills' must be specified for proficiency " + sName + ".");
        }

        Set<String> skillKeys = skillSection.getKeys(false);

        for (String key : skillKeys) {
            //TODO: Finish
        }
    }

    /**
     * Returns the name of the proficiency.
     * 
     * @return the name of the proficiency
     */
    public NamespacedKey getName() {
        return name;
    }

    /**
     * Returns the skills associated with the proficiency.
     * 
     * @return the skills associated with the proficiency
     */
    public HashMap<ISkill, Integer> getSkills() {
        return skills;
    }

    public void registerSkills() {
        for (ISkill skill : skills.keySet()) {
            skill.register();
        }
    }

    public void deregisterSkills() {
        for (ISkill skill : skills.keySet()) {
            skill.deregister();
        }
    }

    public boolean hasSkill(ISkill skill) {
        return skills.containsKey(skill);
    }

    /**
     * Sets the skills associated with the proficiency.
     * 
     * @param skills the new skills associated with the proficiency
     */
    public void setSkills(HashMap<ISkill, Integer> skills) {
        this.skills.clear();
        this.skills.putAll(skills);
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
        sb.append(JSONUtils.fromValue("proficiencyName", name.getKey().toLowerCase()) + ", ");
        sb.append("\"skills\":" + skills.toString());
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("proficiencyName", name.getKey().toLowerCase()) + ", ");
        sb.append(JSONUtils.fromMap("skills", skills));
        sb.append("}");

        return sb.toString();
    }

}
