package dev.boooiil.historia.events.pvp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.handlers.pvp.PlayerKilled;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player deadEntity = event.getEntity();
        HistoriaPlayer historiaKiller = PlayerStorage.getPlayer(deadEntity.getKiller().getUniqueId(), false);

        PlayerKilled playerKilled = new PlayerKilled(event, historiaKiller);
        playerKilled.doDeath();

    }

}
