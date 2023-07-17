package dev.boooiil.historia.classes.historia.proficiency;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class Stats {

    private float baseHealth;
    private float maxHealth;
    private int baseFood;
    private double baseSpeed;
    private double baseExperienceGain;

    private double baseEvasion;

    private double harvestChance;
    private double doubleHarvestChance;
    private double instantGrowthChance;
    private double beheadChance;

    private double baseSwordProficiency;
    private double baseBowProficiency;
    private double baseCrossbowProficiency;

    private List<String> usableWeaponTypes;
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

    }

    /**
     * This function returns the base health of the player
     * 
     * @return The baseHealth variable is being returned.
     */
    public float getBaseHealth() {
        return baseHealth;
    }

    /**
     * This function sets the base health of the player
     * 
     * @param baseHealth The base health of the player.
     */
    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    /**
     * This function returns the maxHealth variable
     * 
     * @return The maxHealth variable is being returned.
     */
    public float getMaxHealth() {
        return maxHealth;
    }

    /**
     * This function sets the maxHealth variable to the value of the maxHealth
     * parameter
     * 
     * @param maxHealth The maximum health of the player.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * This function returns the baseFood variable
     * 
     * @return The baseFood variable is being returned.
     */
    public int getBaseFood() {
        return baseFood;
    }

    /**
     * This function sets the base food of the player
     * 
     * @param baseFood The amount of food the player starts with.
     */
    public void setBaseFood(int baseFood) {
        this.baseFood = baseFood;
    }

    /**
     * This function returns the base speed of the player
     * 
     * @return The baseSpeed variable is being returned.
     */
    public double getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * This function sets the base speed of the player
     * 
     * @param baseSpeed The speed of the enemy.
     */
    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    /**
     * This function returns the baseEvasion variable
     * 
     * @return The baseEvasion variable is being returned.
     */
    public double getBaseEvasion() {
        return baseEvasion;
    }

    /**
     * This function sets the baseEvasion variable to the value of the parameter
     * baseEvasion
     * 
     * @param baseEvasion The base evasion of the character.
     */
    public void setBaseEvasion(float baseEvasion) {
        this.baseEvasion = baseEvasion;
    }

    /**
     * This function returns the harvest chance of the crop
     * 
     * @return The harvestChance variable is being returned.
     */
    public double getHarvestChance() {
        return harvestChance;
    }

    /**
     * This function sets the harvest chance of the crop to the value of the
     * parameter harvestChance
     * 
     * @param harvestChance The chance that the block will drop an item when broken.
     */
    public void setHarvestChance(float harvestChance) {
        this.harvestChance = harvestChance;
    }

    /**
     * It returns the doubleHarvestChance variable
     * 
     * @return The doubleHarvestChance variable is being returned.
     */
    public double getDoubleHarvestChance() {
        return doubleHarvestChance;
    }

    /**
     * This function sets the double harvest chance to the value of the parameter
     * 
     * @param doubleHarvestChance The chance that a crop will drop two items instead
     *                            of one.
     */
    public void setDoubleHarvestChance(float doubleHarvestChance) {
        this.doubleHarvestChance = doubleHarvestChance;
    }

    /**
     * This function returns the instantGrowthChance variable
     * 
     * @return The instantGrowthChance variable is being returned.
     */
    public double getInstantGrowthChance() {
        return instantGrowthChance;
    }

    /**
     * This function sets the instantGrowthChance variable to the value of the
     * instantGrowthChance
     * parameter
     * 
     * @param instantGrowthChance The chance that a plant will grow instantly.
     */
    public void setInstantGrowthChance(float instantGrowthChance) {
        this.instantGrowthChance = instantGrowthChance;
    }

    /**
     * This function returns the beheadChance variable
     * 
     * @return The beheadChance variable is being returned.
     */
    public double getBeheadChance() {
        return beheadChance;
    }

    /**
     * This function sets the beheadChance variable to the value of the beheadChance
     * parameter
     * 
     * @param beheadChance The chance that the player will behead the player.
     */
    public void setBeheadChance(float beheadChance) {
        this.beheadChance = beheadChance;
    }

    /**
     * This function returns the baseSwordProficiency variable
     * 
     * @return The baseSwordProficiency variable is being returned.
     */
    public double getBaseSwordProficiency() {
        return baseSwordProficiency;
    }

    /**
     * This function sets the baseSwordProficiency variable to the value of the
     * parameter passed in
     * 
     * @param baseSwordProficiency The base proficiency of the sword.
     */
    public void setBaseSwordProficiency(float baseSwordProficiency) {
        this.baseSwordProficiency = baseSwordProficiency;
    }

    /**
     * This function returns the baseBowProficiency variable
     * 
     * @return The baseBowProficiency variable is being returned.
     */
    public double getBaseBowProficiency() {
        return baseBowProficiency;
    }

    /**
     * This function sets the baseBowProficiency variable to the value of the
     * parameter passed in
     * 
     * @param baseBowProficiency The base proficiency of the bow.
     */
    public void setBaseBowProficiency(float baseBowProficiency) {
        this.baseBowProficiency = baseBowProficiency;
    }

    /**
     * This function returns the baseCrossbowProficiency variable
     * 
     * @return The baseCrossbowProficiency variable is being returned.
     */
    public double getBaseCrossbowProficiency() {
        return baseCrossbowProficiency;
    }

    /**
     * This function sets the baseCrossbowProficiency variable to the value of the
     * parameter passed in
     * 
     * @param baseCrossbowProficiency The base proficiency of the crossbow.
     */
    public void setBaseCrossbowProficiency(float baseCrossbowProficiency) {
        this.baseCrossbowProficiency = baseCrossbowProficiency;
    }

    /**
     * This function returns the base experience gain of the Pokemon
     * 
     * @return The baseExperienceGain variable is being returned.
     */
    public double getBaseExperienceGain() {
        return baseExperienceGain;
    }

    /**
     * This function sets the base experience gain of the player
     * 
     * @param baseExperienceGain The base experience gain for the player.
     */
    public void setBaseExperienceGain(float baseExperienceGain) {
        this.baseExperienceGain = baseExperienceGain;
    }

    /**
     * This function returns a list of strings that represent the weapon proficiency
     * of the character
     * 
     * @return The weaponProficiency list.
     */
    public List<String> getUsableWeaponTypes() {
        return usableWeaponTypes;
    }

    /**
     * This function sets the weapon proficiency of the character
     * 
     * @param weaponProficiency List of Strings
     */
    public void setUsableWeaponTypes(List<String> weaponProficiency) {
        this.usableWeaponTypes = weaponProficiency;
    }

    /**
     * This function returns a list of strings that represent the armor proficiency
     * of the class
     * 
     * @return A list of strings.
     */
    public List<String> getUsableArmorTypes() {
        return usableArmorTypes;
    }

    /**
     * This function sets the armorProficiency variable to the value of the
     * parameter armorProficiency
     * 
     * @param armorProficiency List of armor types the character is proficient with.
     */
    public void setUsableArmorTypes(List<String> armorProficiency) {
        this.usableArmorTypes = armorProficiency;
    }

    public String toString() {

        return "*** STATS *** \n" +
                "Base Health: " + this.baseHealth + "\n" +
                "Base Speed: " + this.baseSpeed + "\n" +
                "Base Evasion: " + this.baseEvasion + "\n" +
                "Harvest Chance: " + this.harvestChance + "\n" +
                "Double Harvest Chance: " + this.doubleHarvestChance + "\n" +
                "Instant Growth Chance: " + this.instantGrowthChance + "\n" +
                "Behead Chance: " + this.beheadChance + "\n" +
                "Base Sword Proficiency: " + this.baseSwordProficiency + "\n" +
                "Base Bow Proficiency: " + this.baseBowProficiency + "\n" +
                "Base Crossbow Proficiency: " + this.baseCrossbowProficiency + "\n" +
                "Base Experience Gain: " + this.baseExperienceGain + "\n" +
                "Weapon Proficiency: " + this.usableWeaponTypes + "\n" +
                "Armor Proficiency: " + this.usableArmorTypes + "\n" +
                "************* \n";

    }

}
