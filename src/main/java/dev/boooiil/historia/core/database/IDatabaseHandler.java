package dev.boooiil.historia.core.database;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.boooiil.historia.core.database.IDatabaseConnection.DatabaseType;
import dev.boooiil.historia.core.database.mysql.MySQLUserKeys;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.Proficiency;

public interface IDatabaseHandler extends IDatabaseConnection {

    /**
     * Create the table in the database if it does not exist.
     * 
     */
    public void createTable();

    /**
     * Create the user in the database.
     * 
     * @param uuid       - UUID of the player.
     * @param playerName - Name of the player.
     */
    public void createUser(UUID uuid, String playerName);

    /**
     * Set the current experience for the given user.
     * 
     * @param uuid       - UUID of the player.
     * @param experience - Provided experience of the player.
     */
    public void setCurrentExperience(UUID uuid, double experience);

    public void setUsername(UUID uuid, String playerName);

    /**
     * Change the {@link Proficiency} for the given user.
     * 
     * @param uuid        - UUID of the player.
     * @param proficiency - The proficiency to change.
     */
    public void setProficiency(UUID uuid, Proficiency proficiency);

    /**
     * Set the class level for the given user.
     * 
     * @param uuid - UUID of the player.
     * @param int  - The level of the current class.
     */
    public void setProficiencyLevel(UUID uuid, int classLevel);

    /**
     * Set the login time for the given user.
     * 
     * @param uuid - UUID of the player.
     */
    public void setLogin(UUID uuid);

    /**
     * Set the logout time for the given user.
     * 
     * @param uuid             - UUID of the player.
     * @param lastLogin        - Provided last login of the player.
     * @param previousPlaytime - Provided playtime of the player.
     */
    public void setLogout(UUID uuid, long lastLogin, long previousPlaytime);

    /**
     * Get the username with a given UUID.
     * 
     * @param uuid - UUID of the player.
     * @return Username of the player.
     */
    public String getUsername(UUID uuid);

    /**
     * Get a list of usernames from the database.
     *
     * @return List of usernames.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public List<String> getUsernames();

    /**
     * Get all user information from the database.
     * 
     * @param uuid - UUID of the player.
     *
     * @return
     *         <p>
     *         "UUID", {@link java.lang.String String}
     *         <p>
     *         "Username", {@link java.lang.String String}
     *         <p>
     *         "Class", {@link java.lang.String String}
     *         <p>
     *         "Level", {@link java.lang.String String}
     *         <p>
     *         "Experience", {@link java.lang.String String}
     *         <p>
     *         "Login", {@link java.lang.String String}
     *         <p>
     *         "Logout", {@link java.lang.String String}
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/Map.html">Map</a>
     */
    @Deprecated(forRemoval = true)
    public Map<MySQLUserKeys, String> getUser(UUID uuid);

    /**
     * Get the provided HistoriaPlayer from the database.
     * 
     * @param uuid - UUID of the player.
     * @return {@link HistoriaPlayer}
     */
    public HistoriaPlayer getUser(UUID uuid, boolean opt);

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
    public UUID getUUID(String playerName);

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
    public List<UUID> getUUIDs();

    public void saveUser(HistoriaPlayer historiaPlayer);

    /**
     * It checks if the user exists in the database
     * 
     * @param uuid The UUID of the player
     * @return A boolean value.
     */
    public boolean userExists(UUID uuid);

    /**
     * Get the type of the current database.
     * 
     * @return {@link DatabaseType}
     */
    public DatabaseType getDatabaseType();

    public ResultSet queryExecutor(String statement);

    public void updateExecutor(String statement);

    public boolean nextResult(ResultSet result);
}
