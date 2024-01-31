package dev.boooiil.historia.core.classes.proficiency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import dev.boooiil.historia.core.Main;

public class ProficiencyTest {

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

        System.out.println("Finished setup.");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    public void testConstructWarrior() {
        Proficiency proficiency = new Proficiency("Warrior");
        assert proficiency.getName().equals("Warrior");
    }

    @Test
    public void testConstructFarmer() {
        Proficiency proficiency = new Proficiency("Farmer");
        assert proficiency.getName().equals("Farmer");
    }

    @Test
    public void testConstructNone() {
        Proficiency proficiency = new Proficiency("None");
        assert proficiency.getName().equals("None");
    }

    @Test
    public void testConstructInvalid() {
        Proficiency proficiency = new Proficiency("Invalid");
        assert proficiency.getName().equals("None");
    }

    @Test
    public void testConstructFisherman() {
        Proficiency proficiency = new Proficiency("Fisherman");
        assert proficiency.getName().equals("Fisherman");
    }

    @Test
    public void testConstructMiner() {
        Proficiency proficiency = new Proficiency("Miner");
        assert proficiency.getName().equals("Miner");
    }

    @Test
    public void testConstructLumberjack() {
        Proficiency proficiency = new Proficiency("Lumberjack");
        assert proficiency.getName().equals("Lumberjack");
    }

    @Test
    public void testConstructArchitect() {
        Proficiency proficiency = new Proficiency("Architect");
        assert proficiency.getName().equals("Architect");
    }

    @Test
    public void testConstructArcher() {
        Proficiency proficiency = new Proficiency("Archer");
        assert proficiency.getName().equals("Archer");
    }

    @Test
    public void testConstructHuntsman() {
        Proficiency proficiency = new Proficiency("Huntsman");
        assert proficiency.getName().equals("Huntsman");
    }

    @Test
    public void testConstructApothecary() {
        Proficiency proficiency = new Proficiency("Apothecary");
        assert proficiency.getName().equals("Apothecary");
    }
}
