package dev.boooiil.historia.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.mysql.MySQLHandler;
import dev.boooiil.historia.util.PlayerStorage;

/**
 * It creates a new user in the database if they don't exist, sets their login status to true, creates
 * a new HistoriaPlayer object, and adds it to the PlayerStorage HashMap.
 */
public class PlayerJoin implements Listener {

    @EventHandler
    // Creating a new user in the database if they don't exist, sets their login status to true,
    // creates
    // a new HistoriaPlayer object, and adds it to the PlayerStorage HashMap.
    public void onPlayerJoin(PlayerJoinEvent event) {

        MySQLHandler.createUser(event.getPlayer().getUniqueId(), event.getPlayer().getDisplayName());
        MySQLHandler.setLogin(event.getPlayer().getUniqueId());
        
        HistoriaPlayer historiaPlayer = new HistoriaPlayer(event.getPlayer().getUniqueId());
        PlayerStorage.addPlayer(event.getPlayer().getUniqueId(), historiaPlayer);

        HistoriaDiscord.setActivity("Online: " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());


    }

}
