package dev.boooiil.historia.core.proficiency;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import org.junit.jupiter.api.Test;

public class StatsTest extends BaseTest {

    @Test
    public void testNoneCompleteness() {
        assertStats(ProficiencyName.NONE);
    }

    @Test
    public void testWarriorCompleteness() {
        assertStats(ProficiencyName.WARRIOR);
    }

    @Test
    public void testArcherCompleteness() {
        assertStats(ProficiencyName.ARCHER);
    }

    @Test
    public void testFarmerCompleteness() {
        assertStats(ProficiencyName.FARMER);
    }

    @Test
    public void testMinerCompleteness() {
        assertStats(ProficiencyName.MINER);
    }

    @Test
    public void testBlacksmithCompleteness() {
        assertStats(ProficiencyName.BLACKSMITH);
    }

    @Test
    public void testLumberjackCompleteness() {
        assertStats(ProficiencyName.LUMBERJACK);
    }

    @Test
    public void testFishermanCompleteness() {
        assertStats(ProficiencyName.FISHERMAN);
    }

    @Test
    public void testApothecaryCompleteness() {
        assertStats(ProficiencyName.APOTHECARY);
    }

    @Test
    public void testHuntsmanCompleteness() {
        assertStats(ProficiencyName.HUNTSMAN);
    }

    @Test
    public void testArchitectCompleteness() {
        assertStats(ProficiencyName.ARCHITECT);
    }

    private void assertStats(ProficiencyName proficiencyName) {

        // TODO: remove this when we get stats

        // ConfigurationSection section =
        // config.getConfigurationSection(proficiencyName.getKey() + ".stats");
        // Stats stats = new Stats(section, proficiencyName);

        // assert stats.getBodyStats().getLevel(BodyStatsType.HEALTH) ==
        // section.getInt("baseHealth");
        // // assert stats.getMaxHealth() == section.getInt("maxHealth");
        // // assert stats.getBaseFood() == section.getInt("baseFood");
        // assert stats.getBodyStats().getLevel(BodyStatsType.SPEED) ==
        // section.getDouble("baseSpeed");

        // assert stats.getBodyStats().getLevel(BodyStatsType.EVASION) ==
        // section.getDouble("baseEvasion");
        // assert stats.getWeaponStats().getLevel(WeaponStatsType.SWORD) ==
        // section.getDouble("baseSwordProficiency");
        // assert stats.getWeaponStats().getLevel(WeaponStatsType.BOW) ==
        // section.getDouble("baseBowProficiency");
        // assert stats.getWeaponStats().getLevel(WeaponStatsType.CROSSBOW) == section
        // .getDouble("baseCrossbowProficiency");

        // // assert stats.getBaseExperienceGain() ==
        // // section.getDouble("baseExperienceGain");
        // assert stats.getChanceStats().getLevel(ChanceStatsType.HARVEST) ==
        // section.getDouble("harvestChance");
        // assert stats.getChanceStats().getLevel(ChanceStatsType.DOUBLE_HARVEST) ==
        // section
        // .getDouble("doubleHarvestChance");

        // assert stats.getChanceStats().getLevel(ChanceStatsType.INSTANT_GROWTH) ==
        // section
        // .getDouble("instantGrowthChance");
        // assert stats.getChanceStats().getLevel(ChanceStatsType.BEHEAD) ==
        // section.getDouble("beheadChance");

        // assert stats.getWeaponStats().getUsableWeaponWeights().size() ==
        // section.getStringList("weaponProficiency")
        // .size();
        // assert
        // stats.getWeaponStats().getUsableWeaponWeights().containsAll(section.getStringList("weaponProficiency"));
        // assert stats.getArmorStats().getUsableArmorWeights().size() ==
        // section.getStringList("armorProficiency").size();
        // assert
        // stats.getArmorStats().getUsableArmorWeights().containsAll(section.getStringList("armorProficiency"));
        // assert stats.getExperienceSources().size() ==
        // section.getStringList("experienceSources").size();
        // for (String source : section.getStringList("experienceSources")) {
        // assert stats.getExperienceSources().contains(AllSources.valueOf(source));
        // }
    }
}
