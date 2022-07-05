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
import java.util.logging.Level;
import java.util.logging.Logger;

import dev.boooiil.historia.Main;
import dev.boooiil.historia.configuration.GeneralConfig.MySQL;
import dev.boooiil.historia.util.Logging;

public class MySQLHandler {

    // Assign variables that we will use to connect to the database.
    private static final String DATABASE = MySQL.database;
    private static final String USERNAME = MySQL.username;
    private static final String PASSWORD = MySQL.password;
    private static final String IP = MySQL.ip;
    private static final String PORT = MySQL.port;
    private static final Logger log = Main.logger();

    // Create a URL that we will use to connect to the MySQL database.
    static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE
            + "?allowPublicKeyRetrieval=true&useSSL=false";

    private static Connection connection;

    private MySQLHandler() {
        throw new IllegalStateException("This class should not be initialized.");
    }

    /**
     * Create the table in the database if it does not exist.
     * 
     * @throws SQLException Generally, if the plugin can't connect to the database.
     */
    public static void createTable() {

        if (validateFields()) {

            // Issue the statement that we will use to create the table if it does not
            // exist.
            String createTable = "CREATE TABLE IF NOT EXISTS historia(UUID varchar(36), Username varchar(16), Class varchar(30), Level int, Experience int, Login bigint, Logout bigint, PRIMARY KEY (UUID))";

            try {

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                // Connect to the database and assign that connection to "connection".

                Statement statement = connection.createStatement();
                statement.execute(createTable);

            } catch (SQLException e) {
                Logging.infoToServer("Failed to load MySQL.");
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

        try {

            String createUser = "INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'None', 1, 0, "
                    + new Date().getTime() + ", 0)";

            Statement statement = connection.createStatement();
            statement.execute(createUser);

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

            log.log(Level.INFO, "Created user: {0} in the Database.", playerName);

        } catch (SQLException e) {
            e.printStackTrace();

            log.severe("FAILED TO CREATE USER.");
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Set the logout time for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public static void setLogout(UUID uuid) {

        try {

            String string = ("UPDATE historia SET Logout = '" + new Date().getTime() + "' WHERE UUID = '" + uuid + "'");

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
     *         <"UUID", {@link java.lang.String String}>
     *         <p>
     *         <"Username", {@link java.lang.String String}>
     *         <p>
     *         <"Class", {@link java.lang.String String}>
     *         <p>
     *         <"Level", {@link java.lang.String String}>
     *         <p>
     *         <"Experience", {@link java.lang.String String}>
     *         <p>
     *         <"Login", {@link java.lang.String String}>
     *         <p>
     *         <"Logout", {@link java.lang.String String}>
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
                map.put("Username", results.getString("Username"));
                map.put("Class", results.getString("Class"));
                map.put("Level", results.getString("Level"));
                map.put("Experience", results.getString("Experience"));
                map.put("Login", results.getString("Login"));
                map.put("Logout", results.getString("Logout"));

            }

            return map;

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

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            results.next();

            return UUID.fromString(results.getString(1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static boolean validateFields() {

        int caught = 0;

        if (DATABASE == null) {

            log.severe("VALUE IN MySQL.database IS NULL.");

            caught++;

        }

        if (IP == null) {

            log.severe("VALUE IN MySQL.ip IS NULL.");

            caught++;

        }

        if (USERNAME == null) {

            log.severe("VALUE IN MySQL.username IS NULL.");

            caught++;

        }

        if (PASSWORD == null) {

            log.severe("VALUE IN MySQL.password IS NULL.");

            caught++;

        }

        if (PORT == null) {

            log.severe("VALUE IN MySQL.port IS NULL.");

            caught++;

        }

        if (caught > 0) {

            Main.disable(Main.plugin());

            return false;
        }

        return true;

    }

}