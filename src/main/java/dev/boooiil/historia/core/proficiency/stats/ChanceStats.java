package dev.boooiil.historia.core.proficiency.stats;

import org.bukkit.configuration.ConfigurationSection;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.stats.Stats.ChanceStatsType;
import dev.boooiil.historia.core.proficiency.stats.Stats.StatsType;
import dev.boooiil.historia.core.util.JSONUtils;

public class ChanceStats implements StatsComponent {

    /** The stat modifiers associated with these chance stats. */
    StatModifiers statModifiers;

    /** Chance to harvest a crop successfully. */
    private int harvest;
    /** Harvest level experience. */
    private double harvestExperience;
    /** Chance to harvest a crop twice. */
    private int doubleHarvest;
    /** Double harvest level experience. */
    private double doubleHarvestExperience;
    /** Chance to grow a crop instantly. */
    private int instantGrowth;
    /** Instant growth level experience. */
    private double instantGrowthExperience;
    /** Chance to behead an enemy. */
    private int behead;
    /** Behead level experience. */
    private double beheadExperience;

    public ChanceStats(ConfigurationSection section, ProficiencyName proficiencyName) {
        this.statModifiers = HistoriaCore.STAT_MODIFIERS_REGISTRY
                .get(proficiencyName.getKey());
        this.harvest = section.getInt("harvest");
        this.harvestExperience = 0;
        this.doubleHarvest = section.getInt("doubleHarvest");
        this.doubleHarvestExperience = 0;
        this.instantGrowth = section.getInt("instantGrowth");
        this.instantGrowthExperience = 0;
        this.behead = section.getInt("behead");
        this.beheadExperience = 0;
    }

    public ChanceStats(StatModifiers statModifiers, int harvestLevel,
            double harvestExperience, int doubleHarvestLevel, double doubleHarvestExperience,
            int instantGrowthLevel, double instantGrowthExperience, int beheadLevel, double beheadExperience) {
        this.statModifiers = statModifiers;
        this.harvest = harvestLevel;
        this.harvestExperience = harvestExperience;
        this.doubleHarvest = doubleHarvestLevel;
        this.doubleHarvestExperience = doubleHarvestExperience;
        this.instantGrowth = instantGrowthLevel;
        this.instantGrowthExperience = instantGrowthExperience;
        this.behead = beheadLevel;
        this.beheadExperience = beheadExperience;
    }

    public void advance(StatsType type) {
        switch (type) {
            case HARVEST:
                harvestExperience++;
                if (harvestExperience >= harvest * 10) {
                    harvest++;
                    harvestExperience = 0;
                }
                break;
            case DOUBLE_HARVEST:
                doubleHarvestExperience++;
                if (doubleHarvestExperience >= doubleHarvest * 10) {
                    doubleHarvest++;
                    doubleHarvestExperience = 0;
                }
                break;
            case INSTANT_GROWTH:
                instantGrowthExperience++;
                if (instantGrowthExperience >= instantGrowth * 10) {
                    instantGrowth++;
                    instantGrowthExperience = 0;
                }
                break;
            case BEHEAD:
                beheadExperience++;
                if (beheadExperience >= behead * 10) {
                    behead++;
                    beheadExperience = 0;
                }
            default:
                break;

        }
    }

    public void advance(ChanceStatsType type) {
        advance(type.getKey());
    }

    public void decrease(StatsType type) {
        switch (type) {
            case HARVEST:
                harvestExperience--;
                if (harvestExperience < 0) {
                    harvest--;
                    harvestExperience = 0;
                }
                break;
            case DOUBLE_HARVEST:
                doubleHarvestExperience--;
                if (doubleHarvestExperience < 0) {
                    doubleHarvest--;
                    doubleHarvestExperience = 0;
                }
                break;
            case INSTANT_GROWTH:
                instantGrowthExperience--;
                if (instantGrowthExperience < 0) {
                    instantGrowth--;
                    instantGrowthExperience = 0;
                }
                break;
            case BEHEAD:
                beheadExperience--;
                if (beheadExperience < 0) {
                    behead--;
                    beheadExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void decrease(ChanceStatsType type) {
        decrease(type.getKey());
    }

    public int getLevel(StatsType type) {
        switch (type) {
            case HARVEST:
                return harvest;
            case DOUBLE_HARVEST:
                return doubleHarvest;
            case INSTANT_GROWTH:
                return instantGrowth;
            case BEHEAD:
                return behead;
            default:
                return 0;
        }
    }

    public int getLevel(ChanceStatsType type) {
        return getLevel(type.getKey());
    }

    public double getExperience(StatsType type) {
        switch (type) {
            case HARVEST:
                return harvestExperience;
            case DOUBLE_HARVEST:
                return doubleHarvestExperience;
            case INSTANT_GROWTH:
                return instantGrowthExperience;
            case BEHEAD:
                return beheadExperience;
            default:
                return 0;
        }
    }

    public double getExperience(ChanceStatsType type) {
        return getExperience(type.getKey());
    }

    public void setLevel(StatsType type, int level) {
        switch (type) {
            case HARVEST:
                this.harvest = level;
                break;
            case DOUBLE_HARVEST:
                this.doubleHarvest = level;
                break;
            case INSTANT_GROWTH:
                this.instantGrowth = level;
                break;
            case BEHEAD:
                this.behead = level;
                break;
            default:
                break;
        }
    }

    public void setLevel(ChanceStatsType type, int level) {
        setLevel(type.getKey(), level);
    }

    public void setExperience(StatsType type, double experience) {
        switch (type) {
            case HARVEST:
                this.harvestExperience = experience;
                break;
            case DOUBLE_HARVEST:
                this.doubleHarvestExperience = experience;
                break;
            case INSTANT_GROWTH:
                this.instantGrowthExperience = experience;
                break;
            case BEHEAD:
                this.beheadExperience = experience;
                break;
            default:
                break;
        }

    }

    public void setExperience(ChanceStatsType type, double experience) {
        setExperience(type.getKey(), experience);
    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append(JSONUtils.fromValue("harvest", this.harvest) + ", ");
        sb.append(JSONUtils.fromValue("doubleHarvest", this.doubleHarvest) + ", ");
        sb.append(JSONUtils.fromValue("instantGrowth", this.instantGrowth) + ", ");
        sb.append(JSONUtils.fromValue("behead", this.behead) + ", ");

        return sb.toString();
    }

}
