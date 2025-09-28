package dev.boooiil.historia.core;

import dev.boooiil.historia.core.util.CoreLogger;
import org.junit.jupiter.api.BeforeAll;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public abstract class BaseTest {

    protected static ServerMock server;

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

        System.out.println("Finished setup.");
    }
}
