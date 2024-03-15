package dev.boooiil.historia.core.database.sqlite;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.database.mysql.MySQLUserKeys;
import dev.boooiil.historia.core.player.HistoriaPlayer;

public class SQLiteHandlerTest {

    ServerMock server;

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

        System.out.println("Finished setup.");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    public void testCreateTable() {
        assert SQLiteHandler.createTable();
    }

    @Test
    public void testCreateUser() {
        PlayerMock player = new PlayerMock(server, "Player0");

        assert SQLiteHandler.createUser(player.getUniqueId(), player.getName());
        assert SQLiteHandler.getUsername(player.getUniqueId()) != null;
    }

    @Test
    public void testSetUsername() {
        Player player = server.addPlayer();

        assert SQLiteHandler.setUsername(player.getUniqueId(), "test");
        assert SQLiteHandler.getUsername(player.getUniqueId()).equals("test");
    }

    @Test
    public void testSetProficiency() {
        Player player = server.addPlayer();

        assert SQLiteHandler.setProficiency(player.getUniqueId(), "Warrior");
        assert SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.CLASS).equals("Warrior");
    }

    @Test
    public void testSetLevel() {
        Player player = server.addPlayer();

        assert SQLiteHandler.setProficiencyLevel(player.getUniqueId(), 2);
        assert SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.LEVEL).equals("2");
    }

    @Test
    public void testSetLogin() {
        Player player = server.addPlayer();

        String login = SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.LOGIN);

        try {
            wait(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert SQLiteHandler.setLogin(player.getUniqueId());
        assert !SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.LOGIN).equals(login);
    }

    @Test
    public void testSetLogout() {
        Player player = server.addPlayer();
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        String logout = SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.LOGOUT);

        assert SQLiteHandler.setLogout(player.getUniqueId(), historiaPlayer.getLastLogin(),
                historiaPlayer.getPlaytime());
        assert !SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.LOGOUT).equals(logout);
    }

    @Test
    public void testSetCurrentExperience() {
        Player player = server.addPlayer();

        assert SQLiteHandler.setCurrentExperience(player.getUniqueId(), 100);
        assert SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.EXPERIENCE).equals("100");
    }

    @Test
    public void testGetUsernames() {
        server.addPlayer();
        assert SQLiteHandler.getUsernames() != null;
    }

    @Test
    public void testGetUsername() {
        Player player = server.addPlayer();

        assert SQLiteHandler.getUsername(player.getUniqueId()) != null;
    }

    @Test
    public void testGetUser() {
        Player player = server.addPlayer();

        assert !SQLiteHandler.getUser(player.getUniqueId()).get(MySQLUserKeys.USERNAME).equals("null");
    }

    @Test
    public void testGetUUIDs() {
        server.addPlayer();
        assert SQLiteHandler.getUUIDs() != null;
    }

    @Test
    public void testGetUUID() {
        Player player = server.addPlayer();

        assert SQLiteHandler.getUUID(player.getName()) != null;
    }

}
