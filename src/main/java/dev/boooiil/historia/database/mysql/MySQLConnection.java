package dev.boooiil.historia.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dev.boooiil.historia.HistoriaPlugin;
import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.configuration.specific.GeneralConfig;
import dev.boooiil.historia.util.Logging;

public class MySQLConnection {
    
    private static GeneralConfig MYSQLCONFIG = ConfigurationLoader.getGeneralConfig();

    private static final String DATABASE = MYSQLCONFIG.database;
    private static final String USERNAME = MYSQLCONFIG.username;
    private static final String PASSWORD = MYSQLCONFIG.password;
    private static final String IP = MYSQLCONFIG.ip;
    private static final String PORT = MYSQLCONFIG.port;
    
    static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE
    + "?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true";

    private static Connection connection;

    /**
     * Connects to the MySQL database.
     */
    public static void connect() {

        if (!validateFields()) {

            return;

        }

        try {

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (connection != null) {

                Logging.infoToConsole("MySQL connection established.");

            }

        } catch (Exception e) {

            Logging.errorToConsole("MySQL connection failed.");

            e.printStackTrace();

        }

    }

    public static void reconnectOnStale() {

        try {

            Logging.warnToConsole("Attempting to close the connection...");
            connection.close();
            Logging.warnToConsole("Connection closed.");

            Logging.warnToConsole("Attempting to reconnect...");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Logging.warnToConsole("Reconnected to SQL Server.");

            // Logging.warnToConsole("Resending query...");

            // ResultSet result = connection.createStatement().executeQuery(query);

            // result.next();

            // return result;

        } catch (SQLException sqlE) {

            Logging.errorToConsole("Could not reconnect on stale connection.");
            sqlE.printStackTrace();

            // return null;
        }
    }

    public static void closeConnection() {
            
            try {
    
                connection.close();
    
                Logging.infoToConsole("MySQL connection closed.");
    
            } catch (SQLException e) {
    
                Logging.errorToConsole("MySQL connection could not be closed.");
    
                e.printStackTrace();
    
            }
    }

    /**
     * @return the connection
     */
    public static Connection getConnection() {
            
            return connection;
    
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

            HistoriaPlugin.disable();

            return false;
        }

        return true;

    }


}
