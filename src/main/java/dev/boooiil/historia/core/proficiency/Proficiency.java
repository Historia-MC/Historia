package dev.boooiil.historia.core.proficiency;

import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.file.FileKeys;
import dev.boooiil.historia.core.proficiency.skills.Skills;
import dev.boooiil.historia.core.proficiency.stats.Stats;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a proficiency that a character can have. It contains
 * information about the proficiency's name, stats, and skills.
 */
public class Proficiency {

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
        FileConfiguration config = FileIO.get(FileKeys.PROFICIENCY);

        if (config.contains(proficiencyName)) {
            this.name = ProficiencyName.fromString(proficiencyName);
            this.stats = new Stats(config, proficiencyName + ".stats");
            this.skills = new Skills(config, proficiencyName + ".skills");
        }

        else {
            this.name = ProficiencyName.NONE;
            this.stats = new Stats(config, "None.stats");
            this.skills = new Skills(config, "None.skills");
        }
    }

    /**
     * Constructs a new proficiency with the given name.
     * 
     * @param proficiencyName the name of the proficiency
     */
    public Proficiency(ProficiencyName proficiencyName) {
        FileConfiguration config = FileIO.get(FileKeys.PROFICIENCY);

        this.name = proficiencyName;
        this.stats = new Stats(config, proficiencyName.getKey() + ".stats");
        this.skills = new Skills(config, proficiencyName.getKey() + ".skills");

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
     * Returns a boolean value indicating whether the character can use light armor
     * or not.
     * 
     * @return true if the character can use light armor, false otherwise
     */
    public boolean canUseLightArmor() {
        return getStats().getUsableArmorTypes().contains("Light");
    }

    /**
     * Returns a boolean value indicating whether the character can use medium armor
     * or not.
     * 
     * @return true if the character can use medium armor, false otherwise
     */
    public boolean canUseMediumArmor() {
        return getStats().getUsableArmorTypes().contains("Medium");
    }

    /**
     * Returns a boolean value indicating whether the character can use heavy armor
     * or not.
     * 
     * @return true if the character can use heavy armor, false otherwise
     */
    public boolean canUseHeavyArmor() {
        return getStats().getUsableArmorTypes().contains("Heavy");
    }

    /**
     * Returns a boolean value indicating whether the character can use light
     * weapons or not.
     * 
     * @return true if the character can use light weapons, false otherwise
     */
    public boolean canUseLightWeapon() {
        return getStats().getUsableWeaponTypes().contains("Light");
    }

    /**
     * Returns a boolean value indicating whether the character can use medium
     * weapons or not.
     * 
     * @return true if the character can use medium weapons, false otherwise
     */
    public boolean canUseMediumWeapon() {
        return getStats().getUsableWeaponTypes().contains("Medium");
    }

    /**
     * This function returns a boolean value indicating whether the character can
     * use heavy weapons or not.
     * 
     * @return true if the character can use heavy weapons, false otherwise.
     */
    public boolean canUseHeavyWeapon() {
        return getStats().getUsableWeaponTypes().contains("Heavy");
    }

    /**
     * This function returns a boolean value indicating whether the character can
     * use bows or not.
     * 
     * @return true if the character can use bows, false otherwise.
     */
    public boolean canUseRanged() {

        return getStats().getUsableWeaponTypes().contains("Bow")
                || getStats().getUsableWeaponTypes().contains("Crossbow");
    }

    /**
     * This function returns a boolean value indicating whether the character can
     * use crossbows or not.
     * 
     * @return true if the character can use crossbows, false otherwise.
     */
    public boolean canUseCrossbow() {
        return getStats().getUsableWeaponTypes().contains("Crossbow");
    }

    /**
     * This function returns a boolean value indicating whether the character can
     * use a weapon with the given localizedName or not.
     * 
     * @param localizedName the name of the weapon to check proficiency for.
     * @return true if the character can use the weapon, false otherwise.
     */
    public boolean canUseWeapon(String localizedName) {

        Pattern tier = Pattern.compile("Light|Medium|Heavy|Trident|Bow|Crossbow");
        Matcher matcher = tier.matcher(localizedName);

        if (matcher.matches()) {

            return getStats().getUsableWeaponTypes().contains(localizedName);

        }

        else {

            return false;

        }

    }

    /**
     * This function returns a boolean value indicating whether the character can
     * use an armor with the given localizedName or not.
     * 
     * @param localizedName the name of the armor to check proficiency for.
     * @return true if the character can use the armor, false otherwise.
     */
    public boolean canUseArmor(String localizedName) {

        Pattern tier = Pattern.compile("Light|Medium|Heavy");
        Matcher matcher = tier.matcher(localizedName);

        if (matcher.matches()) {

            return getStats().getUsableArmorTypes().contains(localizedName);

        }

        else {

            return false;

        }

    }

    /**
     * Returns a string representation of the Proficiency object.
     * 
     * @return a string containing the name, stats, and skills of the Proficiency
     *         object.
     */
    public String toString() {

        String output = "*** PROFICIENCY ***\n";

        output += "Name: " + name + "\n";
        output += stats.toString();
        output += skills.toString();

        return output;

    }

}
