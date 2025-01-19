package dev.boooiil.historia.core.database;

import java.sql.Connection;

public interface IDatabaseConnection {

    // TODO: voids become boolean

    public enum DatabaseType {
        MYSQL("mysql"),
        SQLITE("sqlite"),
        UNKNOWN("unknown");

        private final String key;

        DatabaseType(String key) {

            this.key = key;

        }

        public String getKey() {

            return this.key;

        }

        public static DatabaseType fromString(String key) {

            for (DatabaseType type : DatabaseType.values()) {

                if (type.getKey().equalsIgnoreCase(key)) {

                    return type;

                }

            }

            return UNKNOWN;

        }

    }

    public Connection getConnection();

    /**
     * Get the type of the current database.
     * 
     * @return {@link DatabaseType}
     */
    public DatabaseType getDatabaseType();

    /**
     * Attempt to create a connection that will be used for this instance.
     * 
     * @return true if no error
     */
    public boolean connect();

    /**
     * If there has been an error during init or connection.
     * 
     * @return true if an error
     */
    public boolean isErrored();

    /**
     * close the stored connection.
     */
    public void closeConnection();

    /**
     * Close the stored data source.
     */
    public void closeDataSource();

    /**
     * Attempt to establish a new connection to the database.
     */
    public void reconnect();

    /**
     * Attempt to initialize the data source with the provided credentials.
     */
    public void initDataSource();

}
