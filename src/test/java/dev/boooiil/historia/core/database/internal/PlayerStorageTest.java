package dev.boooiil.historia.core.database.internal;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.database.sql.tables.HistoriaTable;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.UUID;

public class PlayerStorageTest extends BaseTest {

    @BeforeEach
    public void clearStorage() {
        if (PlayerStorage.getPlayerMap() != null) {
            PlayerStorage.getPlayerMap().clear();
            PlayerStorage.getUsernameMap().clear();
        }

        if (player.isOnline()) player.disconnect();
    }

    @Test
    public void testPlayerAddedOnJoin() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(10);

        for (Player player : Bukkit.getOnlinePlayers()) {
            CoreLogger
                    .debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId());
        }

        assert PlayerStorage.getPlayerMap().size() == 10;

    }

    @Test
    public void testPlayerSetOnlineOnJoin() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        PlayerMock player = server.addPlayer();

        CoreLogger
                .debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId());
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
        assert historiaPlayer.isOnline();

        player.disconnect();

    }

    @Test
    public void testPlayerSetOfflineOnQuit() {
        assert PlayerStorage.getPlayerMap().isEmpty();

        PlayerMock player = server.addPlayer();

        CoreLogger
                .debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId());
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
        assert historiaPlayer.isOnline();

        player.disconnect();
        CoreLogger.debugToConsole("Player left: " + player.getName() + " UUID: " + player.getUniqueId());

        assert !historiaPlayer.isOnline();

    }

    @Test
    public void testDatabaseFallback() {
        assert PlayerStorage.getPlayerMap().isEmpty();

        PlayerMock player = server.addPlayer();

        CoreLogger
                .debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId());
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
        assert historiaPlayer.isOnline();

        System.out.println(HistoriaTable.USERNAME.get(player.getUniqueId()));

        player.disconnect();
        CoreLogger.debugToConsole("Player left: " + player.getName() + " UUID: " + player.getUniqueId());

        assert !historiaPlayer.isOnline();

        PlayerStorage.getPlayerMap().remove(player.getUniqueId());
        PlayerStorage.getUsernameMap().remove(player.getName());

        assert PlayerStorage.getPlayerMap().isEmpty();
        assert PlayerStorage.getUsernameMap().isEmpty();

        historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        assert !historiaPlayer.isOnline();
        assert historiaPlayer.getUsername() != null;
        assert historiaPlayer.getUsername().equals(player.getName());

        System.out.println(historiaPlayer);

    }

    @Test
    public void testStorageHasPlayerValid() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        PlayerMock player = server.addPlayer();

        CoreLogger
                .debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId());
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
        assert historiaPlayer.isOnline();

        assert PlayerStorage.has(player.getUniqueId());

    }

    @Test
    public void testStorageHasPlayerInvalid() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        assert !PlayerStorage.has(UUID.randomUUID());
    }

    @Test
    public void testGetOnlinePlayerUsername() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        PlayerMock player = server.addPlayer();


        CoreLogger
                .debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId());
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
        assert historiaPlayer.isOnline();

        assert PlayerStorage.has(player.getUniqueId());

        assert PlayerStorage.getPlayer(player.getName()) != null;

    }

    @Test
    public void testGetOfflinePlayerUsername() {
        assert PlayerStorage.getPlayerMap().isEmpty();

        server.addPlayer(player);

        CoreLogger
                .debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId());
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
        assert historiaPlayer.isOnline();

        assert PlayerStorage.has(player.getUniqueId());

        player.disconnect();
        CoreLogger.debugToConsole("Player left: " + player.getName() + " UUID: " + player.getUniqueId());

        assert !historiaPlayer.isOnline();

        PlayerStorage.getPlayerMap().remove(player.getUniqueId());
        PlayerStorage.getUsernameMap().remove(player.getName());

        assert PlayerStorage.getPlayerMap().isEmpty();
        assert PlayerStorage.getUsernameMap().isEmpty();

        historiaPlayer = PlayerStorage.getPlayer(player.getName());

        assert !historiaPlayer.isOnline();
        assert historiaPlayer.getUsername() != null;
        assert historiaPlayer.getUsername().equals(player.getName());

        System.out.println(historiaPlayer);
    }

    @Test
    public void testAddPlayerNotExist() {
        assert PlayerStorage.getPlayerMap().isEmpty();

        PlayerMock playerMock = new PlayerMock(server, "Player0");

        System.out.println("player " + playerMock.displayName());

        PlayerStorage.addPlayer(playerMock);

        assert PlayerStorage.getPlayerMap().size() == 1;
        assert PlayerStorage.getUsernameMap().size() == 1;

    }
}
