package dev.boooiil.historia.configuration;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

public class ClassConfig {

    private static FileConfiguration configuration = FileGetter.get("class.yml");

    //These are here for testing purposes.
    //Static reference will use less memory than creating a new instance in each HistoriaPlayer
    public static ClassConfig noneConfig = new ClassConfig("None");
    public static ClassConfig warriorConfig = new ClassConfig("Warrior");
    public static ClassConfig archerConfig = new ClassConfig("Archer");
    public static ClassConfig fishermanConfig = new ClassConfig("Fisherman");
    public static ClassConfig minerConfig = new ClassConfig("Miner");
    public static ClassConfig blacksmithConfig = new ClassConfig("Blacksmith");
    public static ClassConfig huntsmanConfig = new ClassConfig("Huntsman");
    public static ClassConfig apothecaryConfig = new ClassConfig("Apothecary");
    public static ClassConfig architectConfig = new ClassConfig("Architect");
    public static ClassConfig lumberjackConfig = new ClassConfig("Lumberjack");
    public static ClassConfig farmerConfig = new ClassConfig("Farmer");

    public int baseHealth;
    public int maxHealth;
    public int baseFood;

    public float baseSpeed;
    public float baseEvasion;
    public float baseSwordProficiency;
    public float baseBowProficiency;
    public float baseCrossbowProficiency;
    public float baseExperienceGain;

    // ** SKILL MODIFIERS ** //
    public float harvestChance;
    public float doubleHarvestChance;
    public float instantGrowthChance;
    public float beheadChance;

    public boolean hasNameTag;
    public boolean hasFeatherFall;
    public boolean hasQuickCharge;

    public boolean hasEfficiencyPickaxe;
    public boolean hasEfficiencyAxe;

    public boolean hasChanceExtraOre;
    public boolean hasChanceExtraWood;
    public boolean hasChanceExtraFeathers;
    public boolean hasChanceNoAnvilDamage;
    public boolean hasChanceNoConsumeBlock;

    public boolean canIgniteOil;
    public boolean canExtractOil;
    public boolean canBreakGlass;
    public boolean canCraftSaddle;
    public boolean canTameAnimals;
    public boolean canSweepingEdge;
    public boolean canExtractBones;
    public boolean canBreakBeehive;
    public boolean canCraftGunpowder;
    public boolean canApplyUnbreaking;
    public boolean canMakeHighTierArmor;
    public boolean canMakeKnowledgeBook;

    /**
     * Don't know what this is for yet.
     */
    public boolean specialItems;
    // ** END MODIFIERS ** //

    public List<String> weaponProficiency;
    public List<String> armorProficiency;

    private ClassConfig(String className) {

        String statsRoot = className + ".stats";
        String skillRoot = className + ".skills";

        this.baseHealth = configuration.getInt(statsRoot + ".baseHealth");
        this.maxHealth = configuration.getInt(statsRoot + ".maxHealth");
        this.baseFood = configuration.getInt(statsRoot + ".baseFood");

        this.baseSpeed = configuration.getLong(statsRoot + ".baseSpeed");
        this.baseEvasion = configuration.getLong(statsRoot + ".baseEvasion");
        this.baseSwordProficiency = configuration.getLong(statsRoot + ".baseSwordProficiency");
        this.baseBowProficiency = configuration.getLong(statsRoot + ".baseBowProficiency");
        this.baseCrossbowProficiency = configuration.getLong(statsRoot + ".baseCrossbowProficiency");
        this.baseExperienceGain = configuration.getLong(statsRoot + ".baseExperienceGain");

        // If they have the base class they should not be having skills.
        if (!className.equals("None")) {

            this.harvestChance = configuration.getLong(skillRoot + ".harvestChance");
            this.beheadChance = configuration.getLong(skillRoot + ".beheadChance");

            this.hasNameTag = configuration.getBoolean(skillRoot + ".nametag");
            this.canSweepingEdge = configuration.getBoolean(skillRoot + ".sweepingEdge");
            this.hasQuickCharge = configuration.getBoolean(skillRoot + ".quickCharge");
            this.canExtractOil = configuration.getBoolean(skillRoot + ".extractOil");
            this.canIgniteOil = configuration.getBoolean(skillRoot + ".igniteOil");

        }

        this.weaponProficiency = configuration.getStringList(statsRoot + ".weaponProficiency");
        this.armorProficiency = configuration.getStringList(statsRoot + ".armorProficiency");

    }

    public static ClassConfig getConfig(String className) {

        if (className.equals("Warrior")) return warriorConfig;
        if (className.equals("Archer")) return archerConfig;
        if (className.equals("Fisherman")) return fishermanConfig;
        if (className.equals("Miner")) return minerConfig;
        if (className.equals("Blacksmith")) return warriorConfig;
        if (className.equals("Huntsman")) return blacksmithConfig;
        if (className.equals("Apothecary")) return apothecaryConfig;
        if (className.equals("Architect")) return architectConfig;
        if (className.equals("Lumberjack")) return lumberjackConfig;
        if (className.equals("Farmer")) return farmerConfig;
        else return noneConfig;

    }

    public static void reloadConfig() {

        noneConfig = new ClassConfig("None");
        warriorConfig = new ClassConfig("Warrior");
        archerConfig = new ClassConfig("Archer");
        fishermanConfig = new ClassConfig("Fisherman");
        minerConfig = new ClassConfig("Miner");
        blacksmithConfig = new ClassConfig("Blacksmith");
        huntsmanConfig = new ClassConfig("Huntsman");
        apothecaryConfig = new ClassConfig("Apothecary");
        architectConfig = new ClassConfig("Architect");
        lumberjackConfig = new ClassConfig("Lumberjack");
        farmerConfig = new ClassConfig("Farmer");

    }

}
