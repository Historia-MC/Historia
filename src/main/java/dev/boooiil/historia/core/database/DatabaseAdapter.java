package dev.boooiil.historia.core.database;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.boooiil.historia.core.database.mysql.MySQLConnection;
import dev.boooiil.historia.core.database.mysql.MySQLHandler;
import dev.boooiil.historia.core.database.mysql.MySQLUserKeys;
import dev.boooiil.historia.core.database.sqlite.SQLiteConnection;
import dev.boooiil.historia.core.database.sqlite.SQLiteHandler;

public class DatabaseAdapter {

    private static DatabaseType databaseType;

    enum DatabaseType {
        MYSQL,
        SQLITE
    }

    public static Connection getConnection() {

        switch (databaseType) {
            case MYSQL:
                return MySQLConnection.getConnection();
            default:
                return SQLiteConnection.getConnection();
        }

    }

    public static void connect() {
        if (!MySQLConnection.connect()) {
            SQLiteConnection.connect();
            databaseType = DatabaseType.SQLITE;
        } else {
            databaseType = DatabaseType.MYSQL;
        }
    }

    public static void closeConnection() {
        MySQLConnection.closeConnection();
        SQLiteConnection.closeConnection();
    }

    public static void closeDataSource() {
        MySQLConnection.closeDataSource();
        SQLiteConnection.closeDataSource();
    }

    public static void reconnect() {
        switch (databaseType) {
            case MYSQL:
                MySQLConnection.reconnectOnStale();
                break;
            default:
                break;
        }
    }

    public static void createTable() {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.createTable();
                break;
            default:
                SQLiteHandler.createTable();
                break;
        }
    }

    public static void createUser(UUID uuid, String username) {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.createUser(uuid, username);
                break;
            default:
                SQLiteHandler.createUser(uuid, username);
                break;
        }
    }

    public static void setUsername(UUID uuid, String username) {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.setUsername(uuid, username);
                break;
            default:
                SQLiteHandler.setUsername(uuid, username);
                break;
        }
    }

    public static void setProficiency(UUID uuid, String proficiency) {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.setProficiency(uuid, proficiency);
                break;
            default:
                SQLiteHandler.setProficiency(uuid, proficiency);
                break;
        }
    }

    public static void setProficiencyLevel(UUID uuid, int level) {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.setProficiencyLevel(uuid, level);
                break;
            default:
                SQLiteHandler.setProficiencyLevel(uuid, level);
                break;
        }
    }

    public static void setLogin(UUID uuid) {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.setLogin(uuid);
                break;
            default:
                SQLiteHandler.setLogin(uuid);
                break;
        }
    }

    public static void setLogout(UUID uuid, long lastLogin, long previousPlaytime) {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.setLogout(uuid, lastLogin, previousPlaytime);
                break;
            default:
                SQLiteHandler.setLogout(uuid, lastLogin, previousPlaytime);
                break;
        }
    }

    public static void setCurrentExperience(UUID uuid, double experience) {
        switch (databaseType) {
            case MYSQL:
                MySQLHandler.setCurrentExperience(uuid, experience);
                break;
            default:
                SQLiteHandler.setCurrentExperience(uuid, experience);
                break;
        }
    }

    public static List<String> getUsernames() {
        switch (databaseType) {
            case MYSQL:
                return MySQLHandler.getUsernames();
            default:
                return SQLiteHandler.getUsernames();
        }
    }

    public static String getUsername(UUID uuid) {
        switch (databaseType) {
            case MYSQL:
                return MySQLHandler.getUsername(uuid);
            default:
                return SQLiteHandler.getUsername(uuid);
        }
    }

    public static Map<MySQLUserKeys, String> getUser(UUID uuid) {
        switch (databaseType) {
            case MYSQL:
                return MySQLHandler.getUser(uuid);
            default:
                return SQLiteHandler.getUser(uuid);
        }
    }

    public static List<UUID> getUUIDs() {
        switch (databaseType) {
            case MYSQL:
                return MySQLHandler.getUUIDs();
            default:
                return SQLiteHandler.getUUIDs();
        }
    }

    public static UUID getUUID(String username) {
        switch (databaseType) {
            case MYSQL:
                return MySQLHandler.getUUID(username);
            default:
                return SQLiteHandler.getUUID(username);
        }
    }
}
