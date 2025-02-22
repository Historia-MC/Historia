package dev.boooiil.historia.core.database.mysql;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.database.DatabaseConnection;
import dev.boooiil.historia.core.util.CoreLogger;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * MySQL database connection handler for Historia-Core.
 */
@NullMarked
public class MySQLConnection extends DatabaseConnection {

    /**
     * Name of the database.
     */
    private final @Nullable String database = ConfigurationLoader.getGeneralConfig().database;

    /**
     * Username of the MySQL database.
     */
    private final @Nullable String username = ConfigurationLoader.getGeneralConfig().username;

    /**
     * Password of the MySQL database.
     */
    private final @Nullable String password = ConfigurationLoader.getGeneralConfig().password;

    /**
     * IP address of the MySQL server.
     */
    private final @Nullable String ip = ConfigurationLoader.getGeneralConfig().ip;

    /**
     * Port of the MySQL server.
     */
    private final @Nullable String port = ConfigurationLoader.getGeneralConfig().port;

    public MySQLConnection() {
        errored = !validateFields();
    }

    /**
     * Get the type of database.
     */
    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    /**
     * Initialize the data source. This is essentially building the connection to
     * the database.
     */
    @Override
    public boolean initDataSource() {
        if (dataSource == null || dataSource.isClosed()) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + database
                    + "?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true");
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(30);
            config.setLeakDetectionThreshold(2000);

            dataSource = new HikariDataSource(config);
        }

        return true;
    }

    /**
     * Validate if the MySQL fields are present.
     * 
     * @return true if all fields are present, false otherwise.
     */
    private boolean validateFields() {

        int caught = 0;

        if (database == null) {

            CoreLogger.errorToConsole("VALUE IN MySQL.database IS NULL.");

            caught++;

        }

        if (ip == null) {

            CoreLogger.errorToConsole("VALUE IN MySQL.ip IS NULL.");

            caught++;

        }

        if (username == null) {

            CoreLogger.errorToConsole("VALUE IN MySQL.username IS NULL.");

            caught++;

        }

        if (password == null) {

            CoreLogger.errorToConsole("VALUE IN MySQL.password IS NULL.");

            caught++;

        }

        if (port == null) {

            CoreLogger.errorToConsole("VALUE IN MySQL.port IS NULL.");

            caught++;

        }

        if (caught > 0) {

            return false;
        }

        return true;

    }

}
