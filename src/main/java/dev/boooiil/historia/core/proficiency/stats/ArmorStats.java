package dev.boooiil.historia.core.proficiency.stats;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.stats.Stats.ArmorStatsType;
import dev.boooiil.historia.core.proficiency.stats.Stats.StatsType;
import dev.boooiil.historia.core.util.JSONUtils;

public class ArmorStats implements StatsComponent {

    /** The proficiency name of the player. */
    private StatModifiers statModifiers;
    /** Helmet proficiency of the player. */
    private int helmet;
    /** Helmet level experience of the player. */
    private double helmetExperience;
    /** Chestplate proficiency of the player. */
    private int chestplate;
    /** Chestplate level experience of the player. */
    private double chestplateExperience;
    /** Leggings proficiency of the player. */
    private int leggings;
    /** Leggings level experience of the player. */
    private double leggingsExperience;
    /** Boots proficiency of the player. */
    private int boots;
    /** Boots level experience of the player. */
    private double bootsExperience;

    /** The usable armor weights this player can use. */
    private List<String> usableArmorWeights;

    public ArmorStats(ConfigurationSection section, ProficiencyName proficiencyName) {
        this.statModifiers = HistoriaCore.statModifiersRegistry
                .get(HistoriaCore.getNamespacedKey(proficiencyName.getKey()));
        this.helmet = section.getInt("helmet");
        this.helmetExperience = 0;
        this.chestplate = section.getInt("chestplate");
        this.chestplateExperience = 0;
        this.leggings = section.getInt("leggings");
        this.leggingsExperience = 0;
        this.boots = section.getInt("boots");
        this.bootsExperience = 0;
        this.usableArmorWeights = section.getStringList("usableArmorWeights");

    }

    public ArmorStats(StatModifiers statModifiers, int helmetLevel,
            double helmetExperience, int chestplateLevel, double chestplateExperience,
            int leggingsLevel, double leggingsExperience, int bootsLevel, double bootsExperience,
            List<String> usableArmorWeights) {
        this.statModifiers = statModifiers;
        this.helmet = helmetLevel;
        this.helmetExperience = helmetExperience;
        this.chestplate = chestplateLevel;
        this.chestplateExperience = chestplateExperience;
        this.leggings = leggingsLevel;
        this.leggingsExperience = leggingsExperience;
        this.boots = bootsLevel;
        this.bootsExperience = bootsExperience;
        this.usableArmorWeights = usableArmorWeights;
    }

    public void advance(StatsType type) {
        switch (type) {
            case HELMET:
                helmetExperience++;
                if (helmetExperience >= helmet * 10) {
                    helmet++;
                    helmetExperience = 0;
                }
                break;

            case CHESTPLATE:
                chestplateExperience++;
                if (chestplateExperience >= chestplate * 10) {
                    chestplate++;
                    chestplateExperience = 0;
                }
                break;

            case LEGGINGS:
                leggingsExperience++;
                if (leggingsExperience >= leggings * 10) {
                    leggings++;
                    leggingsExperience = 0;
                }
                break;

            case BOOTS:
                bootsExperience++;
                if (bootsExperience >= boots * 10) {
                    boots++;
                    bootsExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void advance(ArmorStatsType type) {
        advance(type.getKey());
    }

    public void decrease(StatsType type) {
        switch (type) {
            case HELMET:
                helmetExperience--;
                if (helmet > 0) {
                    helmet--;
                    helmetExperience = 0;
                }
                break;

            case CHESTPLATE:
                chestplateExperience--;
                if (chestplate > 0) {
                    chestplate--;
                    chestplateExperience = 0;
                }
                break;

            case LEGGINGS:
                leggingsExperience--;
                if (leggings > 0) {
                    leggings--;
                    leggingsExperience = 0;
                }
                break;

            case BOOTS:
                bootsExperience--;
                if (boots > 0) {
                    boots--;
                    bootsExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void decrease(ArmorStatsType type) {
        decrease(type.getKey());
    }

    public int getLevel(StatsType type) {
        switch (type) {
            case HELMET:
                return helmet;
            case CHESTPLATE:
                return chestplate;
            case LEGGINGS:
                return leggings;
            case BOOTS:
                return boots;
            default:
                return 0;
        }
    }

    public int getLevel(ArmorStatsType type) {
        return getLevel(type.getKey());
    }

    public double getExperience(StatsType type) {
        switch (type) {
            case HELMET:
                return helmetExperience;
            case CHESTPLATE:
                return chestplateExperience;
            case LEGGINGS:
                return leggingsExperience;
            case BOOTS:
                return bootsExperience;
            default:
                return 0;
        }
    }

    public double getExperience(ArmorStatsType type) {
        return getExperience(type.getKey());
    }

    public void setLevel(StatsType type, int level) {
        switch (type) {
            case HELMET:
                helmet = level;
                break;
            case CHESTPLATE:
                chestplate = level;
                break;
            case LEGGINGS:
                leggings = level;
                break;
            case BOOTS:
                boots = level;
                break;
            default:
                break;
        }
    }

    public void setLevel(ArmorStatsType type, int level) {
        setLevel(type.getKey(), level);
    }

    public void setExperience(StatsType type, double experience) {
        switch (type) {
            case HELMET:
                helmetExperience = experience;
                break;
            case CHESTPLATE:
                chestplateExperience = experience;
                break;
            case LEGGINGS:
                leggingsExperience = experience;
                break;
            case BOOTS:
                bootsExperience = experience;
                break;
            default:
                break;
        }
    }

    public void setExperience(ArmorStatsType type, double experience) {
        setExperience(type.getKey(), experience);
    }

    public boolean isUsableArmorWeight(String armorType) {
        return usableArmorWeights.contains(armorType.toLowerCase());
    }

    /**
     * returns a list of strings that represent the armor proficiency
     * of the class
     * 
     * @return A list of strings.
     */
    public List<String> getUsableArmorWeights() {
        return usableArmorWeights;
    }

    /**
     * sets the armorProficiency of the player to the value of the
     * parameter armorProficiency
     * 
     * @param armorProficiency List of armor types the character is proficient with.
     */
    public void setUsableArmorWeights(List<String> armorProficiency) {
        this.usableArmorWeights = armorProficiency;
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("helmet", this.helmet) + ", ");
        sb.append(JSONUtils.fromValue("chestplate", this.chestplate) + ", ");
        sb.append(JSONUtils.fromValue("leggings", this.leggings) + ", ");
        sb.append(JSONUtils.fromValue("boots", this.boots) + ", ");
        sb.append(JSONUtils.fromValue("helmetExperience", this.helmetExperience) + ", ");
        sb.append(JSONUtils.fromValue("chestplateExperience", this.chestplateExperience) + ", ");
        sb.append(JSONUtils.fromValue("leggingsExperience", this.leggingsExperience) + ", ");
        sb.append(JSONUtils.fromValue("bootsExperience", this.bootsExperience) + ", ");
        sb.append(JSONUtils.fromStringList("usableArmorWeights", this.usableArmorWeights));
        sb.append("}");

        return sb.toString();
    }

}
