package dev.boooiil.historia.core.proficiency;

import dev.boooiil.historia.core.registry.Registry;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockbukkit.mockbukkit.MockBukkit;
import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;

public class ProficiencyTest {

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up mock...");
        MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            MockBukkit.load(HistoriaCore.class);
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
    public void testConstructClasses() {
        for (ProficiencyName name : ProficiencyName.values()) {
            Proficiency proficiency = getProficiency(name.getKeyLowercase());

            assert !proficiency.getSkills().isEmpty();
        }
    }

    private Proficiency getProficiency(String proficiency) {

        NamespacedKey key = HistoriaCore.getNamespacedKey(proficiency);
        Registry<@NotNull Proficiency> registry = HistoriaCore.PROFICIENCY_REGISTRY;

        assert(registry != null);

        return HistoriaCore.PROFICIENCY_REGISTRY.get(key);

    }
}
