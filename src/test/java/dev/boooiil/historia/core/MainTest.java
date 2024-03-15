package dev.boooiil.historia.core;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest {

    private ServerMock server;
    private PlayerMock player;

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
        System.out.println("Creating player...");
        player = new PlayerMock(server, "MockUser", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        System.out.println("Player created: " + player.getName());

        System.out.println("Finished setup.");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    public void testNewPlayerJoinLeave() {
        server.addPlayer(player);

        System.out.println("Player joined: " + player.getName());

        player.disconnect();

        System.out.println("Player left: " + player.getName());
    }

    @Test
    public void testWarriorProficiency() {

        System.out.println("*********************************");
        System.out.println("Testing Warrior Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Warrior");
    }

    @Test
    public void testArcherProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Archer Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Archer");
    }

    @Test
    public void testFishermanProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Fisherman Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Fisherman");
    }

    @Test
    public void testMinerProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Miner Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Miner");
    }

    @Test
    public void testBlacksmithProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Blacksmith Proficiency");
        System.out.println("*********************************");

        System.out.println(server.getOnlinePlayers().size());

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Blacksmith");
    }

    @Test
    public void testHuntsmanProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Huntsman Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Huntsman");
    }

    @Test
    public void testApothecaryProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Apothecary Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Apothecary");
    }

    @Test
    public void testArchitectProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Architect Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Architect");
    }

    @Test
    public void testLumberjackProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Lumberjack Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Lumberjack");
    }

    @Test
    public void testFarmerProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Farmer Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency("Farmer");
    }
}
