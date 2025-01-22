package dev.boooiil.historia.core.database;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.Proficiency;

public abstract class CoreDatabaseHandler extends DatabaseConnection implements ICoreDatabaseHandler {

    private DatabaseConnection databaseConnection;

    // TODO: voids become boolean

    /**
     * Create the table in the database if it does not exist.
     * 
     */
    public abstract void createTable();

    public abstract void createUser(UUID uuid, String playerName);

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    };

    /**
     * Set the current experience for the given user.
     * 
     * @param uuid       - UUID of the player.
     * @param experience - Provided experience of the player.
     */
    public abstract void setCurrentExperience(UUID uuid, double experience);

    public abstract void setUsername(UUID uuid, String playerName);

    /**
     * Change the {@link Proficiency} for the given user.
     * 
     * @param uuid        - UUID of the player.
     * @param proficiency - The proficiency to change.
     */
    public abstract void setProficiency(UUID uuid, Proficiency proficiency);

    /**
     * Set the class level for the given user.
     * 
     * @param uuid - UUID of the player.
     * @param int  - The level of the current class.
     */
    public abstract void setProficiencyLevel(UUID uuid, int classLevel);

    /**
     * Set the login time for the given user.
     * 
     * @param uuid - UUID of the player.
     */
    public abstract void setLogin(UUID uuid);

    /**
     * Set the logout time for the given user.
     * 
     * @param uuid             - UUID of the player.
     * @param lastLogin        - Provided last login of the player.
     * @param previousPlaytime - Provided playtime of the player.
     */
    public abstract void setLogout(UUID uuid, long lastLogin, long previousPlaytime);

    /**
     * Get the username with a given UUID.
     * 
     * @param uuid - UUID of the player.
     * @return Username of the player.
     */
    public abstract String getUsername(UUID uuid);

    /**
     * Get a list of usernames from the database.
     *
     * @return List of usernames.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public abstract List<String> getUsernames();

    /**
     * Get the provided HistoriaPlayer from the database.
     * 
     * @param uuid - UUID of the player.
     * @return {@link HistoriaPlayer}
     */
    public abstract HistoriaPlayer getUser(UUID uuid);

    /**
     * Get a specific UUID from the database using a username.
     * 
     * @param playerName - Name of the player.
     *
     * @return UUID of the given username.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html">UUID</a>
     */
    public abstract UUID getUUID(String playerName);

    /**
     * Get a list of UUIDs from the database.
     *
     * @return List of UUIDs.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html">UUID</a>
     */
    public abstract List<UUID> getUUIDs();

    public abstract void saveUser(HistoriaPlayer historiaPlayer);

    /**
     * Get the type of the current database.
     * 
     * @return {@link DatabaseType}
     */
    public abstract DatabaseType getDatabaseType();

    /**
     * Execute a SQL statement without returning any result.
     * 
     * @param statement The SQL statement to be executed.
     * 
     */
    public abstract void executor(String statement);

    public abstract <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor);

    public abstract <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry);

    public abstract <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry, int curr);

    /**
     * This method will execute an update statement with no retry count.
     * 
     * @param statement The SQL statement to be executed.
     * 
     */
    public abstract void updateExecutor(String statement);

    /**
     * This method will attempt to execute the update statement up to maxRetry times
     * before giving up.
     * 
     * @param statement The SQL statement to be executed.
     * @param maxRetry  The maximum number of times to retry the execution.
     * 
     */
    public abstract void updateExecutor(String statement, int maxRetry);

    /**
     * This method will attempt to execute the update statement up to maxRetry times
     * before giving up.
     * 
     * @param statement The SQL statement to be executed.
     * @param maxRetry  The maximum number of times to retry the execution.
     * @param curr      The current retry count.
     * 
     */
    public abstract void updateExecutor(String statement, int maxRetry, int curr);

    /**
     * Get the next result from the result set.
     * 
     * @param result The ResultSet to process.
     * @return true if there is a next result, false otherwise.
     * 
     */
    public abstract boolean nextResult(ResultSet result);

    /**
     * Get a result from the result set.
     * 
     * @param <T>    T - The type of the object to be returned.
     * @param result - The ResultSet to process.
     * @param column - The index of the column to retrieve.
     * @param clazz  - The class type to cast the result to.
     * @return The object of the specified type.
     */
    public abstract <T> T getResult(ResultSet result, int column, Class<T> clazz);

    /**
     * Get a result from the result set.
     * 
     * @param <T>        T - The type of the object to be returned.
     * @param result     - The ResultSet to process.
     * @param columnName - The name of the column to retrieve.
     * @param clazz      - The class type to cast the result to.
     * @return The object of the specified type.
     */
    public abstract <T> T getResult(ResultSet result, String columnName, Class<T> clazz);

    public abstract interface IResultProcessor<T> {
        T process(ResultSet resultSet);
    }

}
