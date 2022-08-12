package dev.boooiil.historia.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

public class GeneralConfig {

    private static FileConfiguration configuration = FileGetter.get("config.yml");

    /**
     * MySQL Configuration Class for grabbing MySQL credentials
     */
    public class MySQL {

        public String username;
        public String password;
        public String database;
        public String ip;
        public String port;

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
