package dev.boooiil.historia.core.handlers.player;

import org.bukkit.event.player.PlayerJoinEvent;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.database.DatabaseAdapter;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.handlers.connection.InitialStatLoader;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.Logging;

public class PlayerJoinHandler {

    private PlayerJoinEvent event;

    public PlayerJoinHandler(PlayerJoinEvent event) {
        this.event = event;
    }

    public void doPlayerDBInitialization() {
        DatabaseAdapter.createUser(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        DatabaseAdapter.setLogin(event.getPlayer().getUniqueId());
    }

    public void doAddToInternalStorage() {
        HistoriaPlayer historiaPlayer = new HistoriaPlayer(event.getPlayer().getUniqueId());
        PlayerStorage.addPlayer(event.getPlayer().getUniqueId(), historiaPlayer);

        Logging.debugToConsole("************* INITIAL STATS *************");
        Logging.debugToConsole("Speed: " + event.getPlayer().getWalkSpeed());
        Logging.debugToConsole("Health: " + event.getPlayer().getHealth());
        Logging.debugToConsole("Food: " + event.getPlayer().getFoodLevel());
        Logging.debugToConsole("Saturation: " + event.getPlayer().getSaturation());

        // MockBukkit does not have the exhaustion or level attributes implemented.
        if (!Main.isTesting) {
            Logging.debugToConsole("Exhaustion: " + event.getPlayer().getExhaustion());
        }
        Logging.debugToConsole("Level: " + event.getPlayer().getLevel());
    }

    public void doPlayerStatsInitialization() {
        InitialStatLoader initialStatLoader = new InitialStatLoader(event.getPlayer());
        initialStatLoader.apply();

        Logging.debugToConsole("************* ADJUSTED STATS *************");
        Logging.debugToConsole("Speed: " + event.getPlayer().getWalkSpeed());
        Logging.debugToConsole("Health: " + event.getPlayer().getHealth());
        Logging.debugToConsole("Food: " + event.getPlayer().getFoodLevel());
        Logging.debugToConsole("Saturation: " + event.getPlayer().getSaturation());

        // MockBukkit does not have the exhaustion or level attributes implemented.
        if (!Main.isTesting) {
            Logging.debugToConsole("Exhaustion: " + event.getPlayer().getExhaustion());
        }

        Logging.debugToConsole("Level: " + event.getPlayer().getLevel());
    }
}
