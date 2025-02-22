package dev.boooiil.historia.core.database.sqlite;

import org.jspecify.annotations.NullMarked;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.DatabaseConnection;
import dev.boooiil.historia.core.util.CoreLogger;

/**
 * SQLite database connection handler for Historia-Core.
 */
@Deprecated(forRemoval = false)
@NullMarked
public class SQLiteConnection extends DatabaseConnection {

    public SQLiteConnection() {
    }

    /**
     * Get the type of database.
     */
    public DatabaseType getDatabaseType() {
        return DatabaseType.SQLITE;
    }

    /**
     * Initialize the data source. This is essentially building the connection to
     * the database.
     */
    @Override
    public boolean initDataSource() {
        if (dataSource == null || dataSource.isClosed()) {
            CoreLogger.infoToConsole("(SQLite) Initializing data source.");
            CoreLogger.infoToConsole(
                    "(SQLite) Data source location: " + HistoriaCore.plugin().getDataFolder().getAbsolutePath()
                            + "/database.db");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(
                    "jdbc:sqlite:" + HistoriaCore.plugin().getDataFolder().getAbsolutePath() + "/database.db");
            config.setMaximumPoolSize(150); // Set max pool size

            dataSource = new HikariDataSource(config);
        }

        return true;
    }

}
