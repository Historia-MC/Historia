package dev.boooiil.historia.core;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.Proficiency;
import dev.boooiil.historia.core.registry.RegistryHolder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MainTest extends BaseTest {

    @Test
    public void testProficiency() {

        for (Map.Entry<NamespacedKey, Proficiency> proficiency : RegistryHolder.PROFICIENCY_REGISTRY.entrySet()) {
            Player player = server.addPlayer();

            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            historiaPlayer.changeProficiency(proficiency.getKey());

        }

    }

}
