package dev.boooiil.historia.core.database.sqlite;

import java.sql.Connection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import be.seeseemelk.mockbukkit.MockBukkit;
import dev.boooiil.historia.core.Main;

public class SQLiteConnectionTest {

    // @BeforeEach
    public void setUp() {
        System.out.println("Setting up mock...");
        MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            MockBukkit.load(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Finished setup.");

    }

    // @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    // disabled due to db refactor
    // @Test
    public void testSQLiteCanConnect() {
        SQLiteConnection connection = new SQLiteConnection();

        assert connection.connect();

        connection.closeConnection();
    }

    // disabled due to db refactor
    // @Test
    public void testGetConnection() {
        SQLiteConnection connection = new SQLiteConnection();

        connection.connect();
        Connection dbconn = connection.getConnection();

        assert dbconn != null;

        connection.closeConnection();
    }
}
