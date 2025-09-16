package dev.boooiil.historia.core.handlers.player;

import org.bukkit.event.player.PlayerQuitEvent;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

public class PlayerQuitHandler {

    private PlayerQuitEvent event;

    public PlayerQuitHandler(PlayerQuitEvent event) {
        this.event = event;
    }

    public void doPlayerDBLogout() {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId());

        HistoriaCore.Companion.getDatabaseHandler().setLogout(event.getPlayer().getUniqueId(), historiaPlayer.getLastLogin(),
                historiaPlayer.getPlaytime());
    }

    public void doMarkAsOffline() {
        PlayerStorage.markOffline(event.getPlayer().getUniqueId());
    }

}
