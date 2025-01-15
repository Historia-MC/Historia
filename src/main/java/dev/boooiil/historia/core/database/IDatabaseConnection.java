package dev.boooiil.historia.core.database;

import java.sql.Connection;

public interface IDatabaseConnection {

    public enum DatabaseType {
        MYSQL,
        SQLITE
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
