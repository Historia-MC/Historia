package dev.boooiil.historia.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import dev.boooiil.historia.Config;

public class MySQL {

    private static Config config = new Config();

    private static Map<String, String> sql = config.getMySQLInfo();

    // Assign variables that we will use to connect to the database.
    private static final String DATABASE = sql.get("DATABASE");
    private static final String USERNAME = sql.get("USER");
    private static final String PASSWORD = sql.get("PASSWORD");
    private static final String IP = sql.get("IP");
    private static final String PORT = sql.get("PORT");

    // Create a URL that we will use to connect to the MySQL database.
    static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?useSSL=false";

    public void createTable() throws SQLException {

        // Issue the statement that we will use to create the table if it does not
        // exist.
        String createTable = "CREATE TABLE IF NOT EXISTS historia(UUID varchar(36), Username varchar(16), Class varchar(30), Level int, Experience int, Login bigint, Logout bigint, PRIMARY KEY (UUID))";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // Connect to the database and assign that connection to "connection".

            Statement statement = connection.createStatement();
            statement.execute(createTable);

        }
    }

    public static void createUser(UUID uuid, String playerName) {

        try {

            String createUser = "INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'None', 1, 0, " + new Date().getTime() + ", 0)";

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            statement.execute(createUser);

        } catch (SQLException e) { e.printStackTrace(); }

    }

    public static void setUsername(UUID uuid, String playerName) {

        try {

            String string = ("UPDATE historia SET Username = '" + playerName + "' WHERE UUID = '" + uuid + "'");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setClass(UUID uuid, String className) {

        try {

            String string = ("UPDATE historia SET Class = '" + className + "' WHERE UUID = '" + uuid + "'");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setClassLevel(UUID uuid, int classLevel) {

        try {

            String string = ("UPDATE historia SET Level = '" + classLevel + "' WHERE UUID = '" + uuid + "'");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setLogin(UUID uuid) {

        try {

            String string = ("UPDATE historia SET Login = '" + new Date().getTime() + "' WHERE UUID = '" + uuid + "'");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setLogout(UUID uuid) {

        try {

            String string = ("UPDATE historia SET Logout = '" + new Date().getTime() + "' WHERE UUID = '" + uuid + "'");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            statement.execute(string);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static  List<String> getUsernames() {

        String string = "SELECT Username FROM historia";
        List<String> answer = new ArrayList<>();

        try {

            // Connect to the database and assign that connection to "connection".
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(results.getString("Username"));

            }

        } catch (Exception e) { e.printStackTrace(); }

        return answer;

    } 
    
    public static String getUsername(UUID uuid) {

        String string = "SELECT Username FROM historia WHERE UUID = '" + uuid + "'";

        try {

            // Connect to the database and assign that connection to "connection".
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            results.next();

            return results.getString(1);

        } catch (Exception e) { e.printStackTrace(); }

        return null;

    }

    public static Map<String, String> getUser(UUID uuid) {
        

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Map<String, String> map = new HashMap<>();

            //Connect to the database and assign that connection to "connection".
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

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

        } catch (Exception e) { e.printStackTrace(); }

        return null;

    }

    public static List<UUID> getUUIDs() {

        String string = "SELECT UUID FROM historia";
        List<UUID> answer = new ArrayList<>();

        try {

            // Connect to the database and assign that connection to "connection".
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(UUID.fromString(results.getString("UUID")));

            }

        } catch (Exception e) { e.printStackTrace(); }

        return answer;

    }

    public static  UUID getUUID(String playerName) {

        String string = "SELECT UUID FROM historia WHERE Username = '" + playerName + "'";

        try {

            // Connect to the database and assign that connection to "connection".
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            results.next();

            return UUID.fromString(results.getString(1));

        } catch (Exception e) { e.printStackTrace(); }

        return null;

    }
    
}