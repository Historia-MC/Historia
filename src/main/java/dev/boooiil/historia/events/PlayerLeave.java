package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerLeave implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerKickEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId());

        historiaPlayer.saveCharacter();
        
        PlayerStorage.removePlayer(event.getPlayer().getUniqueId());

    }

}
