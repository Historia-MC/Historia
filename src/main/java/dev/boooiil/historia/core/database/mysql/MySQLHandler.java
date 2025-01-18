package dev.boooiil.historia.core.database.mysql;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dev.boooiil.historia.core.database.DatabaseAdapter;
import dev.boooiil.historia.core.database.IDatabaseHandler;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.Proficiency;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.util.Logging;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * It's a class that handles all the MySQL queries for the plugin.
 */
public class MySQLHandler extends MySQLConnection implements IDatabaseHandler {

    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    /**
     * Create the table in the database if it does not exist.
     * 
     */
    public void createTable() {

        String string = "CREATE TABLE IF NOT EXISTS " +
                "historia(UUID varchar(36), " +
                "Username varchar(16), " +
                "Class varchar(30), " +
                "Level int, " +
                "Experience int, " +
                "Login bigint, " +
                "Logout bigint, " +
                "Playtime bigint, " +
                "PRIMARY KEY (UUID))";

        executor(string);

    }

    /**
     * Create the user in the database.
     * 
     * @param uuid       - UUID of the player.
     * @param playerName - Name of the player.
     */

    public void createUser(UUID uuid, String playerName) {

        // TODO: figure out why i am making this check.
        // i should not be doing this, this is redundant
        if (userExists(uuid))
            return;

        String string = "INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'None', 1, 0, "
                + System.currentTimeMillis() + ", 0, 0)";

        executor(string);

    }

    /**
     * Set the username for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setUsername(UUID uuid, String playerName) {

        String string = ("UPDATE historia SET Username = '" + playerName + "' WHERE UUID = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the class name for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setProficiency(UUID uuid, Proficiency proficiency) {

        String string = ("UPDATE historia SET Class = '" + proficiency.getName() + "' WHERE UUID = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the class level for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setProficiencyLevel(UUID uuid, int classLevel) {

        String string = ("UPDATE historia SET Level = '" + classLevel + "' WHERE UUID = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the login time for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setLogin(UUID uuid) {

        String string = ("UPDATE historia SET Login = '" + System.currentTimeMillis() + "' WHERE UUID = '" + uuid
                + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the current experience for the given user.
     * 
     * @param uuid       - UUID of the player.
     * @param experience - Provided experience of the player.
     */

    public void setCurrentExperience(UUID uuid, double experience) {

        String string = ("UPDATE historia SET Experience = '" + experience + "' WHERE UUID = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the logout time for the given user.
     * 
     * @param uuid             - UUID of the player.
     * @param lastLogin        - Provided last login of the player.
     * @param previousPlaytime - Provided playtime of the player.
     */

    public void setLogout(UUID uuid, long lastLogin, long previousPlaytime) {

        long time = System.currentTimeMillis();

        String string = ("UPDATE historia " +
                "SET Logout = '" + time + "', " +
                "Playtime = '" + ((time - lastLogin) + previousPlaytime) + "' " +
                "WHERE UUID = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Get a list of usernames from the database.
     *
     * @return List of usernames.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public List<String> getUsernames() {

        String string = "SELECT Username FROM historia";

        ResultSet result = queryExecutor(string);
        List<String> usernames = new ArrayList<>();

        while (nextResult(result)) {
            usernames.add(getResult(result, "Username", String.class));
        }

        return usernames;

    }

    /**
     * Get the username with a given UUID.
     * 
     * @param uuid - UUID of the player.
     * @return Username of the player.
     */

    public String getUsername(UUID uuid) {

        String string = "SELECT Username FROM historia WHERE UUID = '" + uuid + "'";

        ResultSet result = queryExecutor(string);

        if (!nextResult(result))
            return null;

        return getResult(result, 1, String.class);

    }

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
    public Map<MySQLUserKeys, String> getUser(UUID uuid) {

        Logging.errorToConsole("WARNING: This method is marked for removal and will not be updated.");

        Map<MySQLUserKeys, String> map = new HashMap<>();

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                map.put(MySQLUserKeys.UUID, results.getString("UUID"));
                map.put(MySQLUserKeys.USERNAME, results.getString("Username"));
                map.put(MySQLUserKeys.CLASS, results.getString("Class"));
                map.put(MySQLUserKeys.LEVEL, results.getString("Level"));
                map.put(MySQLUserKeys.EXPERIENCE, results.getString("Experience"));
                map.put(MySQLUserKeys.LOGIN, results.getString("Login"));
                map.put(MySQLUserKeys.LOGOUT, results.getString("Logout"));
                map.put(MySQLUserKeys.PLAYTIME, results.getString("Playtime"));

            }

            return map;

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            DatabaseAdapter.reconnect();
            return getUser(uuid);

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO GET USER.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

            map.put(MySQLUserKeys.UUID, uuid.toString());
            map.put(MySQLUserKeys.USERNAME, "null");
            map.put(MySQLUserKeys.CLASS, "None");
            map.put(MySQLUserKeys.LEVEL, "1");
            map.put(MySQLUserKeys.EXPERIENCE, "0");
            map.put(MySQLUserKeys.LOGIN, "0");
            map.put(MySQLUserKeys.LOGOUT, "0");
            map.put(MySQLUserKeys.PLAYTIME, "0");

        }

        return map;

    }

    public HistoriaPlayer getUser(UUID uuid, boolean opt) {

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        ResultSet result = queryExecutor(string);

        if (!nextResult(result))
            return null;

        String username = getResult(result, "Username", String.class);
        ProficiencyName proficiencyName = ProficiencyName.fromString(getResult(result, "Class", String.class));
        int level = getResult(result, "Level", Integer.class);
        double experience = getResult(result, "Experience", Double.class);
        long login = getResult(result, "Login", Long.class);
        long logout = getResult(result, "Logout", Long.class);
        long playtime = getResult(result, "Playtime", Long.class);

        return new HistoriaPlayer(uuid, username, proficiencyName, level, experience, login, logout, playtime);

    }

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

    public List<UUID> getUUIDs() {

        String string = "SELECT UUID FROM historia";

        ResultSet result = queryExecutor(string);
        List<UUID> uuids = new ArrayList<>();

        while (nextResult(result)) {
            uuids.add(UUID.fromString(getResult(result, "UUID", String.class)));
        }

        return uuids;

    }

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
    @Nullable
    public UUID getUUID(String playerName) {

        String string = "SELECT UUID FROM historia WHERE Username = '" + playerName + "'";

        ResultSet result = queryExecutor(string);

        if (!nextResult(result))
            return null;

        return UUID.fromString(getResult(result, 1, String.class));

    }

    public void saveUser(HistoriaPlayer historiaPlayer) {

        UUID uuid = historiaPlayer.getUUID();
        String username = historiaPlayer.getUsername();
        String proficiency = historiaPlayer.getProficiency().getName().getKey();
        int level = historiaPlayer.getLevel();
        double experience = historiaPlayer.getCurrentExperience();

        String query = "UPDATE historia " +
                "SET Class = '" + proficiency + "', " +
                "Username = '" + username + "', " +
                "Level = '" + level + "', " +
                "Experience = '" + experience + "' " +
                "WHERE UUID = '" + uuid + "' AND " +
                "(Class != '" + proficiency + "' OR " +
                "Username != '" + username + "' OR " +
                "Level != '" + level + "' OR " +
                "Experience != '" + experience + "')";

        updateExecutor(query, 5);
    }

    /**
     * It checks if the user exists in the database
     * 
     * @param uuid The UUID of the player
     * @return A boolean value.
     */
    public boolean userExists(UUID uuid) {

        String statement = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        ResultSet result = queryExecutor(statement);
        return nextResult(result);

    }

    public void executor(String statement) {

        Logging.debugToConsole("Executnig:", statement);

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);

            preparedStatement.execute();
        } catch (SQLException sqlException) {

            Logging.errorToConsole("Failed to execute:", statement);
            Logging.errorToConsole("Cause:", sqlException.getCause().toString());
            Logging.errorToConsole("MySQL Error Code:", String.valueOf(sqlException.getErrorCode()));
            Logging.errorToConsole("MySQL Error Message:", sqlException.getMessage().toString());

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    public ResultSet queryExecutor(String statement) {

        Logging.debugToConsole("Executing query:", statement);

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);

            return preparedStatement.executeQuery();
        } catch (SQLException sqlException) {

            Logging.errorToConsole("Failed to execute query:", statement);
            Logging.errorToConsole("Cause:", sqlException.getCause().toString());
            Logging.errorToConsole("MySQL Error Code:", String.valueOf(sqlException.getErrorCode()));
            Logging.errorToConsole("MySQL Error Message:", sqlException.getMessage().toString());

            return null;
        }
    }

    public void updateExecutor(String statement) {

        Logging.debugToConsole("Executnig update query:", statement);

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {

            Logging.errorToConsole("Failed to execute update:", statement);
            Logging.errorToConsole("Cause:", sqlException.getCause().toString());
            Logging.errorToConsole("MySQL Error Code:", String.valueOf(sqlException.getErrorCode()));
            Logging.errorToConsole("MySQL Error Message:", sqlException.getMessage().toString());

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    public void updateExecutor(String statement, int maxRetry) {
        Logging.debugToConsole("Executnig update query:", statement, "with max retries: " + maxRetry);
        updateExecutor(statement, maxRetry, 0);
    }

    public void updateExecutor(String statement, int maxRetry, int curr) {

        Logging.debugToConsole("Executnig update query:", statement, "with max retries: " + maxRetry,
                "and current retries: " + curr);

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {

            Logging.errorToConsole("Failed to execute update:", statement);
            Logging.errorToConsole("Cause:", sqlException.getCause().toString());
            Logging.errorToConsole("MySQL Error Code:", String.valueOf(sqlException.getErrorCode()));
            Logging.errorToConsole("MySQL Error Message:", sqlException.getMessage().toString());
            Logging.errorToConsole("Retry " + ++curr + "/" + maxRetry);

            updateExecutor(statement, maxRetry, curr);

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    public boolean nextResult(ResultSet result) {

        Logging.debugToConsole("Trying next result...");

        try {
            return result.next();
        } catch (SQLException sqlException) {

            Logging.errorToConsole("Failed to process next result.");
            Logging.errorToConsole("Cause:", sqlException.getCause().toString());
            Logging.errorToConsole("MySQL Error Code:", String.valueOf(sqlException.getErrorCode()));
            Logging.errorToConsole("MySQL Error Message:", sqlException.getMessage().toString());

            return false;
        }

    }

    public <T> T getResult(ResultSet result, int column, Class<T> clazz) {
        try {
            if (clazz == String.class) {
                return clazz.cast(result.getString(column));
            } else if (clazz == Integer.class) {
                return clazz.cast(result.getInt(column));
            } else if (clazz == Double.class) {
                return clazz.cast(result.getDouble(column));
            } else if (clazz == Float.class) {
                return clazz.cast(result.getFloat(column));
            } else if (clazz == Boolean.class) {
                return clazz.cast(result.getBoolean(column));
            } else if (clazz == Long.class) {
                return clazz.cast(result.getLong(column));
            } else if (clazz == Array.class) {
                return clazz.cast(result.getArray(column));
            } else {
                Logging.errorToConsole("Unimplemented result type:", clazz.getName());
                return null;
            }
        } catch (SQLException sqlException) {
            Logging.errorToConsole("Failed to process next result.");
            Logging.errorToConsole("Cause:", sqlException.getCause().toString());
            Logging.errorToConsole("MySQL Error Code:", String.valueOf(sqlException.getErrorCode()));
            Logging.errorToConsole("MySQL Error Message:", sqlException.getMessage().toString());
            return null;
        }
    };

    public <T> T getResult(ResultSet result, String columnName, Class<T> clazz) {

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
                Logging.errorToConsole("Unimplemented result type:", clazz.getName());
                return null;
            }
        } catch (SQLException sqlException) {
            Logging.errorToConsole("Failed to process next result.");
            Logging.errorToConsole("Cause:", sqlException.getCause().toString());
            Logging.errorToConsole("MySQL Error Code:", String.valueOf(sqlException.getErrorCode()));
            Logging.errorToConsole("MySQL Error Message:", sqlException.getMessage().toString());
            return null;
        }
    };

    // A private constructor.
    private MySQLHandler() {
        throw new IllegalStateException("This class should not be initialized.");
    }
}