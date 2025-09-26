package dev.boooiil.historia.core.handlers.player;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.database.sql.tables.HistoriaDBFields;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinHandler {

    private final PlayerJoinEvent event;
    private HistoriaPlayer historiaPlayer;

    public PlayerJoinHandler(PlayerJoinEvent event) {
        this.event = event;
    }

    public void doPlayerDBInitialization() {
        historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId());
        historiaPlayer.setLastLogin(System.currentTimeMillis());
        HistoriaDBFields.LOGIN.update(
                historiaPlayer.getLastLogin(),
                historiaPlayer.getUUID());
    }

    public void doAddToInternalStorage() {

        CoreLogger.debugToConsole("************* INITIAL STATS *************");
        CoreLogger.debugToConsole("Speed: " + event.getPlayer().getWalkSpeed());
        CoreLogger.debugToConsole("Health: " + event.getPlayer().getHealth());
        CoreLogger.debugToConsole("Food: " + event.getPlayer().getFoodLevel());
        CoreLogger.debugToConsole("Saturation: " + event.getPlayer().getSaturation());

        // MockBukkit does not have the exhaustion or level attributes implemented.
        if (!HistoriaCore.isTesting) {
            CoreLogger.debugToConsole("Exhaustion: " + event.getPlayer().getExhaustion());
        }
        CoreLogger.debugToConsole("Level: " + event.getPlayer().getLevel());
    }

    public void doPlayerStatsInitialization() {

        CoreLogger.debugToConsole("************* ADJUSTED STATS *************");
        CoreLogger.debugToConsole("Speed: " + event.getPlayer().getWalkSpeed());
        CoreLogger.debugToConsole("Health: " + event.getPlayer().getHealth());
        CoreLogger.debugToConsole("Food: " + event.getPlayer().getFoodLevel());
        CoreLogger.debugToConsole("Saturation: " + event.getPlayer().getSaturation());

        // MockBukkit does not have the exhaustion or level attributes implemented.
        if (!HistoriaCore.isTesting) {
            CoreLogger.debugToConsole("Exhaustion: " + event.getPlayer().getExhaustion());
        }

        CoreLogger.debugToConsole("Level: " + event.getPlayer().getLevel());
    }
}
