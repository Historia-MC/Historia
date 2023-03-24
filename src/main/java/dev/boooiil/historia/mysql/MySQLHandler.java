package dev.boooiil.historia.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dev.boooiil.historia.Main;
import dev.boooiil.historia.configuration.GeneralConfig;
import dev.boooiil.historia.configuration.GeneralConfig.MySQL;
import dev.boooiil.historia.util.Logging;

/**
 * It's a class that handles all of the MySQL queries for the plugin.
 */
public class MySQLHandler {

    // TODO: Do below
    // We could create a user cache that loads when we first connect to the database so we are not fetching users every time they connect.

    private static final MySQL MYSQLCONFIG = new GeneralConfig.MySQL();

    private static final String DATABASE = MYSQLCONFIG.database;
    private static final String USERNAME = MYSQLCONFIG.username;
    private static final String PASSWORD = MYSQLCONFIG.password;
    private static final String IP = MYSQLCONFIG.ip;
    private static final String PORT = MYSQLCONFIG.port;

    // Create a URL that we will use to connect to the MySQL database.
    static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE
            + "?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true";

    private static Connection connection;

    /**
     * Create the table in the database if it does not exist.
     * 
     */
    public static void createTable() {

        if (validateFields()) {

            // Issue the statement that we will use to create the table if it does not
            // exist.
            String createTable = "CREATE TABLE IF NOT EXISTS " + 
                "historia(UUID varchar(36), " +
                "Username varchar(16), " +
                "Class varchar(30), " +
                "Level int, " + 
                "Experience int, " +
                "Login bigint, " + 
                "Logout bigint, " +
                "Playtime bigint, " +
                "PRIMARY KEY (UUID))";

            try {

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                // Connect to the database and assign that connection to "connection".

                Statement statement = connection.createStatement();
                statement.execute(createTable);

            } catch (SQLException e) {
                Logging.infoToConsole("Failed to load MySQL.");
                e.printStackTrace();
                Main.disable(Main.plugin());
            }
        }
    }

    /**
     * Create the user in the database.
     * 
     * @param uuid       - UUID of the player.
     * @param playerName - Name of the player.
     */

    public static void createUser(UUID uuid, String playerName) {

        if (userExists(uuid)) return;

        try {

            String createUser = "INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'None', 1, 0, "
                    + new Date().getTime() + ", 0, 0)";

            Statement statement = connection.createStatement();
            statement.execute(createUser);

        } 
        catch(CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");
            cE.printStackTrace();

        }
        catch (SQLException e) {

            e.printStackTrace();

        }

    }

    /**
     * Set the username for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setUsername(UUID uuid, String playerName) {

        try {

            String string = ("UPDATE historia SET Username = '" + playerName + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

            Logging.infoToConsole("Created user: " + playerName + " in the Database.");

        } catch (SQLException e) {
            e.printStackTrace();

            Logging.errorToConsole("FAILED TO CREATE USER.");
        }

    }

    /**
     * Set the class name for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setClass(UUID uuid, String className) {

        try {

            String string = ("UPDATE historia SET Class = '" + className + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Set the class level for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setClassLevel(UUID uuid, int classLevel) {

        try {

            String string = ("UPDATE historia SET Level = '" + classLevel + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Set the login time for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setLogin(UUID uuid) {

        try {

            String string = ("UPDATE historia SET Login = '" + new Date().getTime() + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } 
        catch(CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");
            cE.printStackTrace();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Set the logout time for the given user.
     * 
     * @param uuid - UUID of the player.
     * @param lastLogin - Provided last login of the player.
     * @param previousPlaytime - Provided playtime of the player.
     */

    public static void setLogout(UUID uuid, long lastLogin, long previousPlaytime) {

        try {

            long time = new Date().getTime();

            String string = (
                            "UPDATE historia " +
                            "SET Logout = '" + time + "', " +
                            "Playtime = '" + ((time - lastLogin) + previousPlaytime) + "' " +
                            "WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get a list of usernames from the database.
     *
     * @return List of usernames.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public static List<String> getUsernames() {

        String string = "SELECT Username FROM historia";
        List<String> answer = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(results.getString("Username"));

            }

        } 
        catch(CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");
            cE.printStackTrace();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return answer;

    }

    /**
     * Get the username with a given UUID.
     * 
     * @param uuid - UUID of the player.
     * @return Username of the player.
     */

    public static String getUsername(UUID uuid) {

        String string = "SELECT Username FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            results.next();

            return results.getString(1);

        } 
        catch(CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");
            cE.printStackTrace();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Get all user information from the database.
     * 
     * @param uuid - UUID of the player.
     *
     * @return
     *         <p>
     *         "UUID", {@link java.lang.String String}
     *         <p>
     *         "Username", {@link java.lang.String String}
     *         <p>
     *         "Class", {@link java.lang.String String}
     *         <p>
     *         "Level", {@link java.lang.String String}
     *         <p>
     *         "Experience", {@link java.lang.String String}
     *         <p>
     *         "Login", {@link java.lang.String String}
     *         <p>
     *         "Logout", {@link java.lang.String String}
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/Map.html">Map</a>
     */

    public static Map<String, String> getUser(UUID uuid) {

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Map<String, String> map = new HashMap<>();

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                map.put("UUID", results.getString("UUID"));
                map.put("username", results.getString("Username"));
                map.put("class", results.getString("Class"));
                map.put("level", results.getString("Level"));
                map.put("experience", results.getString("Experience"));
                map.put("login", results.getString("Login"));
                map.put("logout", results.getString("Logout"));
                map.put("playtime", results.getString("Playtime"));

            }

            return map;

        } 
        catch(CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");
            cE.printStackTrace();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Get a list of UUIDs from the database.
     *
     * @return List of UUIDs.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html">UUID</a>
     */

    public static List<UUID> getUUIDs() {

        String string = "SELECT UUID FROM historia";
        List<UUID> answer = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(UUID.fromString(results.getString("UUID")));

            }

        } 
        catch(CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");
            cE.printStackTrace();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return answer;

    }

    /**
     * Get a specific UUID from the database using a username.
     * 
     * @param playerName - Name of the player.
     *
     * @return UUID of the given username.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html">UUID</a>
     */

    public static UUID getUUID(String playerName) {

        String string = "SELECT UUID FROM historia WHERE Username = '" + playerName + "'";

        Logging.debugToConsole("Query String: " + string);

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            Logging.debugToConsole("SQL Result: " + results);

            if (!results.next()) {

                return getUUID("null");

            }

            else {

                Logging.debugToConsole("Next SQL Result: " + results);

                return UUID.fromString(results.getString(1));

            }

        } 
        catch(CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");
            
            try {

                return UUID.fromString(reconnectOnStale(string).getString(1));

            }
            catch (Exception e) {

                e.printStackTrace();

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Deprecated
    // Checking if the fields are empty.
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

            Main.disable(Main.plugin());

            return false;
        }

        return true;

    }

    /**
     * It checks if the user exists in the database
     * 
     * @param uuid The UUID of the player
     * @return A boolean value.
     */
    private static boolean userExists(UUID uuid) {

        String statement = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            return connection.createStatement().executeQuery(statement).next();

        } catch (SQLException sqlE) {

            sqlE.printStackTrace();

        }
    
        return false;

    }

    /*
     * Reconnects the client on a stale request.
     */
    private static ResultSet reconnectOnStale(String query) {

        try {

            Logging.warnToConsole("Attempting to close the connection...");
            connection.close();
            Logging.warnToConsole("Connection closed.");

            Logging.warnToConsole("Attempting to reconnect...");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Logging.warnToConsole("Reconnected to SQL Server.");

            Logging.warnToConsole("Resending query...");

            ResultSet result = connection.createStatement().executeQuery(query);

            result.next();
            
            return result;

        } catch (SQLException sqlE) {

            Logging.errorToConsole("Could not reconnect on stale connection.");
            sqlE.printStackTrace();

            return null;
        }
    }

    // A private constructor.
    private MySQLHandler() {
        throw new IllegalStateException("This class should not be initialized.");
    }
}