package dev.boooiil.historia.core.proficiency.stats;

import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.experience.AllSources;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will hold base stats provided in the configuration for each user's
 * proficiency.
 */
@NullMarked
public class Stats implements JSONSerializable {

    private ArmorStats armorStats;
    private BodyStats bodyStats;
    private ChanceStats chanceStats;
    private ToolStats toolStats;
    private WeaponStats weaponStats;

    public Stats() {
    }

    public Stats(ConfigurationSection section, ProficiencyName proficiencyName) {

        this.armorStats = new ArmorStats(section.getConfigurationSection("armor"), proficiencyName);
        this.bodyStats = new BodyStats(section.getConfigurationSection("body"), proficiencyName);
        this.chanceStats = new ChanceStats(section.getConfigurationSection("chance"), proficiencyName);
        this.toolStats = new ToolStats(section.getConfigurationSection("tool"), proficiencyName);
        this.weaponStats = new WeaponStats(section.getConfigurationSection("weapon"), proficiencyName);

        this.experienceSources = new ArrayList<>();

        for (String key : section.getStringList("experienceSources")) {

            CoreLogger.debugToConsole("Experience Source: " + key);
            this.experienceSources.add(AllSources.valueOf(key));

        }

    }

    public enum StatsType {
        BOW,
        CROSSBOW,
        TRIDENT,
        SWORD,
        AXE,
        PICKAXE,
        SHOVEL,
        HOE,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        HEALTH,
        SPEED,
        EVASION,
        TOUGHNESS,
        HARVEST,
        INSTANT_GROWTH,
        DOUBLE_HARVEST,
        BEHEAD
    };

    public enum ArmorStatsType {
        HELMET(StatsType.HELMET),
        CHESTPLATE(StatsType.CHESTPLATE),
        LEGGINGS(StatsType.LEGGINGS),
        BOOTS(StatsType.BOOTS);

        private final StatsType key;

        ArmorStatsType(StatsType key) {
            this.key = key;
        }

        public StatsType getKey() {
            return key;
        }
    }

    public enum BodyStatsType {
        HEALTH(StatsType.HEALTH),
        SPEED(StatsType.SPEED),
        EVASION(StatsType.EVASION),
        TOUGHNESS(StatsType.TOUGHNESS);

        private final StatsType key;

        BodyStatsType(StatsType key) {
            this.key = key;
        }

        public StatsType getKey() {
            return key;
        }
    }

    public enum ChanceStatsType {
        HARVEST(StatsType.HARVEST),
        INSTANT_GROWTH(StatsType.INSTANT_GROWTH),
        DOUBLE_HARVEST(StatsType.DOUBLE_HARVEST),
        BEHEAD(StatsType.BEHEAD);

        private final StatsType key;

        ChanceStatsType(StatsType key) {
            this.key = key;
        }

        public StatsType getKey() {
            return key;
        }
    }

    public enum ToolStatsType {
        AXE(StatsType.AXE),
        PICKAXE(StatsType.PICKAXE),
        SHOVEL(StatsType.SHOVEL),
        HOE(StatsType.HOE);

        private final StatsType key;

        ToolStatsType(StatsType key) {
            this.key = key;
        }

        public StatsType getKey() {
            return key;
        }
    }

    public enum WeaponStatsType {
        BOW(StatsType.BOW),
        CROSSBOW(StatsType.CROSSBOW),
        TRIDENT(StatsType.TRIDENT),
        SWORD(StatsType.SWORD),
        AXE(StatsType.AXE);

        private final StatsType key;

        WeaponStatsType(StatsType key) {
            this.key = key;
        }

        public StatsType getKey() {
            return key;
        }
    }

    public boolean isArmor(StatsType type) {
        return type == StatsType.HELMET || type == StatsType.CHESTPLATE || type == StatsType.LEGGINGS
                || type == StatsType.BOOTS;
    }

    public boolean isBody(StatsType type) {
        return type == StatsType.HEALTH || type == StatsType.SPEED || type == StatsType.EVASION
                || type == StatsType.TOUGHNESS;
    }

    public boolean isChance(StatsType type) {
        return type == StatsType.HARVEST || type == StatsType.INSTANT_GROWTH || type == StatsType.DOUBLE_HARVEST
                || type == StatsType.BEHEAD;
    }

    public boolean isWeapon(StatsType type) {
        return type == StatsType.BOW || type == StatsType.CROSSBOW || type == StatsType.TRIDENT
                || type == StatsType.SWORD || type == StatsType.AXE;
    }

    public boolean isTool(StatsType type) {
        return type == StatsType.AXE || type == StatsType.PICKAXE || type == StatsType.SHOVEL || type == StatsType.HOE;
    }

    public void increaseStats(StatsType type, float amount) {

        // most to least likely
        if (isBody(type)) {
            // TODO: send to BodyStats
        } else if (isTool(type)) {
            // TODO: send to ToolStats
        } else if (isWeapon(type)) {
            // TODO: send to WeaponStats
        } else if (isArmor(type)) {
            // TODO: send to ArmorStats
        } else if (isChance(type)) {
            // TODO: send to ChanceStats
        }
    }

    public void increaseStats(ArmorStatsType type, float amount) {
        // TODO: send to ArmorStats
    }

    public void increaseStats(BodyStatsType type, float amount) {
        // TODO: send to BodyStats
    }

    public void increaseStats(ChanceStatsType type, float amount) {
        // TODO: send to ChanceStats
    }

    public void increaseStats(ToolStatsType type, float amount) {
        // TODO: send to ToolStats
    }

    public void increaseStats(WeaponStatsType type, float amount) {
        // TODO: send to ArmorStats
    }

    public ArmorStats getArmorStats() {
        return armorStats;
    }

    public BodyStats getBodyStats() {
        return bodyStats;
    }

    public ChanceStats getChanceStats() {
        return chanceStats;
    }

    public ToolStats getToolStats() {
        return toolStats;
    }

    public WeaponStats getWeaponStats() {
        return weaponStats;
    }

    public void setArmorStats(ArmorStats armorStats) {
        this.armorStats = armorStats;
    }

    public void setBodyStats(BodyStats bodyStats) {
        this.bodyStats = bodyStats;
    }

    public void setChanceStats(ChanceStats chanceStats) {
        this.chanceStats = chanceStats;
    }

    public void setToolStats(ToolStats toolStats) {
        this.toolStats = toolStats;
    }

    public void setWeaponStats(WeaponStats weaponStats) {
        this.weaponStats = weaponStats;
    }

    /** Experience sources this player can gain from. */
    private List<AllSources> experienceSources;

    /**
     * returns the experienceSources of the player
     * 
     * @return The experienceSources of the player is being returned.
     */
    public List<AllSources> getExperienceSources() {
        return experienceSources;
    }

    /**
     * sets the experienceSources of the player to the value of the
     * parameter
     * experienceSources
     * 
     * @param experienceSources The income sources of the character.
     */
    public void setExperienceSources(List<AllSources> experienceSources) {
        this.experienceSources = experienceSources;
    }

    /**
     * returns a boolean value indicating whether the character has
     * the given income source or not.
     * 
     * @param source The income source to check for.
     * @return true if the character has the income source, false otherwise.
     */
    public boolean hasIncomeSource(AllSources source) {

        return this.experienceSources.contains(source);

    }

    public double getIncomeValue(AllSources source) {

        return source.getKey();

    }

    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Stats");
        sb.append(toJSON());

        return sb.toString();

    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("armorStats", this.armorStats) + ", ");
        sb.append(JSONUtils.fromValue("bodyStats", this.bodyStats) + ", ");
        sb.append(JSONUtils.fromValue("chanceStats", this.chanceStats) + ", ");
        sb.append(JSONUtils.fromValue("toolStats", this.toolStats) + ", ");
        sb.append(JSONUtils.fromValue("weaponStats", this.weaponStats));
        sb.append("}");

        return sb.toString();
    }

}
