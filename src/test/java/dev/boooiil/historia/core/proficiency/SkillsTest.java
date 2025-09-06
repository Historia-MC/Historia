//package dev.boooiil.historia.core.proficiency;
//
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import org.mockbukkit.mockbukkit.MockBukkit;
//import dev.boooiil.historia.core.HistoriaCore;
//import dev.boooiil.historia.core.file.FileIO;
//import dev.boooiil.historia.core.file.FileKeys;
//import dev.boooiil.historia.core.proficiency.skills.Skills.SkillName;
//import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
//import dev.boooiil.historia.core.proficiency.skills.Skills;
//
//public class SkillsTest {
//
//    FileConfiguration config;
//
//    @BeforeEach
//    public void setUp() {
//        System.out.println("Setting up mock...");
//        MockBukkit.mock();
//        System.out.println("Loading plugin...");
//        try {
//            MockBukkit.load(HistoriaCore.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        config = FileIO.get(FileKeys.PROFICIENCY);
//
//        System.out.println("Finished setup.");
//
//    }
//
//    @AfterEach
//    public void tearDown() {
//        System.out.println("Tearing down mock...");
//        MockBukkit.unmock();
//    }
//
//    @Test
//    public void testNoneCompleteness() {
//
//        ConfigurationSection section = config.getConfigurationSection("None.skills");
//
//        assert section == null;
//    }
//
//    @Test
//    public void testWarriorCompleteness() {
//        assertSkills(ProficiencyName.WARRIOR);
//    }
//
//    @Test
//    public void testArcherCompleteness() {
//        assertSkills(ProficiencyName.ARCHER);
//    }
//
//    @Test
//    public void testFarmerCompleteness() {
//        assertSkills(ProficiencyName.FARMER);
//    }
//
//    @Test
//    public void testMinerCompleteness() {
//        assertSkills(ProficiencyName.MINER);
//    }
//
//    @Test
//    public void testLumberjackCompleteness() {
//        assertSkills(ProficiencyName.LUMBERJACK);
//    }
//
//    @Test
//    public void testBlacksmithCompleteness() {
//        assertSkills(ProficiencyName.BLACKSMITH);
//    }
//
//    @Test
//    public void testFishermanCompleteness() {
//        assertSkills(ProficiencyName.FISHERMAN);
//    }
//
//    @Test
//    public void testHuntsmanCompleteness() {
//        assertSkills(ProficiencyName.HUNTSMAN);
//    }
//
//    @Test
//    public void testApothecaryCompleteness() {
//        assertSkills(ProficiencyName.APOTHECARY);
//    }
//
//    @Test
//    public void testArchitectCompleteness() {
//        assertSkills(ProficiencyName.ARCHITECT);
//    }
//
//    private void assertSkills(ProficiencyName proficiencyName) {
//
//        ConfigurationSection section = config.getConfigurationSection(proficiencyName.getKey() + ".skills");
//        Skills skills = new Skills(config);
//
//        for (String key : section.getKeys(false)) {
//            for (SkillName skill : SkillName.values()) {
//                if (skill.getKey().equals(key)) {
//                    System.out.println("Testing " + skill.getKey());
//                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
//                    assert skills.hasSkill(skill) == section.getBoolean(key);
//                }
//            }
//        }
//    }
//}
