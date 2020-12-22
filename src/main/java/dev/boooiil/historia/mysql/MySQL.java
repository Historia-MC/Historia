package dev.boooiil.historia.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void initiate() throws SQLException {

        // Issue the statement that we will use to create the table if it does not
        // exist.
        String createTable = "CREATE TABLE IF NOT EXISTS historia(UUID varchar(36), Username varchar(16), Class varchar(30), Level int, Experience int, Login bigint, Logout bigint, PRIMARY KEY (UUID))";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // Connect to the database and assign that connection to "connection".

            Statement statement = connection.createStatement();
            statement.execute(createTable);

        }
    }

    public void doStatement(String string) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // Connect to the database and assign that connection to "connection".

            Statement statement = connection.createStatement();
            statement.execute(string);

        }
    }

    public Map<String, String> getStatement(String string) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            ResultSet results;
            Map<String, String> map = new HashMap<>();

            //Connect to the database and assign that connection to "connection".
            
            Statement statement = connection.createStatement();
            results = statement.executeQuery(string);

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

        }
    }

    public List<String> getUsernames() throws SQLException {

        String string = "SELECT Username FROM historia";
        List<String> answer = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // Connect to the database and assign that connection to "connection".

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(results.getString("Username"));

            }

            return answer;

        }

    } 

    public UUID getUUID(String playerName) throws SQLException {

        String string = "SELECT UUID FROM historia WHERE Username = '" + playerName + "'";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // Connect to the database and assign that connection to "connection".

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            results.next();

            return UUID.fromString(results.getString(1));

        }

    }

    public List<UUID> getUUIDs() throws SQLException {

        String string = "SELECT UUID FROM historia";
        List<UUID> answer = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // Connect to the database and assign that connection to "connection".

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(UUID.fromString(results.getString("UUID")));

            }

            return answer;

        }

    }
}