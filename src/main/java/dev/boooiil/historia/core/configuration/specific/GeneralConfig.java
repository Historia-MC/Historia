package dev.boooiil.historia.core.configuration.specific;

import dev.boooiil.historia.core.database.sql.DatabaseType;
import dev.boooiil.historia.core.file.FileIO;
import dev.boooiil.historia.core.file.FileKeys;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Configuration storage class holding the GeneralConfig (config.yml).
 */
@NullMarked
public class GeneralConfig {

    /**
     * The YAML configuration object for the config.yml file.
     */
    public static YamlConfiguration configuration = FileIO.get(FileKeys.CONFIG);

    /**
     * Debugging flag.
     */
    public static boolean debug = false;
    public static boolean verbose = false;
    public static boolean trace = false;

    /**
     * Username of the MYSQL database.
     */

    public final @Nullable String username;

    /**
     * Password of the MYSQL database.
     */

    public final @Nullable String password;

    /**
     * Name of the database.
     */

    public final @Nullable String database;

    /**
     * IP of the database.
     */

    public final @Nullable String ip;

    /**
     * Port of the database.
     */

    public final @Nullable String port;

    /**
     * Type of database to use (MySQL, SQLite, etc)
     */
    public final DatabaseType databaseType;

    public GeneralConfig() {

        System.out.print(configuration);

        this.username = configuration.getString("MySQL.user");
        this.password = configuration.getString("MySQL.password");
        this.database = configuration.getString("MySQL.database");
        this.ip = configuration.getString("MySQL.ip");
        this.port = configuration.getString("MySQL.port");
        this.databaseType = DatabaseType.fromString(configuration.getString("type"));

        debug = configuration.getBoolean("debug", false);
        verbose = configuration.getBoolean("verbose", false);
        trace = configuration.getBoolean("trace", false);

    }

}
