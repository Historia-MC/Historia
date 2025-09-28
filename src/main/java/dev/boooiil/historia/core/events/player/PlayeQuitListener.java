package dev.boooiil.historia.core.events.player;

import dev.boooiil.historia.core.database.internal.TemperatureStorage;
import dev.boooiil.historia.core.handlers.player.PlayerQuitHandler;
import dev.boooiil.historia.core.util.CoreLogger;
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

        CoreLogger.debugToConsole("Issued player quit event for player", event.getPlayer().getName());

        PlayerQuitHandler playerQuitHandler = new PlayerQuitHandler(event);

        playerQuitHandler.doPlayerDBLogout();
        playerQuitHandler.doMarkAsOffline();

        TemperatureStorage.removeTemperatureManager(event.getPlayer().getUniqueId());

    }

}
