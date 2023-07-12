package dev.boooiil.historia.events.connection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.sql.mysql.MySQLHandler;
import dev.boooiil.historia.util.PlayerStorage;

/**
 * It saves the player's data, sets the player as offline, and updates the
 * Discord bot's activity.
 */
public class PlayerLeave implements Listener {

    // A method that is called when a player leaves the server.
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        historiaPlayer.saveCharacter();

        MySQLHandler.setLogout(historiaPlayer.getUUID(), historiaPlayer.getLastLogin(), historiaPlayer.getPlaytime());

        PlayerStorage.markOffline(event.getPlayer().getUniqueId());

        HistoriaDiscord.setActivity("Online: " + (Bukkit.getOnlinePlayers().size() - 1) + "/" + Bukkit.getMaxPlayers());

    }

}
