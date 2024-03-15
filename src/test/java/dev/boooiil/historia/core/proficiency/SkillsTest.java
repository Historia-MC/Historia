package dev.boooiil.historia.core.proficiency;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.file.FileKeys;
import dev.boooiil.historia.core.proficiency.skills.Skills.SkillType;
import dev.boooiil.historia.core.proficiency.skills.Skills;

public class SkillsTest {

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

        ConfigurationSection section = config.getConfigurationSection("None.skills");

        assert section == null;
    }

    @Test
    public void testWarriorCompleteness() {
        Skills skills = new Skills(config, "Warrior.skills");

        ConfigurationSection section = config.getConfigurationSection("Warrior.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testArcherCompleteness() {
        Skills skills = new Skills(config, "Archer.skills");

        ConfigurationSection section = config.getConfigurationSection("Archer.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testFarmerCompleteness() {
        Skills skills = new Skills(config, "Farmer.skills");

        ConfigurationSection section = config.getConfigurationSection("Farmer.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testMinerCompleteness() {
        Skills skills = new Skills(config, "Miner.skills");

        ConfigurationSection section = config.getConfigurationSection("Miner.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testLumberjackCompleteness() {
        Skills skills = new Skills(config, "Lumberjack.skills");

        ConfigurationSection section = config.getConfigurationSection("Lumberjack.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testBlacksmithCompleteness() {
        Skills skills = new Skills(config, "Blacksmith.skills");

        ConfigurationSection section = config.getConfigurationSection("Blacksmith.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testFishermanCompleteness() {
        Skills skills = new Skills(config, "Fisherman.skills");

        ConfigurationSection section = config.getConfigurationSection("Fisherman.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testHuntsmanCompleteness() {
        Skills skills = new Skills(config, "Huntsman.skills");

        ConfigurationSection section = config.getConfigurationSection("Huntsman.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }

    @Test
    public void testApothecaryCompleteness() {
        Skills skills = new Skills(config, "Apothecary.skills");

        ConfigurationSection section = config.getConfigurationSection("Apothecary.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);

                }
            }
        }
    }

    @Test
    public void testArchitectCompleteness() {
        Skills skills = new Skills(config, "Architect.skills");

        ConfigurationSection section = config.getConfigurationSection("Architect.skills");

        for (String key : section.getKeys(false)) {
            for (SkillType skill : SkillType.values()) {
                if (skill.getKey().equals(key)) {
                    System.out.println("Testing " + skill.getKey());
                    System.out.println("Expected: " + section.getBoolean(key) + " Actual: " + skills.hasSkill(skill));
                    assert skills.hasSkill(skill) == section.getBoolean(key);
                }
            }
        }
    }
}
