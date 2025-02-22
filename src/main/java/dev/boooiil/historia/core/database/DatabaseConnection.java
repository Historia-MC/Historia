package dev.boooiil.historia.core.database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import com.zaxxer.hikari.HikariDataSource;

import dev.boooiil.historia.core.util.CoreLogger;

/**
 * Abstract class to handle database connections.
 */
@NullMarked
public abstract class DatabaseConnection implements IDatabaseConnection {

    /**
     * The {@link HikariDataSource} data source for this connection.
     */
    protected HikariDataSource dataSource;

    /**
     * The {@link Connection} object for this class.
     */
    protected Connection connection;

    /**
     * If the connection has been errored.
     */
    protected boolean errored;

    /**
     * Enum containing types of databases.
     */
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

    /**
     * Initialize the data source. This is essentially building the connection to
     * the database.
     */
    public abstract boolean initDataSource();

    public DatabaseConnection() {
    };

    /**
     * Get the type of database.
     */
    public abstract DatabaseType getDatabaseType();

    /**
     * If this connection has been errored.
     * 
     * @return true if the connection has been errored.
     */
    public boolean isErrored() {
        return errored;
    };

    /**
     * Close the {@link Connection} associated with this object.
     * 
     * @return true if the connection was closed.
     */
    public boolean closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                CoreLogger.debugToConsole("Closed", getDatabaseType().toString(), "connection.");
            }
            return true;
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to close connection.");
            return false;
        }

    }

    /**
     * Connect to the database.
     * 
     * @return true if the connection was created.
     */
    public boolean connect() {

        CoreLogger.debugToConsole("Connecting to database...");

        try {
            connection = dataSource.getConnection();

            if (connection != null) {
                CoreLogger.debugToConsole("Connected to database.");
                return true;
            } else {
                CoreLogger.errorToConsole("Failed to connect to database.");
            }
        }

        catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to connect to database.");

        }

        return false;

    }

    /**
     * Close the {@link HikariDataSource} data source associated with this object.
     * 
     * @return true if the data source was closed.
     */
    public boolean closeDataSource() {

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            CoreLogger.debugToConsole("Closed", getDatabaseType().toString(), "data source.");
        }

        return true;

    }

    /**
     * Get the ${@link Connection} of this object.
     */
    public Connection getConnection() {

        try {
            if (dataSource != null && !dataSource.isClosed()) {
                connection = dataSource.getConnection();
                return connection;
            }
            return null;
        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to get connection.");
            return null;
        }

    }

    /**
     * Attempt to reconnect to the database.
     * 
     * @return true if successful reconnection.
     */
    public boolean reconnect() {

        try {

            CoreLogger.warnToConsole("Attempting to close the connection...");
            connection.close();
            CoreLogger.warnToConsole("Connection closed.");

            CoreLogger.warnToConsole("Attempting to reconnect...");
            connection = dataSource.getConnection();
            CoreLogger.warnToConsole("Reconnected to SQL Server.");
            return true;
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to reconnect.");
            return false;
        }
    }

    /**
     * Executes a SQL statement that does not return a result set.
     *
     * <p>
     * This method runs a generic SQL statement.
     * </p>
     *
     * <p>
     * If an exception occurs during execution, it is logged.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * String sql = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, name VARCHAR(100))";
     * executor(sql);
     * }</pre>
     * 
     * @param statement The SQL statement to be executed.
     * 
     */
    public void executor(String statement) {

        CoreLogger.debugToConsole("Executing:", statement);

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            preparedStatement.execute();
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to execute:" + statement);

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    /**
     * Executes a SQL query and processes the result using the provided
     * {@link IResultProcessor}.
     *
     * <p>
     * This method establishes a database connection, prepares a statement, executes
     * the query,
     * and then passes the resulting {@link ResultSet} to the given result processor
     * for handling.
     * </p>
     *
     * <p>
     * If an SQL exception occurs during execution, it is logged, and {@code null}
     * is returned.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * String sql = "SELECT name FROM users";
     * 
     * IResultProcessor<List<String>> processor = resultSet -> {
     *     List<String> names = new ArrayList<>();
     *     while (resultSet.next()) {
     *         names.add(resultSet.getString("name"));
     *     }
     *     return names;
     * };
     * 
     * List<String> userNames = queryExecutor(sql, processor);
     * System.out.println("User Names: " + userNames);
     * }</pre>
     *
     * @param <T>             The type of the result produced by the
     *                        {@link IResultProcessor}.
     * @param statement       The SQL query to be executed.
     * @param resultProcessor The processor that handles the {@link ResultSet} and
     *                        extracts data.
     * @return The processed result of type {@code T}, or {@code null} if an
     *         exception occurs.
     * 
     * @see {@link IResultProcessor}
     *
     * 
     */
    public @Nullable <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor) {

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultProcessor.process(resultSet);

        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to execute query:" + statement);

            return null;
        }

    }

    /**
     * Executes a SQL query and processes the result using the provided
     * {@link IResultProcessor}.
     *
     * <p>
     * This method establishes a database connection, prepares a statement, executes
     * the query,
     * and then passes the resulting {@link ResultSet} to the given result processor
     * for handling.
     * </p>
     *
     * <p>
     * If an SQL exception occurs during execution, it is logged, and {@code null}
     * is returned.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * String sql = "SELECT name FROM users";
     * 
     * IResultProcessor<List<String>> processor = resultSet -> {
     *     List<String> names = new ArrayList<>();
     *     while (resultSet.next()) {
     *         names.add(resultSet.getString("name"));
     *     }
     *     return names;
     * };
     * 
     * List<String> userNames = queryExecutor(sql, processor);
     * System.out.println("User Names: " + userNames);
     * }</pre>
     *
     * @param <T>             The type of the result produced by the
     *                        {@link IResultProcessor}.
     * @param statement       The SQL query to be executed.
     * @param resultProcessor The processor that handles the {@link ResultSet} and
     *                        extracts data.
     * @param maxRetry        The maximum amount of retries until the executor gives
     *                        up.
     * @return The processed result of type {@code T}, or {@code null} if an
     *         exception occurs.
     *
     * @see {@link IResultProcessor}
     *
     * 
     */
    public @Nullable <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry) {
        return queryExecutor(statement, resultProcessor, maxRetry, 0);
    }

    /**
     * Executes a SQL query and processes the result using the provided
     * {@link IResultProcessor}.
     *
     * <p>
     * This method establishes a database connection, prepares a statement, executes
     * the query,
     * and then passes the resulting {@link ResultSet} to the given result processor
     * for handling.
     * </p>
     *
     * <p>
     * If an SQL exception occurs during execution, it is logged, and {@code null}
     * is returned.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * String sql = "SELECT name FROM users";
     * 
     * IResultProcessor<List<String>> processor = resultSet -> {
     *     List<String> names = new ArrayList<>();
     *     while (resultSet.next()) {
     *         names.add(resultSet.getString("name"));
     *     }
     *     return names;
     * };
     * 
     * List<String> userNames = queryExecutor(sql, processor);
     * System.out.println("User Names: " + userNames);
     * }</pre>
     *
     * @param <T>             The type of the result produced by the
     *                        {@link IResultProcessor}.
     * @param statement       The SQL query to be executed.
     * @param resultProcessor The processor that handles the {@link ResultSet} and
     *                        extracts data.
     * @param maxRetry        The maximum amount of retries until the executor gives
     *                        up.
     * @param curr            The current amount of retries.
     * @return The processed result of type {@code T}, or {@code null} if an
     *         exception occurs.
     * 
     * @see {@link IResultProcessor}
     *
     * 
     */
    public @Nullable <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry,
            int curr) {

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultProcessor.process(resultSet);

        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to execute query:" + statement);

            return curr > maxRetry ? queryExecutor(statement, resultProcessor, maxRetry, curr) : null;
        }

    }

    /**
     * Executes a SQL update statement, such as an {@code INSERT}, {@code UPDATE},
     * or {@code DELETE}.
     *
     * <p>
     * This method establishes a database connection, prepares the statement,
     * and executes the update operation. If an exception occurs during execution,
     * it is logged.
     * </p>
     * 
     * <p>
     * <b>Note:</b> This method currently does not
     * handle stale connections. A caching mechanism
     * may be required to optimize connection handling
     * while the server is running.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>
     * {@code
     * String sql = "UPDATE users SET status = 'active' WHERE id = 1";
     * updateExecutor(sql);
     * }</pre>
     * 
     * @param statement The SQL update statement to be executed.
     * 
     */
    public void updateExecutor(String statement) {

        CoreLogger.debugToConsole("Executing update query:", statement);

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to execute update:" + statement);

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    /**
     * Executes a SQL update statement, such as an {@code INSERT}, {@code UPDATE},
     * or {@code DELETE}.
     *
     * <p>
     * This method establishes a database connection, prepares the statement,
     * and executes the update operation. If an exception occurs during execution,
     * it is logged.
     * </p>
     * 
     * <p>
     * <b>Note:</b> This method currently does not
     * handle stale connections. A caching mechanism
     * may be required to optimize connection handling
     * while the server is running.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>
     * {@code
     * String sql = "UPDATE users SET status = 'active' WHERE id = 1";
     * updateExecutor(sql);
     * }</pre>
     * 
     * @param statement The SQL update statement to be executed.
     * @param maxRetry  The maximum amount of retries until the executor gives
     *                  up.
     * 
     */
    public void updateExecutor(String statement, int maxRetry) {
        updateExecutor(statement, maxRetry, 0);
    }

    /**
     * Executes a SQL update statement, such as an {@code INSERT}, {@code UPDATE},
     * or {@code DELETE}.
     *
     * <p>
     * This method establishes a database connection, prepares the statement,
     * and executes the update operation. If an exception occurs during execution,
     * it is logged.
     * </p>
     * 
     * <p>
     * <b>Note:</b> This method currently does not
     * handle stale connections. A caching mechanism
     * may be required to optimize connection handling
     * while the server is running.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>
     * {@code
     * String sql = "UPDATE users SET status = 'active' WHERE id = 1";
     * updateExecutor(sql);
     * }</pre>
     * 
     * @param statement The SQL update statement to be executed.
     * @param maxRetry  The maximum amount of retries until the executor gives
     *                  up.
     * @param curr      The current amount of retries.
     * 
     */
    public void updateExecutor(String statement, int maxRetry, int curr) {

        CoreLogger.debugToConsole("Executing update query:", statement, "with max retries: " + maxRetry,
                "and current retries: " + curr);

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to execute update: " + statement + "Retry " + ++curr + "/"
                    + maxRetry);

            updateExecutor(statement, maxRetry, curr);

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    /**
     * Advances the cursor in the given {@link ResultSet} to the next row.
     *
     * <p>
     * This method attempts to move to the next result in the provided
     * {@link ResultSet}.
     * If an exception occurs during execution, it is logged, and {@code false} is
     * returned.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
     * while (nextResult(resultSet)) {
     *     String name = resultSet.getString("name");
     *     System.out.println("User Name: " + name);
     * }
     * }</pre>
     * 
     * @param result The {@link ResultSet} to process.
     * @return {@code true} if the cursor moves to the next row successfully,
     *         {@code false} otherwise.
     *
     */
    public boolean nextResult(ResultSet result) {

        CoreLogger.debugToConsole("Trying next result...");

        try {
            return result.next();
        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to process next result");
            return false;
        }

    }

    /**
     * Retrieves a value from the specified column in the given {@link ResultSet}.
     *
     * <p>
     * This method fetches the value from the given column index, determines the
     * column name,
     * and delegates to the {@code getResult} method that accepts a column name.
     * </p>
     *
     * <p>
     * If an exception occurs during execution, it is logged, and {@code null} is
     * returned.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * ResultSet resultSet = statement.executeQuery("SELECT age FROM users WHERE id = 1");
     * if (nextResult(resultSet)) {
     *     Integer age = getResult(resultSet, 1, Integer.class);
     *     System.out.println("User Age: " + age);
     * }
     * }</pre>
     * 
     * @param <T>    The type of the result expected.
     * @param result The {@link ResultSet} from which the value should be retrieved.
     * @param column The column index (1-based) to fetch the value from.
     * @param clazz  The class of the expected return type.
     * @return The retrieved value of type {@code T}, or {@code null} if an
     *         exception occurs.
     * 
     */
    public @Nullable <T> T getResult(ResultSet result, int column, Class<T> clazz) {
        try {

            ResultSetMetaData resultSetMetaData = result.getMetaData();

            return getResult(result, resultSetMetaData.getColumnName(column), clazz);

        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to get result: " + column);
            return null;
        }
    };

    /**
     * Retrieves a value from the specified column in the given {@link ResultSet}.
     *
     * <p>
     * This method fetches the value from the given column index, determines the
     * column name,
     * and delegates to the {@code getResult} method that accepts a column name.
     * </p>
     *
     * <p>
     * If an exception occurs during execution, it is logged, and {@code null} is
     * returned.
     * </p>
     * 
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * ResultSet resultSet = statement.executeQuery("SELECT age FROM users WHERE id = 1");
     * if (nextResult(resultSet)) {
     *     Integer age = getResult(resultSet, "age", Integer.class);
     *     System.out.println("User Age: " + age);
     * }
     * }</pre>
     * 
     * @param <T>        The type of the result expected.
     * @param result     The {@link ResultSet} from which the value should be
     *                   retrieved.
     * @param columnName The column name to fetch the value from.
     * @param clazz      The class of the expected return type.
     * @return The retrieved value of type {@code T}, or {@code null} if an
     *         exception occurs.
     * 
     */
    public @Nullable <T> T getResult(ResultSet result, String columnName, Class<T> clazz) {

        try {
            if (clazz == String.class) {
                return clazz.cast(result.getString(columnName));
            } else if (clazz == Integer.class) {
                return clazz.cast(result.getInt(columnName));
            } else if (clazz == Double.class) {
                return clazz.cast(result.getDouble(columnName));
            } else if (clazz == Float.class) {
                return clazz.cast(result.getFloat(columnName));
            } else if (clazz == Boolean.class) {
                return clazz.cast(result.getBoolean(columnName));
            } else if (clazz == Long.class) {
                return clazz.cast(result.getLong(columnName));
            } else if (clazz == Array.class) {
                return clazz.cast(result.getArray(columnName));
            } else {
                CoreLogger.errorToConsole("Unimplemented result type:", clazz.getName());
                return null;
            }
        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to get result: " + columnName);
            return null;
        }
    };

    /**
     * Logs details of an {@link SQLException} to the console.
     *
     * <p>
     * This method logs the provided leading message, along with detailed
     * information about the SQL exception, including its cause, error code,
     * and error message.
     * </p>
     *
     * <h3>Example Usage:</h3>
     * 
     * <pre>{@code
     * try {
     *     Statement statement = connection.createStatement();
     *     statement.executeUpdate("INVALID SQL QUERY");
     * } catch (SQLException e) {
     *     exceptionLogger(e, "Failed to execute update");
     * }
     * }</pre>
     * 
     * @param sqlE           The {@link SQLException} to log.
     * @param leadingMessage A custom message to provide context for the exception.
     *
     */
    protected void exceptionLogger(SQLException sqlE, String leadingMessage) {

        CoreLogger.errorToConsole("[", getDatabaseType().toString(), "]", leadingMessage);
        CoreLogger.errorToConsole("[", getDatabaseType().toString(), "]", "Cause: " + sqlE.getCause());
        CoreLogger.errorToConsole("[", getDatabaseType().toString(), "]", "Error Code: " + sqlE.getErrorCode());
        CoreLogger.errorToConsole("[", getDatabaseType().toString(), "]", "Error Message: " + sqlE.getMessage());

    }

}
