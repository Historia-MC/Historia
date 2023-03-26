package dev.boooiil.historia.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.classes.Configuration;
import dev.boooiil.historia.util.FileGetter;

/**
 * It's a class that grabs the configuration file and stores it in a variable.
 */
public class GeneralConfig extends Configuration<Object> {
    
    public static boolean debug;

    public String username;
    public String password;
    public String database;
    public String ip;
    public String port;

    public GeneralConfig() {

        super("config.yml");

        System.out.print(configuration.toString());

        this.username = configuration.getString("MySQL.user");
        this.password = configuration.getString("MySQL.password");
        this.database = configuration.getString("MySQL.database");
        this.ip = configuration.getString("MySQL.ip");
        this.port = configuration.getString("MySQL.port");
        
        debug = configuration.getBoolean("debug");

    }

    @Override
    public Object createNew(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNew'");
    }

    @Override
    public Object getObject(String objectName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObject'");
    }

}
