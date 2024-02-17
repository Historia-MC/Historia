package dev.boooiil.historia.core.database.mysql;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dev.boooiil.historia.core.util.Logging;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * It's a class that handles all the MySQL queries for the plugin.
 */
public class MySQLHandler {

    private final static Connection connection = MySQLConnection.getConnection();

    /**
     * Create the table in the database if it does not exist.
     * 
     */
    public static void createTable() {

        if (connection == null) {
            Logging.errorToConsole("FAILED TO CREATE TABLE. CONNECTION IS NULL.");
            return;
        }

        try {
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

            Statement statement = connection.createStatement();
            statement.execute(createTable);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            createTable();

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO CREATE TABLE.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }
    }

    /**
     * Create the user in the database.
     * 
     * @param uuid       - UUID of the player.
     * @param playerName - Name of the player.
     */

    public static void createUser(UUID uuid, String playerName) {

        if (userExists(uuid))
            return;

        try {

            String createUser = "INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'None', 1, 0, "
                    + System.currentTimeMillis() + ", 0, 0)";

            Statement statement = connection.createStatement();
            statement.execute(createUser);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            createUser(uuid, playerName);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO CREATE USER.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setUsername(uuid, playerName);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SET USERNAME.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }

    }

    /**
     * Set the class name for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setProficiency(UUID uuid, String className) {

        try {

            String string = ("UPDATE historia SET Class = '" + className + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setProficiency(uuid, className);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SET PROFICIENCY.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }

    }

    /**
     * Set the class level for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setProficiencyLevel(UUID uuid, int classLevel) {

        try {

            String string = ("UPDATE historia SET Level = '" + classLevel + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setProficiencyLevel(uuid, classLevel);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SET PROFICIENCY LEVEL.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }

    }

    /**
     * Set the login time for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setLogin(UUID uuid) {

        try {

            String string = ("UPDATE historia SET Login = '" + System.currentTimeMillis() + "' WHERE UUID = '" + uuid
                    + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setLogin(uuid);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SET LOGIN.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }

    }

    /**
     * Set the current experience for the given user.
     * 
     * @param uuid       - UUID of the player.
     * @param experience - Provided experience of the player.
     */

    public static void setCurrentExperience(UUID uuid, double experience) {

        try {

            String string = ("UPDATE historia SET Experience = '" + experience + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setCurrentExperience(uuid, experience);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SET EXPERIENCE.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }

    }

    /**
     * Set the logout time for the given user.
     * 
     * @param uuid             - UUID of the player.
     * @param lastLogin        - Provided last login of the player.
     * @param previousPlaytime - Provided playtime of the player.
     */

    public static void setLogout(UUID uuid, long lastLogin, long previousPlaytime) {

        try {

            long time = System.currentTimeMillis();

            String string = ("UPDATE historia " +
                    "SET Logout = '" + time + "', " +
                    "Playtime = '" + ((time - lastLogin) + previousPlaytime) + "' " +
                    "WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setLogout(uuid, lastLogin, previousPlaytime);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SET LOGOUT.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUsernames();

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO GET USERNAMES.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUsername(uuid);

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO GET USERNAME.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

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

    public static Map<MySQLUserKeys, String> getUser(UUID uuid) {

        Map<MySQLUserKeys, String> map = new HashMap<>();

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                map.put(MySQLUserKeys.UUID, results.getString("UUID"));
                map.put(MySQLUserKeys.USERNAME, results.getString("Username"));
                map.put(MySQLUserKeys.CLASS, results.getString("Class"));
                map.put(MySQLUserKeys.LEVEL, results.getString("Level"));
                map.put(MySQLUserKeys.EXPERIENCE, results.getString("Experience"));
                map.put(MySQLUserKeys.LOGIN, results.getString("Login"));
                map.put(MySQLUserKeys.LOGOUT, results.getString("Logout"));
                map.put(MySQLUserKeys.PLAYTIME, results.getString("Playtime"));

            }

            return map;

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUser(uuid);

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO GET USER.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

            map.put(MySQLUserKeys.UUID, uuid.toString());
            map.put(MySQLUserKeys.USERNAME, "null");
            map.put(MySQLUserKeys.CLASS, "None");
            map.put(MySQLUserKeys.LEVEL, "1");
            map.put(MySQLUserKeys.EXPERIENCE, "0");
            map.put(MySQLUserKeys.LOGIN, "0");
            map.put(MySQLUserKeys.LOGOUT, "0");
            map.put(MySQLUserKeys.PLAYTIME, "0");

        }

        return map;

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUUIDs();

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO GET UUIDS.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUUID(playerName);

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO GET UUID.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }

        return null;

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return userExists(uuid);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SET PROFICIENCY.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }

        return false;

    }

    // A private constructor.
    private MySQLHandler() {
        throw new IllegalStateException("This class should not be initialized.");
    }
}