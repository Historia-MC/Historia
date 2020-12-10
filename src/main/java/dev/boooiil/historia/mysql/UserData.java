package dev.boooiil.historia.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.CheckForNull;

import org.bukkit.entity.Player;

public class UserData {

    /**
     * THIS CLASS WILL LIKELY BE CONVERTED INTO A HANDLER FOR ALL PLAYER INFORMATION
     * 
     * THIS WILL HANDLE IN THE FUTURE:
     *      PLAYER HEALTH
     *      PLAYER SPEED
     *      PLAYER SATURATION
     *      PLAYER CLASS
     *      
     */
   
    MySQL sql = new MySQL();

    //Assign accessable variables.
    UUID uuid;
    String displayName;
    String className;
    Integer classLevel;
    Integer classExperience;
    Long lastLogin;
    Long lastLogout;

    public UserData(Player player) {

        try {

            Map<String, String> result;

            //Issue a statement that will return all values related to this user's uuid.
            result = sql.getStatement("SELECT * FROM historia WHERE UUID = '" + player.getUniqueId() + "'");

            System.out.println(result.toString());

            Date date = new Date();

            //If there is no data stored for that UUID

            if (result.containsKey("UUID")) {

                System.out.println(result.toString());

                uuid = UUID.fromString(result.get("UUID"));
                displayName = result.get("Username");
                className = result.get("Class");
                classLevel = Integer.parseInt(result.get("Level"));
                classExperience = Integer.parseInt(result.get("Experience"));
                lastLogin = Long.parseLong(result.get("Login"));
                lastLogout = Long.parseLong(result.get("Logout"));

            } else {

                uuid = player.getUniqueId();
                displayName = player.getName();
                lastLogin = date.getTime();

                createUser();

            }

        } catch (SQLException e) { e.printStackTrace(); }

        System.out.println("UUID: " + uuid);
        System.out.println("Player Name: " + displayName);
        System.out.println("Class Name: " + className);
        System.out.println("Class Level: " + classLevel);
        System.out.println("Class Experience: " + classExperience);
        System.out.println("Player Login: " + lastLogin);
        System.out.println("Player Logout: " + lastLogout);

    }

    public void createUser() {

        try {

            sql.doStatement("INSERT INTO historia VALUES ('" + uuid + "', '" + displayName + "', 'None', 0, 0, " + lastLogin + ", 0)");

        } catch (SQLException e) { e.printStackTrace(); }
        


    }

    public void setName(UUID uuid, String name) {

        try {

            displayName = name;

            sql.doStatement("UPDATE historia SET Username = '" + displayName + "' WHERE UUID = '" + uuid + "'");

        } catch (SQLException e) { e.printStackTrace(); }


    }

    public void setClass(UUID uuid, String name) {

        try {

            className = name;

            sql.doStatement("UPDATE historia SET Class = '" + className + "', Level = 0, Experience = 0 WHERE UUID = '" + uuid + "'");

        } catch (SQLException e) { e.printStackTrace(); }

    }

    public void setLevel(UUID uuid, Integer level) {

        try {

            classLevel = level;

            sql.doStatement("UPDATE historia SET Username = '" + classLevel + "' WHERE UUID = '" + uuid + "'");

        } catch (SQLException e) { e.printStackTrace(); }

    }

    public void setLogin(UUID uuid, Long login) {

        try {

            lastLogin = login;

            sql.doStatement("UPDATE historia SET Login = '" + login + "' WHERE UUID = '" + uuid + "'");

        } catch (SQLException e) { e.printStackTrace(); }

    }

    public void setLogout(UUID uuid, Long logout) {

        try {

            lastLogout = logout;

            sql.doStatement("UPDATE historia SET Logout = '" + logout + "' WHERE UUID = '" + uuid + "'");

        } catch (SQLException e) { e.printStackTrace(); }

    }

    public Integer getLevel() {

        return classLevel;

    }

    public String getDisplayName() {

        return displayName;

    }

    public String getClassName() {

        return className;

    }

    public Long getLogin() {

        return lastLogin;

    }

    public Long getLogout() {

        return lastLogout;

    }
}
