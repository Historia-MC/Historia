package dev.boooiil.historia.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

public class GeneralConfig {

    private static FileConfiguration configuration = FileGetter.get("config.yml");

    /**
     * MySQL Configuration Class for grabbing MySQL credentials
     */
    public static class MySQL {

        public static String username;
        public static String password;
        public static String database;
        public static String ip;
        public static String port;

        MySQL() {

            MySQL.username = configuration.getString("MySQL.user");
            MySQL.password = configuration.getString("MySQL.password");
            MySQL.database = configuration.getString("MySQL.database");
            MySQL.ip = configuration.getString("MySQL.ip");
            MySQL.port = configuration.getString("MySQL.port");

        }

    }

}
