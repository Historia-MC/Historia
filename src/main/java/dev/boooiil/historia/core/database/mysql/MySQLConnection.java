package dev.boooiil.historia.core.database.mysql;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.database.DatabaseConnection;
import dev.boooiil.historia.core.util.Logging;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLConnection extends DatabaseConnection {

    private String database = ConfigurationLoader.getGeneralConfig().database;
    private String username = ConfigurationLoader.getGeneralConfig().username;
    private String password = ConfigurationLoader.getGeneralConfig().password;
    private String ip = ConfigurationLoader.getGeneralConfig().ip;
    private String port = ConfigurationLoader.getGeneralConfig().port;

    public MySQLConnection() {
        errored = !validateFields();
    }

    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

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

    private boolean validateFields() {

        int caught = 0;

        if (database == null) {

            Logging.errorToConsole("VALUE IN MySQL.database IS NULL.");

            caught++;

        }

        if (ip == null) {

            Logging.errorToConsole("VALUE IN MySQL.ip IS NULL.");

            caught++;

        }

        if (username == null) {

            Logging.errorToConsole("VALUE IN MySQL.username IS NULL.");

            caught++;

        }

        if (password == null) {

            Logging.errorToConsole("VALUE IN MySQL.password IS NULL.");

            caught++;

        }

        if (port == null) {

            Logging.errorToConsole("VALUE IN MySQL.port IS NULL.");

            caught++;

        }

        if (caught > 0) {

            return false;
        }

        return true;

    }

}
