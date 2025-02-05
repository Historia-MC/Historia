package dev.boooiil.historia.core.database.internal;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.Logging;

public class PlayerStorageTest {

    private ServerMock server;

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            MockBukkit.load(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayerStorage.getPlayerMap().clear();
        PlayerStorage.getUsernameMap().clear();

        System.out.println("Finished setup.");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    public void testPlayerAddedOnJoin() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(10);

        for (Player player : Bukkit.getOnlinePlayers()) {
            Logging.debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId().toString());
        }

        assert PlayerStorage.getPlayerMap().size() == 10;

    }

    @Test
    public void testPlayerSetOnlineOnJoin() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(1);

        for (Player player : Bukkit.getOnlinePlayers()) {
            Logging.debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId().toString());
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            assert historiaPlayer.isOnline();
        }

    }

    @Test
    public void testPlayerSetOfflineOnQuit() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(1);

        for (Player player : Bukkit.getOnlinePlayers()) {

            Logging.debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId().toString());
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            assert historiaPlayer.isOnline();

            PlayerMock playerMock = (PlayerMock) player;
            playerMock.disconnect();
            Logging.debugToConsole("Player left: " + player.getName() + " UUID: " + player.getUniqueId().toString());

            assert !historiaPlayer.isOnline();
        }

    }

    @Test
    public void testDatabaseFallback() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(1);

        for (Player player : Bukkit.getOnlinePlayers()) {

            Logging.debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId().toString());
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            assert historiaPlayer.isOnline();

            System.out.println(Main.getDatabaseHandler().getUsername(player.getUniqueId()));

            PlayerMock playerMock = (PlayerMock) player;
            playerMock.disconnect();
            Logging.debugToConsole("Player left: " + player.getName() + " UUID: " + player.getUniqueId().toString());

            assert !historiaPlayer.isOnline();

            PlayerStorage.getPlayerMap().remove(player.getUniqueId());
            PlayerStorage.getUsernameMap().remove(player.getName());

            assert PlayerStorage.getPlayerMap().size() == 0;
            assert PlayerStorage.getUsernameMap().size() == 0;

            historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

            assert !historiaPlayer.isOnline();
            assert historiaPlayer.getUsername() != null;
            assert historiaPlayer.getUsername().equals(player.getName());

            System.out.println(historiaPlayer);

        }
    }

    @Test
    public void testStorageHasPlayerValid() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(1);

        for (Player player : Bukkit.getOnlinePlayers()) {

            Logging.debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId().toString());
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            assert historiaPlayer.isOnline();

            assert PlayerStorage.has(player.getUniqueId());

        }
    }

    @Test
    public void testStorageHasPlayerInvalid() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        assert !PlayerStorage.has(UUID.randomUUID());
    }

    @Test
    public void testGetOnlinePlayerUsername() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(1);

        for (Player player : Bukkit.getOnlinePlayers()) {

            Logging.debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId().toString());
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            assert historiaPlayer.isOnline();

            assert PlayerStorage.has(player.getUniqueId());

            assert PlayerStorage.getPlayer(player.getName()) != null;

        }
    }

    @Test
    public void testGetOfflinePlayerUsername() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        server.setPlayers(1);

        for (Player player : Bukkit.getOnlinePlayers()) {

            Logging.debugToConsole("Player joined: " + player.getName() + " UUID: " + player.getUniqueId().toString());
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            assert historiaPlayer.isOnline();

            assert PlayerStorage.has(player.getUniqueId());

            PlayerMock playerMock = (PlayerMock) player;
            playerMock.disconnect();
            Logging.debugToConsole("Player left: " + player.getName() + " UUID: " + player.getUniqueId().toString());

            assert !historiaPlayer.isOnline();

            PlayerStorage.getPlayerMap().remove(player.getUniqueId());
            PlayerStorage.getUsernameMap().remove(player.getName());

            assert PlayerStorage.getPlayerMap().size() == 0;
            assert PlayerStorage.getUsernameMap().size() == 0;

            historiaPlayer = PlayerStorage.getPlayer(player.getName());

            assert !historiaPlayer.isOnline();
            assert historiaPlayer.getUsername() != null;
            assert historiaPlayer.getUsername().equals(player.getName());

            System.out.println(historiaPlayer);

        }
    }

    @Test
    public void testAddPlayerNotExist() {
        assert PlayerStorage.getPlayerMap().size() == 0;

        PlayerMock playerMock = new PlayerMock(server, "Player0");

        HistoriaPlayer historiaPlayer = new HistoriaPlayer(playerMock.getUniqueId());

        PlayerStorage.addPlayer(playerMock.getUniqueId(), historiaPlayer);

        assert PlayerStorage.getPlayerMap().size() == 1;
        assert PlayerStorage.getUsernameMap().size() == 1;

    }
}
