package dev.boooiil.historia.core;

import dev.boooiil.historia.core.util.CoreLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.UUID;

public abstract class BaseTest {

    protected static ServerMock server;
    protected static PlayerMock player;

    @BeforeAll
    public static void setup() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");

        assert server != null;

        try {
            MockBukkit.load(dev.boooiil.historia.core.HistoriaCore.class);
        } catch (Exception e) {
            CoreLogger.errorToConsole(e.toString());
            System.exit(1);
        }
        System.out.println("Creating player...");
        player = new PlayerMock(server, "TestUser1", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        System.out.println("Player created: " + player.getName());

        System.out.println("Finished setup.");
    }

    @AfterAll
    public static void teardown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

}
