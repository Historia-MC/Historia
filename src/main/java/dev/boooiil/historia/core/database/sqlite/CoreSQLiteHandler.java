package dev.boooiil.historia.core.database.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import dev.boooiil.historia.core.database.ICoreDatabaseHandler;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.player.culture.Cultures;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;

/**
 * SQLite database query handler for Historia-Core.
 */
@Deprecated(forRemoval = false)
@NullMarked
public class CoreSQLiteHandler extends SQLiteConnection implements ICoreDatabaseHandler {

    public CoreSQLiteHandler() {

    }

    /**
     * Get the type of database.
     */
    public DatabaseType getDatabaseType() {
        return DatabaseType.SQLITE;
    }

    /**
     * Create the table in the database if it does not exist.
     * 
     */
    public void createTable() {

        String string = "CREATE TABLE IF NOT EXISTS " +
                "historia(uuid varchar(36), " +
                "username varchar(16), " +
                "proficiency varchar(30), " +
                "culture varchar(30), " +
                "level int, " +
                "experience int, " +
                "login bigint, " +
                "logout bigint, " +
                "playtime bigint, " +
                "PRIMARY KEY (uuid))";

        executor(string);

    }

    /**
     * Create the user in the database.
     * 
     * @param uuid       - UUID of the player.
     * @param playerName - Name of the player.
     */

    public void createUser(UUID uuid, String playerName) {

        String string = "INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'none', 'none', 1, 0, "
                + System.currentTimeMillis() + ", 0, 0)";

        executor(string);

    }

    /**
     * Set the username for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setUsername(UUID uuid, String playerName) {

        String string = ("UPDATE historia SET username = '" + playerName + "' WHERE uuid = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the proficiency name for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setProficiency(UUID uuid, ProficiencyName proficiencyName) {

        String string = ("UPDATE historia SET proficiency = '" + proficiencyName.name().toLowerCase()
                + "' WHERE uuid = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the proficiency name for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setCulture(UUID uuid, Cultures culture) {

        String string = ("UPDATE historia SET culture = '" + culture.name().toLowerCase() + "' WHERE uuid = '"
                + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the proficiency level for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setProficiencyLevel(UUID uuid, int proficiencyLevel) {

        String string = ("UPDATE historia SET level = '" + proficiencyLevel + "' WHERE uuid = '" + uuid + "'");

        updateExecutor(string, 5);

    }

    /**
     * Set the login time for the given user.
     * 
     * @param uuid - UUID of the player.
     */

    public void setLogin(UUID uuid) {

        String string = ("UPDATE historia SET login = '" + System.currentTimeMillis() + "' WHERE uuid = '" + uuid
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

        String string = ("UPDATE historia SET experience = '" + experience + "' WHERE uuid = '" + uuid + "'");

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
                "SET logout = '" + time + "', " +
                "playtime = '" + ((time - lastLogin) + previousPlaytime) + "' " +
                "WHERE uuid = '" + uuid + "'");

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

        String string = "SELECT username FROM historia";

        return queryExecutor(string, result -> {
            List<String> usernames = new ArrayList<>();

            while (nextResult(result)) {
                usernames.add(getResult(result, "username", String.class));
            }

            return usernames;
        });

    }

    /**
     * Get the username with a given UUID.
     * 
     * @param uuid - UUID of the player.
     * @return username of the player.
     */

    public String getUsername(UUID uuid) {

        String string = "SELECT username FROM historia WHERE uuid = '" + uuid + "'";

        return queryExecutor(string, result -> {
            if (!nextResult(result))
                return null;

            return getResult(result, 1, String.class);
        });

    }

    /**
     * Get the user with a given UUID. If the user does not exist, create a new user
     * and return it.
     * 
     * @param uuid - UUID of the player.
     * @return {@link HistoriaPlayer} with the given UUID.
     */
    public HistoriaPlayer getUser(UUID uuid) {

        String string = "SELECT * FROM historia WHERE uuid = '" + uuid + "'";

        return queryExecutor(string, result -> {

            if (!nextResult(result)) {
                HistoriaPlayer historiaPlayer = new HistoriaPlayer(uuid);
                createUser(historiaPlayer.getUUID(), historiaPlayer.getUsername());
                return historiaPlayer;
            }

            String username = getResult(result, "username", String.class);
            ProficiencyName proficiencyName = ProficiencyName.Companion
                    .fromString(getResult(result, "proficiency", String.class));
            Cultures culture = Cultures.getCulture(getResult(result, "culture", String.class));
            int level = getResult(result, "level", Integer.class);
            double experience = getResult(result, "experience", Double.class);
            long login = getResult(result, "login", Long.class);
            long logout = getResult(result, "logout", Long.class);
            long playtime = getResult(result, "playtime", Long.class);

            return new HistoriaPlayer(uuid, username, proficiencyName, culture, level, experience, login, logout,
                    playtime);
        }, 1, 1);

        // ResultSet result = queryExecutor(string);
        // return new HistoriaPlayer(uuid, username, proficiencyName, level, experience,
        // login, logout, playtime);

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

        return queryExecutor(string, result -> {
            List<UUID> uuids = new ArrayList<>();

            while (nextResult(result)) {
                uuids.add(UUID.fromString(getResult(result, "uuid", String.class)));
            }

            return uuids;
        });

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
    public @Nullable UUID getUUID(String playerName) {

        String string = "SELECT uuid FROM historia WHERE username = '" + playerName + "'";

        return queryExecutor(string, result -> {
            if (!nextResult(result))
                return null;

            return UUID.fromString(getResult(result, 1, String.class));

        });

    }

    /**
     * Save a user to the database.
     * 
     * @param historiaPlayer - Player to save.
     */
    public void saveUser(HistoriaPlayer historiaPlayer) {

        UUID uuid = historiaPlayer.getUUID();
        String username = historiaPlayer.getUsername();
        String proficiency = historiaPlayer.getProficiency().getName().getKey().toLowerCase();
        String culture = historiaPlayer.getCulture().name().toLowerCase();
        int level = historiaPlayer.getLevel();
        double experience = historiaPlayer.getCurrentExperience();

        String query = "UPDATE historia " +
                "SET proficiency = '" + proficiency + "', " +
                "culture = '" + culture + "', " +
                "username = '" + username + "', " +
                "level = '" + level + "', " +
                "experience = '" + experience + "' " +
                "WHERE uuid = '" + uuid + "' AND " +
                "(proficiency != '" + proficiency + "' OR " +
                "culture != '" + culture + "' OR " +
                "username != '" + username + "' OR " +
                "level != '" + level + "' OR " +
                "experience != '" + experience + "')";

        updateExecutor(query, 5);
    }

}