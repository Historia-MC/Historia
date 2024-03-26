package dev.boooiil.historia.core.database.mysql;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.database.DatabaseConnection;
import dev.boooiil.historia.core.util.Logging;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLConnection implements DatabaseConnection<MySQLConnection> {

    private String database = ConfigurationLoader.getGeneralConfig().database;
    private String username = ConfigurationLoader.getGeneralConfig().username;
    private String password = ConfigurationLoader.getGeneralConfig().password;
    private String ip = ConfigurationLoader.getGeneralConfig().ip;
    private String port = ConfigurationLoader.getGeneralConfig().port;

    private HikariDataSource dataSource;
    private Connection connection;
    private boolean errored;

    public MySQLConnection() {
        if (validateFields()) {
            initDataSource();
        } else {
            Logging.errorToConsole("MYSQL FIELDS ARE NULL. CHECK THE CONFIGURATION FILE.");
            errored = true;
        }
    }

    public void initDataSource() {
        if (dataSource == null || dataSource.isClosed()) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + database
                    + "?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true");
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(15);

            dataSource = new HikariDataSource(config);
        }
    }

    public void customConnection(String database, String username, String password, String ip, String port) {
        Logging.warnToConsole("USING CUSTOM CONNECTION");
        this.database = database;
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
        initDataSource();
    }

    /**
     * Connects to the MySQL database.
     */
    public boolean connect() {

        if (errored) {
            return false;
        }

        try {
            connection = dataSource.getConnection();

            if (connection != null) {

                Logging.infoToConsole("MySQL connection established.");
                return true;
            }

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO CONNECT.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());
            Logging.errorToConsole("USERNAME: " + username);
            Logging.errorToConsole("DATABASE: " + database);
            Logging.errorToConsole("IP: " + ip);
            Logging.errorToConsole("PORT: " + port);
            errored = true;

        }

        errored = true;
        return false;

    }

    public void reconnect() {

        try {

            Logging.warnToConsole("Attempting to close the connection...");
            connection.close();
            Logging.warnToConsole("Connection closed.");

            Logging.warnToConsole("Attempting to reconnect...");
            connection = dataSource.getConnection();
            Logging.warnToConsole("Reconnected to SQL Server.");

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO RECONNECT.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

            // return null;
        }
    }

    public void closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Logging.infoToConsole("MySQL connection closed.");
            }
        } catch (SQLException e) {
            Logging.errorToConsole("MySQL connection could not be closed.");
            Logging.errorToConsole("FAILED TO CLOSE CONNECTION.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());
        }
    }

    public void closeDataSource() {

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            Logging.infoToConsole("MySQL data source closed.");
        }
    }

    /**
     * @return the connection
     */
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
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());
            return null;
        }

    }

    public boolean isErrored() {
        return errored;
    }

    private boolean validateFields() {

        int caught = 0;

        if (database == null) {

            Logging.errorToConsole("VALUE IN MySQL.database IS NULL.");

            caught++;

        }

        if (ip == null) {

            Logging.errorToConsole("VALUE IN MySQL.ip IS NULL.");

            caught++;

        }

        if (username == null) {

            Logging.errorToConsole("VALUE IN MySQL.username IS NULL.");

            caught++;

        }

        if (password == null) {

            Logging.errorToConsole("VALUE IN MySQL.password IS NULL.");

            caught++;

        }

        if (port == null) {

            Logging.errorToConsole("VALUE IN MySQL.port IS NULL.");

            caught++;

        }

        if (caught > 0) {

            return false;
        }

        return true;

    }

}
