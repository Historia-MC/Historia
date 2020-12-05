package dev.boooiil.historia.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dev.boooiil.historia.Config;

public class MySQL {

    private Connection connection;

    private Statement statement;

    private static Config sql = new Config("MySQL");

    //Assign variables that we will use to connect to the database.
    static final String DATABASE = sql.database;
    static final String USERNAME = sql.username;
    static final String PASSWORD = sql.password;
    static final String IP = sql.ip;
    static final Integer PORT = sql.port;

    //Create a URL that we will use to connect to the MySQL database.
    static final String URL = "jbdc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public void initiate() throws Exception {

        //Issue the statement that we will use to create the table if it does not exist.
        String createTable = "CREATE TABLE IF NOT EXISTS historia(UUID varchar(36), Username varchar(16), Class varchar(30), Level int(100), Experience int(10000))";

        System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
        //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        //Try to prepare the statement and issue it to the database.
        //try (PreparedStatement create = connection.prepareStatement(createTable)) {

        //Connect to the database and assign that connection to "connection".
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        statement = connection.createStatement();
        statement.executeQuery(createTable);
            
        //Execute the prepared statement.
        //create.executeQuery();

        //Close out of the connection with the database.
        connection.close();

        //}
    }

    public void doStatement(String string) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(string)) {

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement.executeQuery();

            connection.close();

        }
    }

    public ResultSet getStatement(String string) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(string)) {
            
            ResultSet results;

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            results = statement.executeQuery();
            connection.close();    

            return results; 
            
        }
    }
}