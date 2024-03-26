package dev.boooiil.historia.core.database.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.database.DatabaseConnection;
import dev.boooiil.historia.core.util.Logging;

public class SQLiteConnection implements DatabaseConnection<SQLiteConnection> {

    private HikariDataSource dataSource;
    private Connection connection;

    public void initDataSource() {
        if (dataSource == null || dataSource.isClosed()) {
            Logging.infoToConsole("(SQLite) Initializing data source.");
            Logging.infoToConsole("(SQLite) Data source location: " + Main.plugin().getDataFolder().getAbsolutePath()
                    + "/database.db");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlite:" + Main.plugin().getDataFolder().getAbsolutePath() + "/database.db");
            config.setMaximumPoolSize(150); // Set max pool size

            dataSource = new HikariDataSource(config);
        }
    }

    public boolean connect() {

        Logging.debugToConsole("Connecting to SQLite database...");

        try {
            connection = dataSource.getConnection();

            if (connection != null) {
                Logging.debugToConsole("Connected to SQLite database.");
                return true;
            } else {
                Logging.errorToConsole("Failed to connect to SQLite database.");
            }
        }

        catch (Exception e) {

            Logging.errorToConsole("FAILED TO CONNECT.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("SQLite Error Message: " + e.getMessage());

        }

        return false;

    }

    public void closeConnection() {

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

    public void closeDataSource() {

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            Logging.debugToConsole("Closed SQLite data source.");
        }

    }

    public Connection getConnection() {

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

    public void reconnect() {
        // No need to reconnect for SQLite
    }

}
