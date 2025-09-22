package dev.boooiil.historia.core.handlers.player;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitHandler {

    private final PlayerQuitEvent event;

    public PlayerQuitHandler(PlayerQuitEvent event) {
        this.event = event;
    }

    public void doPlayerDBLogout() {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId());

        HistoriaCore.Companion.getDatabaseExecutor().setLogout(event.getPlayer().getUniqueId(), historiaPlayer.getLastLogin(),
                historiaPlayer.getPlaytime());
    }

    public void doMarkAsOffline() {
        PlayerStorage.markOffline(event.getPlayer().getUniqueId());
    }

}
