package dev.boooiil.historia.core.database;

import java.sql.Connection;
import java.sql.ResultSet;

import dev.boooiil.historia.core.database.DatabaseConnection.DatabaseType;

public interface IDatabaseConnection {

    // TODO: voids become boolean

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
    public boolean closeConnection();

    /**
     * Close the stored data source.
     */
    public boolean closeDataSource();

    /**
     * Attempt to establish a new connection to the database.
     */
    public boolean reconnect();

    /**
     * Attempt to initialize the data source with the provided credentials.
     */
    public boolean initDataSource();

    public <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor);

    public <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry);

    public <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry, int curr);

    /*
     * Execute a SQL statement without returning any result.
     * 
     * @param statement The SQL statement to be executed.
     * 
     */
    public void executor(String statement);

    // public <T> T queryExecutor(String statement, IResultProcessor<T>
    // resultProcessor);

    // public <T> T queryExecutor(String statement, IResultProcessor<T>
    // resultProcessor, int maxRetry);

    // public <T> T queryExecutor(String statement, IResultProcessor<T>
    // resultProcessor, int maxRetry, int curr);

    /**
     * This method will execute an update statement with no retry count.
     * 
     * @param statement The SQL statement to be executed.
     * 
     */
    public void updateExecutor(String statement);

    /**
     * This method will attempt to execute the update statement up to maxRetry times
     * before giving up.
     * 
     * @param statement The SQL statement to be executed.
     * @param maxRetry  The maximum number of times to retry the execution.
     * 
     */
    public void updateExecutor(String statement, int maxRetry);

    /**
     * This method will attempt to execute the update statement up to maxRetry times
     * before giving up.
     * 
     * @param statement The SQL statement to be executed.
     * @param maxRetry  The maximum number of times to retry the execution.
     * @param curr      The current retry count.
     * 
     */
    public void updateExecutor(String statement, int maxRetry, int curr);

    /**
     * Get the next result from the result set.
     * 
     * @param result The ResultSet to process.
     * @return true if there is a next result, false otherwise.
     * 
     */
    public boolean nextResult(ResultSet result);

    /**
     * Get a result from the result set.
     * 
     * @param <T>    T - The type of the object to be returned.
     * @param result - The ResultSet to process.
     * @param column - The index of the column to retrieve.
     * @param clazz  - The class type to cast the result to.
     * @return The object of the specified type.
     */
    public <T> T getResult(ResultSet result, int column, Class<T> clazz);

    /**
     * Get a result from the result set.
     * 
     * @param <T>        T - The type of the object to be returned.
     * @param result     - The ResultSet to process.
     * @param columnName - The name of the column to retrieve.
     * @param clazz      - The class type to cast the result to.
     * @return The object of the specified type.
     */
    public <T> T getResult(ResultSet result, String columnName, Class<T> clazz);

    public interface IResultProcessor<T> {
        T process(ResultSet resultSet);
    }

}
