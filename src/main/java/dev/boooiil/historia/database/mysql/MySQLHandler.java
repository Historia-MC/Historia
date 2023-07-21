package dev.boooiil.historia.database.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dev.boooiil.historia.util.Logging;

/**
 * It's a class that handles all of the MySQL queries for the plugin.
 */
public class MySQLHandler {

    private static Connection connection = MySQLConnection.getConnection();

    /**
     * Create the table in the database if it does not exist.
     * 
     */
    public static void createTable() {

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
            e.printStackTrace();

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setUsername(uuid, playerName);

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
            e.printStackTrace();
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

            String string = ("UPDATE historia SET Login = '" + System.currentTimeMillis() + "' WHERE UUID = '" + uuid + "'");

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            setLogin(uuid);;

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();

            Logging.errorToConsole("FAILED TO CREATE USER.");
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
            setLogout(uuid, lastLogin, previousPlaytime);;

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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUsernames();

        } catch (Exception e) {
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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUsername(uuid);

        } catch (Exception e) {
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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUser(uuid);

        } catch (Exception e) {
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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUUIDs();

        } catch (Exception e) {
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

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUUID(playerName);

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (SQLException sqlE) {

            sqlE.printStackTrace();

        }

        return false;

    }

    // A private constructor.
    private MySQLHandler() {
        throw new IllegalStateException("This class should not be initialized.");
    }
}