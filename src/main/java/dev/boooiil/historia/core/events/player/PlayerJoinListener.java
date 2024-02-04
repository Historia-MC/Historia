package dev.boooiil.historia.core.events.player;

import dev.boooiil.historia.core.handlers.player.PlayerJoinHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * It creates a new user in the database if they don't exist, sets their login
 * status to true, creates
 * a new HistoriaPlayer object, and adds it to the PlayerStorage HashMap.
 */
public class PlayerJoinListener implements Listener {

    // Creating a new user in the database if they don't exist, sets their login
    // status to true,
    // creates
    // a new HistoriaPlayer object, and adds it to the PlayerStorage HashMap.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        PlayerJoinHandler playerJoinHandler = new PlayerJoinHandler(event);

        playerJoinHandler.doPlayerDBInitialization();
        playerJoinHandler.doAddToInternalStorage();
        playerJoinHandler.doPlayerStatsInitialization();

    }

}
