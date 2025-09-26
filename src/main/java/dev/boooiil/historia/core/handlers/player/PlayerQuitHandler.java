package dev.boooiil.historia.core.handlers.player;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.database.sql.tables.HistoriaDBFields;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitHandler {

    private final PlayerQuitEvent event;

    public PlayerQuitHandler(PlayerQuitEvent event) {
        this.event = event;
    }

    public void doPlayerDBLogout() {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId());

        long time = System.currentTimeMillis();
        long previous = historiaPlayer.getLastLogin();

        HistoriaDBFields.LOGOUT.update(
                time,
                historiaPlayer.getUUID());
        HistoriaDBFields.PLAYTIME.update(
                (time - previous) + historiaPlayer.getPlaytime(),
                historiaPlayer.getUUID());
    
    }

    public void doMarkAsOffline() {
        PlayerStorage.markOffline(event.getPlayer().getUniqueId());
    }

}
