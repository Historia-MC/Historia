package dev.boooiil.historia.core.events.connection;

import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.database.mysql.MySQLHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * It saves the player's data, sets the player as offline, and updates the
 * Discord bot activity.
 */
public class PlayeQuitListener implements Listener {

    // A method that is called when a player leaves the server.
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        historiaPlayer.saveCharacter();

        MySQLHandler.setLogout(historiaPlayer.getUUID(), historiaPlayer.getLastLogin(), historiaPlayer.getPlaytime());

        PlayerStorage.markOffline(event.getPlayer().getUniqueId());

    }

}