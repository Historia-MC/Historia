package dev.boooiil.historia.core.database.sql;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.configuration.specific.GeneralConfig;

public class DataSourceProvider implements AutoCloseable {

    private final HikariDataSource mysqlDataSource;
    private final HikariDataSource sqliteDataSource;
    private DatabaseType activeDatabaseType;

    public DataSourceProvider() {

        GeneralConfig generalConfig = ConfigurationLoader.getGeneralConfig();

        if (generalConfig.databaseType == DatabaseType.MYSQL && validateMySQLFields()) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(
                    "jdbc:mysql://" + generalConfig.ip + ":" + generalConfig.port + "/" + generalConfig.database);
            config.setUsername(generalConfig.username);
            config.setPassword(generalConfig.password);

            activeDatabaseType = DatabaseType.MYSQL;
            mysqlDataSource = new HikariDataSource(config);
            sqliteDataSource = null;
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlite:historia.db");

            activeDatabaseType = DatabaseType.SQLITE;
            sqliteDataSource = new HikariDataSource(config);
            mysqlDataSource = null;
        }

    }

    public HikariDataSource getActiveDataSource() {
        return switch (activeDatabaseType) {
            case MYSQL -> mysqlDataSource;
            case SQLITE -> sqliteDataSource;
            default -> throw new IllegalStateException("Unimplemented connection type value: " + activeDatabaseType);
        };
    }

    public DatabaseType getActiveDatabaseType() {
        return activeDatabaseType;
    }

    public void setActiveDatabaseType(DatabaseType databaseType) {
        this.activeDatabaseType = databaseType;
    }

    private boolean validateMySQLFields() {
        GeneralConfig generalConfig = ConfigurationLoader.getGeneralConfig();
        return generalConfig.username != null && !generalConfig.username.isEmpty()
                && generalConfig.password != null && !generalConfig.password.isEmpty()
                && generalConfig.database != null && !generalConfig.database.isEmpty()
                && generalConfig.ip != null && !generalConfig.ip.isEmpty()
                && generalConfig.port != null && !generalConfig.port.isEmpty();
    }

    @Override
    public void close() {
        if (mysqlDataSource != null)
            mysqlDataSource.close();
        if (sqliteDataSource != null)
            sqliteDataSource.close();
    }
}
