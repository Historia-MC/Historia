package dev.boooiil.historia.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

/**
 * It's a class that grabs the configuration file and stores it in a variable.
 */
public class GeneralConfig {

    private static FileConfiguration configuration = FileGetter.get("config.yml");

    // It's grabbing the value of the debug key in the config.yml file and storing it in a variable.
    public static boolean debug = configuration.getBoolean("debug");

    /**
     * MySQL Configuration Class for grabbing MySQL credentials
     */
    public static class MySQL {

        // It's a variable that stores the value of the MySQL.user key in the config.yml file.
        public String username;
        // It's a variable that stores the value of the MySQL.password key in the config.yml file.
        public String password;
        // It's a variable that stores the value of the MySQL.database key in the config.yml file.
        public String database;
        // It's a variable that stores the value of the MySQL.ip key in the config.yml file.
        public String ip;
        // It's a variable that stores the value of the MySQL.port key in the config.yml file.
        public String port;

        // It's grabbing the values of the MySQL keys in the config.yml file and storing them in
        // variables.
        public MySQL() {

            System.out.print(configuration.toString());

            this.username = configuration.getString("MySQL.user");
            this.password = configuration.getString("MySQL.password");
            this.database = configuration.getString("MySQL.database");
            this.ip = configuration.getString("MySQL.ip");
            this.port = configuration.getString("MySQL.port");

        }

    }

}
