package dev.boooiil.historia.core.configuration.specific;

import dev.boooiil.historia.core.classes.enums.file.FileKeys;
import dev.boooiil.historia.core.util.FileIO;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * It's a class that grabs the configuration file and stores it in a variable.
 */
public class GeneralConfig {
    
    public static YamlConfiguration configuration = FileIO.get(FileKeys.CONFIG);

    public static boolean debug;

    public String username;
    public String password;
    public String database;
    public String ip;
    public String port;

    public GeneralConfig() {

        System.out.print(configuration.toString());

        this.username = configuration.getString("MySQL.user");
        this.password = configuration.getString("MySQL.password");
        this.database = configuration.getString("MySQL.database");
        this.ip = configuration.getString("MySQL.ip");
        this.port = configuration.getString("MySQL.port");
        
        debug = configuration.getBoolean("debug");

    }

}
