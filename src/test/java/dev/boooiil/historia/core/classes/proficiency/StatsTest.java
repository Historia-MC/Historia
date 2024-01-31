package dev.boooiil.historia.core.classes.proficiency;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.classes.enums.experience.AllSources;
import dev.boooiil.historia.core.classes.enums.file.FileKeys;
import dev.boooiil.historia.core.util.FileIO;

public class StatsTest {
    FileConfiguration config;

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up mock...");
        MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            MockBukkit.load(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        config = FileIO.get(FileKeys.PROFICIENCY);

        System.out.println("Finished setup.");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    public void testNoneCompleteness() {
        Stats stats = new Stats(config, "None.stats");

        ConfigurationSection section = config.getConfigurationSection("None.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");

        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");

        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");

        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));
        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));
        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();
        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }
    }

    @Test
    public void testWarriorCompleteness() {
        Stats stats = new Stats(config, "Warrior.stats");

        ConfigurationSection section = config.getConfigurationSection("Warrior.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }

    }

    @Test
    public void testArcherCompleteness() {
        Stats stats = new Stats(config, "Archer.stats");

        ConfigurationSection section = config.getConfigurationSection("Archer.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }

    }

    @Test
    public void testFarmerCompleteness() {
        Stats stats = new Stats(config, "Farmer.stats");

        ConfigurationSection section = config.getConfigurationSection("Farmer.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }

    }

    @Test
    public void testMinerCompleteness() {
        Stats stats = new Stats(config, "Miner.stats");

        ConfigurationSection section = config.getConfigurationSection("Miner.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }

    }

    @Test
    public void testBlacksmithCompleteness() {
        Stats stats = new Stats(config, "Blacksmith.stats");

        ConfigurationSection section = config.getConfigurationSection("Blacksmith.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }

    }

    @Test
    public void testLumberjackCompleteness() {
        Stats stats = new Stats(config, "Lumberjack.stats");

        ConfigurationSection section = config.getConfigurationSection("Lumberjack.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }

    }

    @Test
    public void testFishermanCompleteness() {
        Stats stats = new Stats(config, "Fisherman.stats");

        ConfigurationSection section = config.getConfigurationSection("Fisherman.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }

    }

    @Test
    public void testApothecaryCompleteness() {
        Stats stats = new Stats(config, "Apothecary.stats");

        ConfigurationSection section = config.getConfigurationSection("Apothecary.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }
    }

    @Test
    public void testHuntsmanCompleteness() {
        Stats stats = new Stats(config, "Huntsman.stats");

        ConfigurationSection section = config.getConfigurationSection("Huntsman.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");
        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");
        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");
        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");
        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));
        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));
        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();
        for (String source : section.getStringList("experienceSources")) {
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        }
    }

    @Test
    public void testArchitectCompleteness() {
        Stats stats = new Stats(config, "Architect.stats");

        ConfigurationSection section = config.getConfigurationSection("Architect.stats");

        assert stats.getBaseHealth() == section.getInt("baseHealth");
        assert stats.getMaxHealth() == section.getInt("maxHealth");
        assert stats.getBaseFood() == section.getInt("baseFood");
        assert stats.getBaseSpeed() == section.getDouble("baseSpeed");

        assert stats.getBaseEvasion() == section.getDouble("baseEvasion");
        assert stats.getBaseSwordProficiency() == section.getDouble("baseSwordProficiency");
        assert stats.getBaseBowProficiency() == section.getDouble("baseBowProficiency");
        assert stats.getBaseCrossbowProficiency() == section.getDouble("baseCrossbowProficiency");

        assert stats.getBaseExperienceGain() == section.getDouble("baseExperienceGain");
        assert stats.getHarvestChance() == section.getDouble("harvestChance");
        assert stats.getDoubleHarvestChance() == section.getDouble("doubleHarvestChance");

        assert stats.getInstantGrowthChance() == section.getDouble("instantGrowthChance");
        assert stats.getBeheadChance() == section.getDouble("beheadChance");

        assert stats.getUsableWeaponTypes().size() == section.getStringList("weaponProficiency").size();
        assert stats.getUsableWeaponTypes().containsAll(section.getStringList("weaponProficiency"));

        assert stats.getUsableArmorTypes().size() == section.getStringList("armorProficiency").size();
        assert stats.getUsableArmorTypes().containsAll(section.getStringList("armorProficiency"));

        assert stats.getExperienceSources().size() == section.getStringList("experienceSources").size();

        for (String source : section.getStringList("experienceSources"))
            assert stats.getExperienceSources().contains(AllSources.valueOf(source));
    }
}
