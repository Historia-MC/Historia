package dev.boooiil.historia.core.database.sqlite;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.database.DatabaseConnection;
import dev.boooiil.historia.core.util.Logging;

@Deprecated(forRemoval = false)
public class CoreSQLiteConnection extends DatabaseConnection {

    public CoreSQLiteConnection() {
    }

    public DatabaseType getDatabaseType() {
        return DatabaseType.SQLITE;
    }

    @Override
    public boolean initDataSource() {
        if (dataSource == null || dataSource.isClosed()) {
            Logging.infoToConsole("(SQLite) Initializing data source.");
            Logging.infoToConsole("(SQLite) Data source location: " + Main.plugin().getDataFolder().getAbsolutePath()
                    + "/database.db");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlite:" + Main.plugin().getDataFolder().getAbsolutePath() + "/database.db");
            config.setMaximumPoolSize(150); // Set max pool size

            dataSource = new HikariDataSource(config);
        }

        return true;
    }

}
