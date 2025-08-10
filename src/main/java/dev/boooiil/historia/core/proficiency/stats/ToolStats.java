package dev.boooiil.historia.core.proficiency.stats;

import org.bukkit.configuration.ConfigurationSection;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.stats.Stats.StatsType;
import dev.boooiil.historia.core.proficiency.stats.Stats.ToolStatsType;
import dev.boooiil.historia.core.util.JSONUtils;

public class ToolStats implements StatsComponent {

    /** The stat modifiers associated with these tool stats. */
    private StatModifiers statModifiers;
    /** Axe proficiency of the player. */
    private int axe;
    /** Axe level experience of the player. */
    private double axeExperience;
    /** Pickaxe proficiency of the player. */
    private int pickaxe;
    /** Pickaxe level experience of the player. */
    private double pickaxeExperience;
    /** Shovel proficiency of the player. */
    private int shovel;
    /** Shovel level experience of the player. */
    private double shovelExperience;
    /** Hoe proficiency of the player. */
    private int hoe;
    /** Hoe level experience of the player. */
    private double hoeExperience;

    public ToolStats(ConfigurationSection section, ProficiencyName proficiencyName) {
        this.statModifiers = HistoriaCore.statModifiersRegistry
                .get(HistoriaCore.getNamespacedKey(proficiencyName.getKey()));
        this.axe = section.getInt("axe");
        this.axeExperience = 0;
        this.pickaxe = section.getInt("pickaxe");
        this.pickaxeExperience = 0;
        this.shovel = section.getInt("shovel");
        this.shovelExperience = 0;
        this.hoe = section.getInt("hoe");
        this.hoeExperience = 0;
    }

    public ToolStats(StatModifiers statModifiers, int axeLevel,
            double axeExperience, int pickaxeLevel, double pickaxeExperience,
            int shovelLevel, double shovelExperience, int hoeLevel, double hoeExperience) {
        this.statModifiers = statModifiers;
        this.axe = axeLevel;
        this.axeExperience = axeExperience;
        this.pickaxe = pickaxeLevel;
        this.pickaxeExperience = pickaxeExperience;
        this.shovel = shovelLevel;
        this.shovelExperience = shovelExperience;
        this.hoe = hoeLevel;
        this.hoeExperience = hoeExperience;
    }

    public void advance(StatsType type) {
        switch (type) {
            case AXE:
                axeExperience++;
                if (axeExperience >= axe * 10) {
                    axe++;
                    axeExperience = 0;
                }
                break;
            case PICKAXE:
                pickaxeExperience++;
                if (pickaxeExperience >= pickaxe * 10) {
                    pickaxe++;
                    pickaxeExperience = 0;
                }
                break;
            case SHOVEL:
                shovelExperience++;
                if (shovelExperience >= shovel * 10) {
                    shovel++;
                    shovelExperience = 0;
                }
                break;
            case HOE:
                hoeExperience++;
                if (hoeExperience >= hoe * 10) {
                    hoe++;
                    hoeExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void advance(ToolStatsType type) {
        advance(type.getKey());
    }

    public void decrease(StatsType type) {
        switch (type) {
            case AXE:
                axeExperience--;
                if (axeExperience <= 0) {
                    axe--;
                    axeExperience = 0;
                }
                break;
            case PICKAXE:
                pickaxeExperience--;
                if (pickaxeExperience <= 0) {
                    pickaxe--;
                    pickaxeExperience = 0;
                }
                break;
            case SHOVEL:
                shovelExperience--;
                if (shovelExperience <= 0) {
                    shovel--;
                    shovelExperience = 0;
                }
                break;
            case HOE:
                hoeExperience--;
                if (hoeExperience <= 0) {
                    hoe--;
                    hoeExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void decrease(ToolStatsType type) {
        decrease(type.getKey());
    }

    public int getLevel(StatsType type) {
        switch (type) {
            case AXE:
                return axe;
            case PICKAXE:
                return pickaxe;
            case SHOVEL:
                return shovel;
            case HOE:
                return hoe;
            default:
                return 0;
        }
    }

    public int getLevel(ToolStatsType type) {
        return getLevel(type.getKey());
    }

    public double getExperience(StatsType type) {
        switch (type) {
            case AXE:
                return axeExperience;
            case PICKAXE:
                return pickaxeExperience;
            case SHOVEL:
                return shovelExperience;
            case HOE:
                return hoeExperience;
            default:
                return 0;
        }
    }

    public double getExperience(ToolStatsType type) {
        return getExperience(type.getKey());
    }

    public void setLevel(StatsType type, int level) {
        switch (type) {
            case AXE:
                this.axe = level;
                break;
            case PICKAXE:
                this.pickaxe = level;
                break;
            case SHOVEL:
                this.shovel = level;
                break;
            case HOE:
                this.hoe = level;
                break;
            default:
                break;
        }
    }

    public void setLevel(ToolStatsType type, int level) {
        setLevel(type.getKey(), level);
    }

    public void setExperience(StatsType type, double experience) {
        switch (type) {
            case AXE:
                this.axeExperience = experience;
                break;
            case PICKAXE:
                this.pickaxeExperience = experience;
                break;
            case SHOVEL:
                this.shovelExperience = experience;
                break;
            case HOE:
                this.hoeExperience = experience;
                break;
            default:
                break;
        }
    }

    public void setExperience(ToolStatsType type, double experience) {
        setExperience(type.getKey(), experience);
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("axe", this.axe) + ", ");
        sb.append(JSONUtils.fromValue("pickaxe", this.pickaxe) + ", ");
        sb.append(JSONUtils.fromValue("shovel", this.shovel) + ", ");
        sb.append(JSONUtils.fromValue("hoe", this.hoe) + ", ");
        sb.append("}");

        return sb.toString();
    }

}
