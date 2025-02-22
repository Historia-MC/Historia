package dev.boooiil.historia.core.proficiency.stats;

import dev.boooiil.historia.core.proficiency.experience.AllSources;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;

import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will hold base stats provided in the configuration for each user's
 * proficiency.
 */
@NullMarked
public class Stats implements JSONSerializable {

    /** Base health of the player. */
    private float baseHealth;
    /** Max health of the player. */
    private float maxHealth;
    /** Base food of the player. (How much hunger they can satiate) */
    private int baseFood;
    /** Base speed of the player. */
    private double baseSpeed;
    /** Base experience of the player. */
    private double baseExperienceGain;

    /** Base evasion rate of the player. (How well they can dodge attacks) */
    private double baseEvasion;

    /** Chance to harvest a crop successfully. */
    private double harvestChance;
    /** Chance to harvest a crop twice. */
    private double doubleHarvestChance;
    /** Chance to grow a crop instantly. */
    private double instantGrowthChance;
    /** Chance to behead an enemy. */
    private double beheadChance;

    /** ?? */
    private double baseSwordProficiency;
    /** ?? */
    private double baseBowProficiency;
    /** ?? */
    private double baseCrossbowProficiency;

    /** Experience sources this player can gain from. */
    private List<AllSources> experienceSources;

    /** The usable weapon weights this player can use. */
    private List<String> usableWeaponTypes;
    /** The usable armor weights this player can use. */
    private List<String> usableArmorTypes;

    public Stats(FileConfiguration config, String root) {

        this.baseHealth = config.getInt(root + ".baseHealth");
        this.maxHealth = config.getInt(root + ".maxHealth");
        this.baseFood = config.getInt(root + ".baseFood");

        this.baseSpeed = config.getDouble(root + ".baseSpeed");
        this.baseEvasion = config.getDouble(root + ".baseEvasion");
        this.baseSwordProficiency = config.getDouble(root + ".baseSwordProficiency");
        this.baseBowProficiency = config.getDouble(root + ".baseBowProficiency");
        this.baseCrossbowProficiency = config.getDouble(root + ".baseCrossbowProficiency");
        this.baseExperienceGain = config.getDouble(root + ".baseExperienceGain");

        this.harvestChance = config.getDouble(root + ".harvestChance");
        this.doubleHarvestChance = config.getDouble(root + ".doubleHarvestChance");
        this.instantGrowthChance = config.getDouble(root + ".instantGrowChance");
        this.beheadChance = config.getDouble(root + ".beheadChance");

        this.usableWeaponTypes = config.getStringList(root + ".weaponProficiency");
        this.usableArmorTypes = config.getStringList(root + ".armorProficiency");
        this.experienceSources = new ArrayList<>();

        for (String key : config.getStringList(root + ".experienceSources")) {

            CoreLogger.debugToConsole("Experience Source: " + key);
            this.experienceSources.add(AllSources.valueOf(key));

        }

    }

    /**
     * returns the base health of the player
     * 
     * @return The baseHealth of the player is being returned.
     */
    public float getBaseHealth() {
        return baseHealth;
    }

    /**
     * sets the base health of the player
     * 
     * @param baseHealth The base health of the player.
     */
    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    /**
     * returns the maxHealth of the player
     * 
     * @return The maxHealth of the player is being returned.
     */
    public float getMaxHealth() {
        return maxHealth;
    }

    /**
     * sets the maxHealth of the player to the value of the maxHealth
     * parameter
     * 
     * @param maxHealth The maximum health of the player.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * returns the baseFood of the player
     * 
     * @return The baseFood of the player is being returned.
     */
    public int getBaseFood() {
        return baseFood;
    }

    /**
     * sets the base food of the player
     * 
     * @param baseFood The amount of food the player starts with.
     */
    public void setBaseFood(int baseFood) {
        this.baseFood = baseFood;
    }

    /**
     * returns the base speed of the player
     * 
     * @return The baseSpeed of the player is being returned.
     */
    public double getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * sets the base speed of the player
     * 
     * @param baseSpeed The speed of the enemy.
     */
    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    /**
     * returns the baseEvasion of the player
     * 
     * @return The baseEvasion of the player is being returned.
     */
    public double getBaseEvasion() {
        return baseEvasion;
    }

    /**
     * sets the baseEvasion of the player to the value of the parameter
     * baseEvasion
     * 
     * @param baseEvasion The base evasion of the character.
     */
    public void setBaseEvasion(float baseEvasion) {
        this.baseEvasion = baseEvasion;
    }

    /**
     * returns the harvest chance of the crop
     * 
     * @return The harvestChance of the player is being returned.
     */
    public double getHarvestChance() {
        return harvestChance;
    }

    /**
     * sets the harvest chance of the crop to the value of the
     * parameter harvestChance
     * 
     * @param harvestChance The chance that the block will drop an item when broken.
     */
    public void setHarvestChance(float harvestChance) {
        this.harvestChance = harvestChance;
    }

    /**
     * It returns the doubleHarvestChance of the player
     * 
     * @return The doubleHarvestChance of the player is being returned.
     */
    public double getDoubleHarvestChance() {
        return doubleHarvestChance;
    }

    /**
     * sets the double harvest chance to the value of the parameter
     * 
     * @param doubleHarvestChance The chance that a crop will drop two items instead
     *                            of one.
     */
    public void setDoubleHarvestChance(float doubleHarvestChance) {
        this.doubleHarvestChance = doubleHarvestChance;
    }

    /**
     * returns the instantGrowthChance of the player
     * 
     * @return The instantGrowthChance of the player is being returned.
     */
    public double getInstantGrowthChance() {
        return instantGrowthChance;
    }

    /**
     * sets the instantGrowthChance of the player to the value of the
     * instantGrowthChance
     * parameter
     * 
     * @param instantGrowthChance The chance that a plant will grow instantly.
     */
    public void setInstantGrowthChance(float instantGrowthChance) {
        this.instantGrowthChance = instantGrowthChance;
    }

    /**
     * returns the beheadChance of the player
     * 
     * @return The beheadChance of the player is being returned.
     */
    public double getBeheadChance() {
        return beheadChance;
    }

    /**
     * sets the beheadChance of the player to the value of the beheadChance
     * parameter
     * 
     * @param beheadChance The chance that the player will behead the player.
     */
    public void setBeheadChance(float beheadChance) {
        this.beheadChance = beheadChance;
    }

    /**
     * returns the baseSwordProficiency of the player
     * 
     * @return The baseSwordProficiency of the player is being returned.
     */
    public double getBaseSwordProficiency() {
        return baseSwordProficiency;
    }

    /**
     * sets the baseSwordProficiency of the player to the value of the
     * parameter passed in
     * 
     * @param baseSwordProficiency The base proficiency of the sword.
     */
    public void setBaseSwordProficiency(float baseSwordProficiency) {
        this.baseSwordProficiency = baseSwordProficiency;
    }

    /**
     * returns the baseBowProficiency of the player
     * 
     * @return The baseBowProficiency of the player is being returned.
     */
    public double getBaseBowProficiency() {
        return baseBowProficiency;
    }

    /**
     * sets the baseBowProficiency of the player to the value of the
     * parameter passed in
     * 
     * @param baseBowProficiency The base proficiency of the bow.
     */
    public void setBaseBowProficiency(float baseBowProficiency) {
        this.baseBowProficiency = baseBowProficiency;
    }

    /**
     * returns the baseCrossbowProficiency of the player
     * 
     * @return The baseCrossbowProficiency of the player is being returned.
     */
    public double getBaseCrossbowProficiency() {
        return baseCrossbowProficiency;
    }

    /**
     * sets the baseCrossbowProficiency of the player to the value of the
     * parameter passed in
     * 
     * @param baseCrossbowProficiency The base proficiency of the crossbow.
     */
    public void setBaseCrossbowProficiency(float baseCrossbowProficiency) {
        this.baseCrossbowProficiency = baseCrossbowProficiency;
    }

    /**
     * returns the base experience gain
     * 
     * @return The baseExperienceGain of the player is being returned.
     */
    public double getBaseExperienceGain() {
        return baseExperienceGain;
    }

    /**
     * sets the base experience gain of the player
     * 
     * @param baseExperienceGain The base experience gain for the player.
     */
    public void setBaseExperienceGain(float baseExperienceGain) {
        this.baseExperienceGain = baseExperienceGain;
    }

    /**
     * returns a list of strings that represent the weapon proficiency
     * of the character
     * 
     * @return The weaponProficiency list.
     */
    public List<String> getUsableWeaponTypes() {
        return usableWeaponTypes;
    }

    /**
     * sets the weapon proficiency of the character
     * 
     * @param weaponProficiency List of Strings
     */
    public void setUsableWeaponTypes(List<String> weaponProficiency) {
        this.usableWeaponTypes = weaponProficiency;
    }

    /**
     * returns a list of strings that represent the armor proficiency
     * of the class
     * 
     * @return A list of strings.
     */
    public List<String> getUsableArmorTypes() {
        return usableArmorTypes;
    }

    /**
     * sets the armorProficiency of the player to the value of the
     * parameter armorProficiency
     * 
     * @param armorProficiency List of armor types the character is proficient with.
     */
    public void setUsableArmorTypes(List<String> armorProficiency) {
        this.usableArmorTypes = armorProficiency;
    }

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
        sb.append(JSONUtils.fromValue("baseHealth", this.baseHealth) + ", ");
        sb.append(JSONUtils.fromValue("baseSpeed", this.baseSpeed) + ", ");
        sb.append(JSONUtils.fromValue("baseEvasion", this.baseEvasion) + ", ");
        sb.append(JSONUtils.fromValue("harvestChance", this.harvestChance) + ", ");
        sb.append(JSONUtils.fromValue("doubleHarvestChance", this.doubleHarvestChance) + ", ");
        sb.append(JSONUtils.fromValue("instantGrowthChance", this.instantGrowthChance) + ", ");
        sb.append(JSONUtils.fromValue("beheadChance", this.beheadChance) + ", ");
        sb.append(JSONUtils.fromValue("baseSwordProficiency", this.baseSwordProficiency) + ", ");
        sb.append(JSONUtils.fromValue("baseBowProficiency", this.baseBowProficiency) + ", ");
        sb.append(JSONUtils.fromValue("baseCrossbowProficiency", this.baseCrossbowProficiency) + ", ");
        sb.append(JSONUtils.fromValue("baseExperienceGain", this.baseExperienceGain) + ", ");
        sb.append(JSONUtils.fromStringList("usableWeaponTypes", this.usableWeaponTypes) + ", ");
        sb.append(JSONUtils.fromStringList("usableArmorTypes", this.usableArmorTypes));
        sb.append("}");

        return sb.toString();
    }

}
