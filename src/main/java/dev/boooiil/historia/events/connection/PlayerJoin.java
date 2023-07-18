package dev.boooiil.historia.events.connection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.database.mysql.MySQLHandler;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.handlers.connection.InitialStatLoader;
import dev.boooiil.historia.util.Logging;

/**
 * It creates a new user in the database if they don't exist, sets their login
 * status to true, creates
 * a new HistoriaPlayer object, and adds it to the PlayerStorage HashMap.
 */
public class PlayerJoin implements Listener {

    // Creating a new user in the database if they don't exist, sets their login
    // status to true,
    // creates
    // a new HistoriaPlayer object, and adds it to the PlayerStorage HashMap.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        MySQLHandler.createUser(event.getPlayer().getUniqueId(), event.getPlayer().getDisplayName());
        MySQLHandler.setLogin(event.getPlayer().getUniqueId());

        HistoriaPlayer historiaPlayer = new HistoriaPlayer(event.getPlayer().getUniqueId());
        PlayerStorage.addPlayer(event.getPlayer().getUniqueId(), historiaPlayer);

        HistoriaDiscord.setActivity("Online: " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());

        Logging.debugToConsole("************* INITIAL STATS *************");
        Logging.debugToConsole("Speed: " + event.getPlayer().getWalkSpeed());
        Logging.debugToConsole("Health: " + event.getPlayer().getHealth());
        Logging.debugToConsole("Food: " + event.getPlayer().getFoodLevel());
        Logging.debugToConsole("Saturation: " + event.getPlayer().getSaturation());
        Logging.debugToConsole("Exhaustion: " + event.getPlayer().getExhaustion());
        Logging.debugToConsole("Level: " + event.getPlayer().getLevel());

        InitialStatLoader initialStatLoader = new InitialStatLoader(event.getPlayer());
        initialStatLoader.apply();

        Logging.debugToConsole("************* ADJUSTED STATS *************");
        Logging.debugToConsole("Speed: " + event.getPlayer().getWalkSpeed());
        Logging.debugToConsole("Health: " + event.getPlayer().getHealth());
        Logging.debugToConsole("Food: " + event.getPlayer().getFoodLevel());
        Logging.debugToConsole("Saturation: " + event.getPlayer().getSaturation());
        Logging.debugToConsole("Exhaustion: " + event.getPlayer().getExhaustion());
        Logging.debugToConsole("Level: " + event.getPlayer().getLevel());

    }

}
