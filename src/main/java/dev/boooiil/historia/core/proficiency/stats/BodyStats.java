package dev.boooiil.historia.core.proficiency.stats;

import org.bukkit.configuration.ConfigurationSection;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.stats.Stats.BodyStatsType;
import dev.boooiil.historia.core.proficiency.stats.Stats.StatsType;
import dev.boooiil.historia.core.util.JSONUtils;

public class BodyStats implements StatsComponent {

    /** The stat modifiers for the player. */
    private StatModifiers statModifiers;

    /** Health of the player. */
    private int health;
    /** Health level experience of the player. */
    private double healthExperience;
    /** Food consumption of the player. (How much hunger they can satiate) */
    private int food;
    /** Food level experience of the player. */
    private double foodExperience;
    /** Speed of the player. */
    private int speed;
    /** Speed level experience of the player. */
    private double speedExperience;
    /** Evasion rate of the player. (How well they can dodge attacks) */
    private int evasion;
    /** Evasion level experience of the player. */
    private double evasionExperience;
    /** Amount of damage to be absorbed by the user. */
    private int toughness;
    /** Toughness level experience of the player. */
    private double toughnessExperience;

    public BodyStats(ConfigurationSection section, ProficiencyName proficiencyName) {
        this.statModifiers = HistoriaCore.STAT_MODIFIERS_REGISTRY
                .get(proficiencyName.getKey());
        this.health = section.getInt("health");
        this.healthExperience = 0;
        this.food = section.getInt("food");
        this.foodExperience = 0;
        this.speed = section.getInt("speed");
        this.speedExperience = 0;
        this.evasion = section.getInt("evasion");
        this.evasionExperience = 0;
        this.toughness = section.getInt("toughness");
        this.toughnessExperience = 0;

    }

    public BodyStats(StatModifiers statModifiers, int healthLevel,
            double healthExperience, int foodLevel, double foodExperience, int speedLevel,
            double speedExperience, int evasionLevel, double evasionExperience, int toughnessLevel,
            double toughnessExperience) {

        this.statModifiers = statModifiers;
        this.health = healthLevel;
        this.healthExperience = healthExperience;
        this.food = foodLevel;
        this.foodExperience = foodExperience;
        this.speed = speedLevel;
        this.speedExperience = speedExperience;
        this.evasion = evasionLevel;
        this.evasionExperience = evasionExperience;
        this.toughness = toughnessLevel;
        this.toughnessExperience = toughnessExperience;
    }

    public void advance(StatsType type) {
        switch (type) {
            case HEALTH:
                healthExperience++;
                if (healthExperience >= health * 10) {
                    health++;
                    healthExperience = 0;
                }
                break;
            case SPEED:
                speedExperience++;
                if (speedExperience >= speed * 10) {
                    speed++;
                    speedExperience = 0;
                }
                break;
            case EVASION:
                evasionExperience++;
                if (evasionExperience >= evasion * 10) {
                    evasion++;
                    evasionExperience = 0;
                }
                break;
            case TOUGHNESS:
                toughnessExperience++;
                if (toughnessExperience >= toughness * 10) {
                    toughness++;
                    toughnessExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void advance(BodyStatsType type) {
        advance(type.getKey());
    }

    public void decrease(StatsType type) {
        switch (type) {
            case HEALTH:
                healthExperience--;
                if (healthExperience < 0) {
                    health--;
                    healthExperience = health * 10 - 1;
                }
                break;
            case SPEED:
                speedExperience--;
                if (speedExperience < 0) {
                    speed--;
                    speedExperience = speed * 10 - 1;
                }
                break;
            case EVASION:
                evasionExperience--;
                if (evasionExperience < 0) {
                    evasion--;
                    evasionExperience = evasion * 10 - 1;
                }
                break;
            case TOUGHNESS:
                toughnessExperience--;
                if (toughnessExperience < 0) {
                    toughness--;
                    toughnessExperience = toughness * 10 - 1;
                }
                break;
            default:
                break;
        }
    }

    public void decrease(BodyStatsType type) {
        decrease(type.getKey());
    }

    public int getLevel(StatsType type) {
        switch (type) {
            case HEALTH:
                return health;
            case SPEED:
                return speed;
            case EVASION:
                return evasion;
            case TOUGHNESS:
                return toughness;
            default:
                return 0;
        }
    }

    public int getLevel(BodyStatsType type) {
        return getLevel(type.getKey());
    }

    public double getExperience(StatsType type) {
        switch (type) {
            case HEALTH:
                return healthExperience;
            case SPEED:
                return speedExperience;
            case EVASION:
                return evasionExperience;
            case TOUGHNESS:
                return toughnessExperience;
            default:
                return 0;
        }
    }

    public double getExperience(BodyStatsType type) {
        return getExperience(type.getKey());
    }

    public void setLevel(StatsType type, int level) {
        switch (type) {
            case HEALTH:
                this.health = level;
                break;
            case SPEED:
                this.speed = level;
                break;
            case EVASION:
                this.evasion = level;
                break;
            case TOUGHNESS:
                this.toughness = level;
                break;
            default:
                break;
        }
    }

    public void setLevel(BodyStatsType type, int level) {
        setLevel(type.getKey(), level);
    }

    public void setExperience(StatsType type, double experience) {
        switch (type) {
            case HEALTH:
                this.healthExperience = experience;
                break;
            case SPEED:
                this.speedExperience = experience;
                break;
            case EVASION:
                this.evasionExperience = experience;
                break;
            case TOUGHNESS:
                this.toughnessExperience = experience;
                break;
            default:
                break;
        }
    }

    public void setExperience(BodyStatsType type, double experience) {
        setExperience(type.getKey(), experience);
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("health", this.health) + ", ");
        sb.append(JSONUtils.fromValue("speed", this.speed) + ", ");
        sb.append(JSONUtils.fromValue("evasion", this.evasion) + ", ");
        sb.append(JSONUtils.fromValue("toughness", this.toughness) + ", ");
        sb.append("}");

        return sb.toString();
    }

}
