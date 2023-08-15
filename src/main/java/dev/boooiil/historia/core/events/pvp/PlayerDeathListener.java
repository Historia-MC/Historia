package dev.boooiil.historia.core.events.pvp;

import dev.boooiil.historia.core.classes.enums.ExperienceTypes.CombatSources;
import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.handlers.pvp.PlayerKilled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player deadEntity = event.getEntity();

        if (deadEntity.getKiller() == null) {

            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(deadEntity.getUniqueId(), false);

            historiaPlayer.decreaseExperience(CombatSources.DEATH.getKey());

        }

        else {

            HistoriaPlayer historiaKiller = PlayerStorage.getPlayer(deadEntity.getKiller().getUniqueId(), false);

            PlayerKilled playerKilled = new PlayerKilled(event, historiaKiller);
            playerKilled.doDeath();

        }

    }

}
