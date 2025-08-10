package dev.boooiil.historia.core.proficiency.stats;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;

public class StatModifiers {

    /**
     * Okay, I think that we could get away with not having to init this before
     * we do the proficiency init. This is only the case if we are not using
     * any of the methods within the
     */

    static public final HashMap<String, StatModifiers> MODIFIERS = new HashMap<>();

    private final double bowModifier;
    private final double crossbowModifier;
    private final double tridentModifier;
    private final double swordModifier;
    private final double axeModifier;
    private final double pickaxeModifier;
    private final double shovelModifier;
    private final double hoeModifier;
    private final double helmetModifier;
    private final double chestplateModifier;
    private final double leggingsModifier;
    private final double bootsModifier;
    private final double healthModifier;
    private final double speedModifier;
    private final double evasionModifier;
    private final double toughnessModifier;
    private final double harvestModifier;
    private final double instantGrowthModifier;
    private final double doubleHarvestModifier;
    private final double beheadModifier;

    public StatModifiers(ConfigurationSection config) {
        this.bowModifier = config.getDouble("bow");
        this.crossbowModifier = config.getDouble("crossbow");
        this.tridentModifier = config.getDouble("trident");
        this.swordModifier = config.getDouble("sword");
        this.axeModifier = config.getDouble("axe");
        this.pickaxeModifier = config.getDouble("pickaxe");
        this.shovelModifier = config.getDouble("shovel");
        this.hoeModifier = config.getDouble("hoe");
        this.helmetModifier = config.getDouble("helmet");
        this.chestplateModifier = config.getDouble("chestplate");
        this.leggingsModifier = config.getDouble("leggings");
        this.bootsModifier = config.getDouble("boots");
        this.healthModifier = config.getDouble("health");
        this.speedModifier = config.getDouble("speed");
        this.evasionModifier = config.getDouble("evasion");
        this.toughnessModifier = config.getDouble("toughness");
        this.harvestModifier = config.getDouble("harvest");
        this.instantGrowthModifier = config.getDouble("instant_growth");
        this.doubleHarvestModifier = config.getDouble("double_harvest");
        this.beheadModifier = config.getDouble("behead");
    }

    public double getBowModifier() {
        return bowModifier;
    }

    public double getCrossbowModifier() {
        return crossbowModifier;
    }

    public double getTridentModifier() {
        return tridentModifier;
    }

    public double getSwordModifier() {
        return swordModifier;
    }

    public double getAxeModifier() {
        return axeModifier;
    }

    public double getPickaxeModifier() {
        return pickaxeModifier;
    }

    public double getShovelModifier() {
        return shovelModifier;
    }

    public double getHoeModifier() {
        return hoeModifier;
    }

    public double getHelmetModifier() {
        return helmetModifier;
    }

    public double getChestplateModifier() {
        return chestplateModifier;
    }

    public double getLeggingsModifier() {
        return leggingsModifier;
    }

    public double getBootsModifier() {
        return bootsModifier;
    }

    public double getHealthModifier() {
        return healthModifier;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public double getEvasionModifier() {
        return evasionModifier;
    }

    public double getToughnessModifier() {
        return toughnessModifier;
    }

    public double getHarvestModifier() {
        return harvestModifier;
    }

    public double getInstantGrowthModifier() {
        return instantGrowthModifier;
    }

    public double getDoubleHarvestModifier() {
        return doubleHarvestModifier;
    }

    public double getBeheadModifier() {
        return beheadModifier;
    }

}
