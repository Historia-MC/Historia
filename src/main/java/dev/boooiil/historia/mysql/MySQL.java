package dev.boooiil.historia.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dev.boooiil.historia.Config;

public class MySQL {

    //private Connection connection;

    private static Config sql = new Config("MySQL");

    //Assign variables that we will use to connect to the database.
    static final String DATABASE = sql.database;
    static final String USERNAME = sql.username;
    static final String PASSWORD = sql.password;
    static final String IP = sql.ip;
    static final Integer PORT = sql.port;

    //Create a URL that we will use to connect to the MySQL database.
    static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public void initiate() throws SQLException {

        //Issue the statement that we will use to create the table if it does not exist.
        String createTable = "CREATE TABLE IF NOT EXISTS historia(UUID varchar(36), Username varchar(16), Class varchar(30), Level int, Experience int, PRIMARY KEY (UUID))";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            //Connect to the database and assign that connection to "connection".
            
            Statement statement = connection.createStatement();
            statement.execute(createTable);

        }
    }

    public void doStatement(String string) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            //Connect to the database and assign that connection to "connection".
            
            Statement statement = connection.createStatement();
            statement.execute(string);

        }
    }

    public ResultSet getStatement(String string) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            ResultSet results;

            //Connect to the database and assign that connection to "connection".
            
            Statement statement = connection.createStatement();
            results = statement.executeQuery(string);
            
            return results;

        }
    }
}