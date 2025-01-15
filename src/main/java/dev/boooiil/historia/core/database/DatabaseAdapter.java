package dev.boooiil.historia.core.database;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.database.IDatabaseConnection.DatabaseType;
import dev.boooiil.historia.core.database.mysql.MySQLUserKeys;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.Proficiency;
import dev.boooiil.historia.core.util.Logging;

public class DatabaseAdapter {

    private static IDatabaseHandler databaseHandler;

    public static void setDatabaseHandler(IDatabaseHandler databaseHandler) {
        DatabaseAdapter.databaseHandler = databaseHandler;
    }

    public static DatabaseType getDatabaseType() {
        return databaseHandler.getDatabaseType();
    }

    /**
     * Attempt to connect to the configured database.
     */
    public static void connect() {

        if (databaseHandler.isErrored()) {
            Logging.errorToConsole("There was an error connecting to the",
                    databaseHandler.getDatabaseType().toString(), "database. Disabling.");
            Main.disable();
        }

        // not sure why i am init data source before connecting
        databaseHandler.initDataSource();
        databaseHandler.connect();

    }

    public static Connection getConnection() {

        return databaseHandler.getConnection();

    }

    public static void closeConnection() {
        databaseHandler.closeConnection();
    }

    public static void closeDataSource() {
        databaseHandler.closeDataSource();
    }

    public static void reconnect() {
        databaseHandler.reconnect();
    }

    public static void createTable() {
        databaseHandler.createTable();
    }

    public static void createUser(UUID uuid, String username) {
        databaseHandler.createUser(uuid, username);
    }

    public static void setUsername(UUID uuid, String username) {
        databaseHandler.setUsername(uuid, username);

    }

    public static void setProficiency(UUID uuid, Proficiency proficiency) {
        databaseHandler.setProficiency(uuid, proficiency);

    }

    public static void setProficiencyLevel(UUID uuid, int level) {
        databaseHandler.setProficiencyLevel(uuid, level);
    }

    public static void setLogin(UUID uuid) {
        databaseHandler.setLogin(uuid);
    }

    public static void setLogout(UUID uuid, long lastLogin, long previousPlaytime) {
        databaseHandler.setLogout(uuid, lastLogin, previousPlaytime);
    }

    public static void setCurrentExperience(UUID uuid, double experience) {
        databaseHandler.setCurrentExperience(uuid, experience);
    }

    public static List<String> getUsernames() {
        return databaseHandler.getUsernames();
    }

    public static String getUsername(UUID uuid) {
        return databaseHandler.getUsername(uuid);
    }

    @Deprecated(forRemoval = true)
    public static Map<MySQLUserKeys, String> getUser(UUID uuid) {
        return databaseHandler.getUser(uuid);
    }

    public static HistoriaPlayer getUser(UUID uuid, boolean opt) {
        return databaseHandler.getUser(uuid, opt);
    }

    public static List<UUID> getUUIDs() {
        return databaseHandler.getUUIDs();
    }

    public static UUID getUUID(String username) {
        return databaseHandler.getUUID(username);
    }

    public static void saveUser(HistoriaPlayer historiaPlayer) {
        databaseHandler.saveUser(historiaPlayer);
    }
}
