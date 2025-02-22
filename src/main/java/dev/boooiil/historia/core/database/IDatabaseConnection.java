package dev.boooiil.historia.core.database;

import java.sql.Connection;
import java.sql.ResultSet;

import org.jspecify.annotations.Nullable;

import dev.boooiil.historia.core.database.DatabaseConnection.DatabaseType;

/**
 * Interface for database connections.
 */
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
    public @Nullable <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor);

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
    public @Nullable <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry);

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
    public @Nullable <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry, int curr);

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
    public void executor(String statement);

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
    public void updateExecutor(String statement);

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
    public void updateExecutor(String statement, int maxRetry);

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
    public void updateExecutor(String statement, int maxRetry, int curr);

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
    public boolean nextResult(ResultSet result);

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
    public @Nullable <T> T getResult(ResultSet result, int column, Class<T> clazz);

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
    public @Nullable <T> T getResult(ResultSet result, String columnName, Class<T> clazz);

    public interface IResultProcessor<T> {
        T process(ResultSet resultSet);
    }

}
