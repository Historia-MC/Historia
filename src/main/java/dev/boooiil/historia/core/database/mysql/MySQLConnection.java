package dev.boooiil.historia.core.database.mysql;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.configuration.specific.GeneralConfig;
import dev.boooiil.historia.core.util.Logging;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLConnection {

    private static final GeneralConfig MYSQLCONFIG = ConfigurationLoader.getGeneralConfig();

    private static String DATABASE = MYSQLCONFIG.database;
    private static String USERNAME = MYSQLCONFIG.username;
    private static String PASSWORD = MYSQLCONFIG.password;
    private static String IP = MYSQLCONFIG.ip;
    private static String PORT = MYSQLCONFIG.port;

    private static HikariDataSource dataSource;
    private static Connection connection;

    static {
        if (validateFields())
            initDataSource();
    }

    private static void initDataSource() {
        if (dataSource == null || dataSource.isClosed()) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE
                    + "?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true");
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(15);

            dataSource = new HikariDataSource(config);
        }
    }

    public static void customConnection(String database, String username, String password, String ip, String port) {
        Logging.warnToConsole("USING CUSTOM CONNECTION");
        DATABASE = database;
        USERNAME = username;
        PASSWORD = password;
        IP = ip;
        PORT = port;
        initDataSource();
    }

    /**
     * Connects to the MySQL database.
     */
    public static boolean connect() {

        if (!validateFields()) {

            Logging.errorToConsole("MYSQL FIELDS ARE NULL. CHECK THE CONFIGURATION FILE.");
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
            Logging.errorToConsole("USERNAME: " + USERNAME);
            Logging.errorToConsole("DATABASE: " + DATABASE);
            Logging.errorToConsole("IP: " + IP);
            Logging.errorToConsole("PORT: " + PORT);

        }

        return false;

    }

    public static void reconnectOnStale() {

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

    public static void closeConnection() {

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

    public static void closeDataSource() {

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            Logging.infoToConsole("MySQL data source closed.");
        }
    }

    /**
     * @return the connection
     */
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
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());
            return null;
        }

    }

    private static boolean validateFields() {

        int caught = 0;

        if (DATABASE == null) {

            Logging.errorToConsole("VALUE IN MySQL.database IS NULL.");

            caught++;

        }

        if (IP == null) {

            Logging.errorToConsole("VALUE IN MySQL.ip IS NULL.");

            caught++;

        }

        if (USERNAME == null) {

            Logging.errorToConsole("VALUE IN MySQL.username IS NULL.");

            caught++;

        }

        if (PASSWORD == null) {

            Logging.errorToConsole("VALUE IN MySQL.password IS NULL.");

            caught++;

        }

        if (PORT == null) {

            Logging.errorToConsole("VALUE IN MySQL.port IS NULL.");

            caught++;

        }

        if (caught > 0) {

            return false;
        }

        return true;

    }

}
