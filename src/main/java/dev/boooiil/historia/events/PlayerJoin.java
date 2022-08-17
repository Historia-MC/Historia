package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        HistoriaPlayer historiaPlayer = new HistoriaPlayer(event.getPlayer().getUniqueId());

        PlayerStorage.addPlayer(event.getPlayer().getUniqueId(), historiaPlayer);

    }

}
