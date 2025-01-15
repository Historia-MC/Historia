package dev.boooiil.historia.core.database.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dev.boooiil.historia.core.database.DatabaseAdapter;
import dev.boooiil.historia.core.database.IDatabaseHandler;
import dev.boooiil.historia.core.database.mysql.MySQLConnection;
import dev.boooiil.historia.core.database.mysql.MySQLUserKeys;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.util.Logging;

public class SQLiteHandler implements IDatabaseHandler {

    public boolean createTable() {

        try {
            String createTable = "CREATE TABLE IF NOT EXISTS " +
                    "historia(UUID varchar(36), " +
                    "Username varchar(16), " +
                    "Class varchar(30), " +
                    "Level int, " +
                    "Experience int, " +
                    "Login bigint, " +
                    "Logout bigint, " +
                    "Playtime bigint, " +
                    "PRIMARY KEY (UUID))";

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(createTable);
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO CREATE TABLE.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }

        return false;
    }

    public boolean createUser(UUID uuid, String playerName) {

        if (userExists(uuid)) {
            return false;
        }

        try {
            String createUser = "INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'None', 1, 0, "
                    + System.currentTimeMillis() + ", 0, 0)";

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(createUser);
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO CREATE USER.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }
        return false;
    }

    public boolean setUsername(UUID uuid, String playerName) {

        try {
            String setUsername = "UPDATE historia SET Username = '" + playerName + "' WHERE UUID = '" + uuid + "'";

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(setUsername);

            Logging.infoToConsole("(SQLite) SET USERNAME FOR " + uuid + " TO " + playerName + ".");
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO SET USERNAME.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }

        return false;
    }

    public boolean setProficiency(UUID uuid, String proficiency) {

        try {
            String setProficiency = "UPDATE historia SET Class = '" + proficiency + "' WHERE UUID = '" + uuid + "'";

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(setProficiency);
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO SET PROFICIENCY.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }
        return false;
    }

    public boolean setProficiencyLevel(UUID uuid, int level) {

        try {
            String setProficiencyLevel = "UPDATE historia SET Level = " + level + " WHERE UUID = '" + uuid + "'";

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(setProficiencyLevel);
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO SET PROFICIENCY LEVEL.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }
        return false;
    }

    public boolean setLogin(UUID uuid) {

        try {
            String setLogin = "UPDATE historia SET Login = " + System.currentTimeMillis() + " WHERE UUID = '" + uuid
                    + "'";

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(setLogin);
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO SET LOGIN.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }
        return false;
    }

    public boolean setLogout(UUID uuid, long lastLogin, long previousPlaytime) {

        try {
            long time = System.currentTimeMillis();

            String string = ("UPDATE historia " +
                    "SET Logout = '" + time + "', " +
                    "Playtime = '" + ((time - lastLogin) + previousPlaytime) + "' " +
                    "WHERE UUID = '" + uuid + "'");

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(string);
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO SET LOGOUT.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }
        return false;
    }

    public boolean setCurrentExperience(UUID uuid, double experience) {

        try {
            String setCurrentExperience = "UPDATE historia SET Experience = '" + experience + "' WHERE UUID = '" + uuid
                    + "'";

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            statement.executeUpdate(setCurrentExperience);
            return true;
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO SET CURRENT EXPERIENCE.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }
        return false;
    }

    public List<String> getUsernames() {

        String string = "SELECT Username FROM historia";
        List<String> answer = new ArrayList<>();

        try {

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(results.getString("Username"));

            }
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO GET USERNAMES.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }

        return answer;
    }

    public String getUsername(UUID uuid) {

        String string = "SELECT Username FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            ResultSet results = statement.executeQuery(string);

            if (results.next()) {

                return results.getString(1);

            }
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO GET USERNAME.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }

        return null;
    }

    @Deprecated(forRemoval = true)
    public Map<MySQLUserKeys, String> getUser(UUID uuid) {

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            ResultSet results = statement.executeQuery(string);

            if (results.next()) {

                Logging.debugToConsole("(SQLite) USER EXISTS. RETURNING USER.");

                return Map.of(
                        MySQLUserKeys.UUID, results.getString("UUID"),
                        MySQLUserKeys.USERNAME, results.getString("Username"),
                        MySQLUserKeys.CLASS, results.getString("Class"),
                        MySQLUserKeys.LEVEL, results.getString("Level"),
                        MySQLUserKeys.EXPERIENCE, results.getString("Experience"),
                        MySQLUserKeys.LOGIN, results.getString("Login"),
                        MySQLUserKeys.LOGOUT, results.getString("Logout"),
                        MySQLUserKeys.PLAYTIME, results.getString("Playtime"));
            }

        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO GET USER.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());

        }

        Logging.debugToConsole("(SQLite) USER DOES NOT EXIST. RETURNING EMPTY USER.");

        return Map.of(
                MySQLUserKeys.UUID, uuid.toString(),
                MySQLUserKeys.USERNAME, "null",
                MySQLUserKeys.CLASS, "None",
                MySQLUserKeys.LEVEL, "1",
                MySQLUserKeys.EXPERIENCE, "0",
                MySQLUserKeys.LOGIN, "0",
                MySQLUserKeys.LOGOUT, "0",
                MySQLUserKeys.PLAYTIME, "0");

    }

    public HistoriaPlayer getUser(UUID uuid, boolean opt) {

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Statement statement = CONNECTION.createStatement();
            ResultSet results = statement.executeQuery(string);

            String username = "";
            ProficiencyName proficiencyName = ProficiencyName.NONE;
            int level = 0;
            double experience = 0;
            long login = 0;
            long logout = 0;
            long playtime = 0;

            while (results.next()) {

                username = results.getString("Username");
                proficiencyName = ProficiencyName.fromString(results.getString("Class"));
                level = results.getInt("Level");
                experience = results.getDouble("Experience");
                login = results.getLong("Login");
                logout = results.getLong("Logout");
                playtime = results.getLong("Playtime");

            }

            return new HistoriaPlayer(uuid, username, proficiencyName, level, experience, login, logout, playtime);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            return getUser(uuid, true);

        } catch (Exception e) {

            Logging.errorToConsole("FAILED TO GET USER.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

            return new HistoriaPlayer(uuid);

        }

    }

    public List<UUID> getUUIDs() {

        String string = "SELECT UUID FROM historia";
        List<UUID> answer = new ArrayList<>();

        try {

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            ResultSet results = statement.executeQuery(string);

            while (results.next()) {

                answer.add(UUID.fromString(results.getString("UUID")));

            }
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO GET UUIDS.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }

        return answer;
    }

    public UUID getUUID(String playerName) {

        String string = "SELECT UUID FROM historia WHERE Username = '" + playerName + "'";

        try {

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            ResultSet results = statement.executeQuery(string);

            if (results.next()) {

                return UUID.fromString(results.getString(1));

            }
        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO GET UUID.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
        }

        return getUUID("null");
    }

    public void saveUser(HistoriaPlayer historiaPlayer) {
        try {
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

            Statement statement = CONNECTION.createStatement();
            statement.execute(query);

        } catch (CommunicationsException cE) {

            Logging.infoToConsole("Communication Exception");

            MySQLConnection.reconnectOnStale();
            saveUser(historiaPlayer);

        } catch (SQLException e) {

            Logging.errorToConsole("FAILED TO SAVE USER.");
            Logging.errorToConsole("Cause: " + e.getCause());
            Logging.errorToConsole("MySQL State: " + e.getSQLState());
            Logging.errorToConsole("MySQL Error Code: " + e.getErrorCode());
            Logging.errorToConsole("MySQL Error Message: " + e.getMessage());

        }
    }

    private boolean userExists(UUID uuid) {

        String string = "SELECT * FROM historia WHERE UUID = '" + uuid + "'";

        try {

            Statement statement = DatabaseAdapter.getConnection().createStatement();
            ResultSet results = statement.executeQuery(string);

            return results.next();

        }

        catch (Exception e) {
            Logging.errorToConsole("(SQLite) FAILED TO CHECK IF USER EXISTS.");
            Logging.errorToConsole("(SQLite) Cause: " + e.getCause());
            Logging.errorToConsole("(SQLite) SQLite Error Message: " + e.getMessage());
            return false;
        }
    }
}
