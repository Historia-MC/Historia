package dev.boooiil.historia.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dev.boooiil.historia.Config;

public class MySQL {
    
    static Connection connection;

    public static void intitate() {

        final String database = new Config("MySQL").database;
        final String username = new Config("MySQL").username;
        final String password = new Config("MySQL").password;
        final String ip = new Config("MySQL").ip;
        final Integer port = new Config("MySQL").port;
    
        final String url = "jbdc:mysql://" + ip + ":" + port + "/" + database;

        String createTable = "CREATE TABLE IF NOT EXISTS historia(UUID varchar(36), Username varchar(16), Class varchar(30), Level int(100), Experience int(10000))";
        String fetchData = "SELECT WHERE * IN historia";
        
        try {

            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement create = connection.prepareStatement(createTable);
            create.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public static void doStatement() {

    }

    public static String getStatement() {


        return "string";
    }
    

}
