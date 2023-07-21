package dev.boooiil.historia.events.pvp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.boooiil.historia.classes.enums.ExperienceTypes.CombatSources;
import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.handlers.pvp.PlayerKilled;

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
