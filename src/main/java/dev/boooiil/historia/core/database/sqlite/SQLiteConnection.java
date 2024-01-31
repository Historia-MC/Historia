package dev.boooiil.historia.core.database.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.util.Logging;

public class SQLiteConnection {

    private static BasicDataSource dataSource;
    private static Connection connection;

    static {
        if (Main.isTesting)
            initDataSource();
    }

    public static void initDataSource() {
        if (dataSource == null || dataSource.isClosed()) {
            Logging.infoToConsole("(SQLite) Initializing data source.");
            Logging.infoToConsole("(SQLite) Data source location: " + Main.plugin().getDataFolder().getAbsolutePath()
                    + "/database.db");
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("org.sqlite.JDBC");
            dataSource.setUrl("jdbc:sqlite:" + Main.plugin().getDataFolder().getAbsolutePath() + "/database.db");
            dataSource.setMaxTotal(150);
        }
    }

    public static void connect() {

        Logging.debugToConsole("Connecting to SQLite database...");

        try {
            connection = dataSource.getConnection();

            if (connection != null) {
                Logging.debugToConsole("Connected to SQLite database.");
            } else {
                Logging.errorToConsole("Failed to connect to SQLite database.");
            }
        }

        catch (Exception e) {

            Logging.errorToConsole("FAILED TO CONNECT.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("SQLite Error Message: " + e.getMessage());

        }

    }

    public static void closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Logging.debugToConsole("Closed SQLite connection.");
            }
        } catch (SQLException e) {
            Logging.errorToConsole("Failed to close SQLite connection.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("SQLite Error Message: " + e.getMessage());
        }

    }

    public static void closeDataSource() {

        try {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
                Logging.debugToConsole("Closed SQLite data source.");
            }
        } catch (SQLException e) {
            Logging.errorToConsole("Failed to close SQLite data source.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("SQLite Error Message: " + e.getMessage());
        }

    }

    public static Connection getConnection() {

        try {
            if (dataSource != null && !dataSource.isClosed()) {
                connection = dataSource.getConnection();
                return connection;
            }
            return null;
        } catch (Exception e) {
            Logging.errorToConsole("FAILED TO GET CONNECTION.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("SQLite Error Message: " + e.getMessage());
            return null;
        }

    }

}
