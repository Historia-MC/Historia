package dev.boooiil.historia.core.proficiency;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.registry.RegistryHolder;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;

public class ProficiencyTest extends BaseTest {

    @Test
    public void testConstructClasses() {
        for (ProficiencyName name : ProficiencyName.values()) {
            Proficiency proficiency = getProficiency(name.getKeyLowercase());

            CoreLogger.infoToConsole(proficiency.toJSON());

            // TODO: this should pass when we get skills back
            assert !proficiency.getSkills().isEmpty();
        }
    }

    private Proficiency getProficiency(String proficiency) {
        NamespacedKey key = HistoriaCore.getNamespacedKey(proficiency);

        return RegistryHolder.PROFICIENCY_REGISTRY.get(key);
    }
}
