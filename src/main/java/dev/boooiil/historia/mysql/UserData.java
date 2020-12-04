package dev.boooiil.historia.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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

    public UserData(Player player) {

        try {

            ResultSet result;

            //Issue a statement that will return all values related to this user's uuid.
            result = sql.getStatement("SELECT * FROM historia WHERE UUID = ('" + player.getUniqueId() + "')");

            //If there is no data stored for that UUID
            if (!result.next()) {

                //Set the player's information if they are classless.
                uuid = player.getUniqueId();
                displayName = player.getName();
                className = "None";
                classLevel = 0;
                classExperience = 0;

            } else {

                //Else we iterate through.
                while (result.next()) {

                    uuid = UUID.fromString(result.getString("UUID"));
                    displayName = result.getString("Username");
                    className = result.getString("Class");
                    classLevel = result.getInt("Level");
                    classExperience = result.getInt("Expereince");
    
                }
            }

        } catch (SQLException e) { e.printStackTrace(); }

        System.out.println("UUID: " + uuid);
        System.out.println("Player Name: " + displayName);
        System.out.println("Class Name: " + className);
        System.out.println("Class Level: " + classLevel);
        System.out.println("Class Experience: " + classExperience);

    }

    public void setName(UUID uuid, String name) {

        try {

            displayName = name;

            sql.doStatement("UPDATE historia SET Username = ('" + displayName + "') WHERE UUID = ('" + uuid + "')");

        } catch (SQLException e) { e.printStackTrace(); }


    }

    public void setClass(UUID uuid, String name) {

        try {

            className = name;

            sql.doStatement("UPDATE historia SET Class = ('" + className + "') WHERE UUID = ('" + uuid + "')");

        } catch (SQLException e) { e.printStackTrace(); }

    }

    public void setLevel(UUID uuid, Integer level) {

        try {

            classLevel = level;

            sql.doStatement("UPDATE historia SET Username = ('" + classLevel + "') WHERE UUID = ('" + uuid + "')");

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

}
