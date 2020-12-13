package dev.boooiil.historia.mysql;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev.boooiil.historia.Config;

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
    int classLevel;
    int classExperience;
    int classBaseSaturationDrain;
    double classHealth;
    float classSpeed;

    long lastLogin;
    long lastLogout;

    Resident resident;
    Town town;

    PlayerInventory userInventory;

    public UserData(Player player) {

        try {

            resident = TownyUniverse.getInstance().getDataSource().getResident(displayName);
            if (resident.hasTown()) town = resident.getTown();


            Map<String, String> result;

            //Issue a statement that will return all values related to this user's uuid.
            result = sql.getStatement("SELECT * FROM historia WHERE UUID = '" + player.getUniqueId() + "'");

            Date date = new Date();

            userInventory = player.getInventory();

            //If there is no data stored for that UUID
            if (result.containsKey("UUID")) {

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

        } 
        catch (SQLException e) { e.printStackTrace(); }
        catch (NotRegisteredException e) { e.printStackTrace(); Bukkit.getServer().getLogger().warning("USER " + player.getDisplayName() + " COULD NOT BE FOUND IN TOWNY DATABASE OR NO TOWN FOUND."); }

        /* DEBUGGING

        System.out.println("UUID: " + uuid);
        System.out.println("Player Name: " + displayName);
        System.out.println("Class Name: " + className);
        System.out.println("Class Level: " + classLevel);
        System.out.println("Class Experience: " + classExperience);
        System.out.println("Player Login: " + lastLogin);
        System.out.println("Player Logout: " + lastLogout);

        */

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

    public void setLevel(UUID uuid, int level) {

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

    public Double getArmorValue() {

        ItemStack[] playerArmor = userInventory.getArmorContents();

        ItemStack helmet = playerArmor[0] != null ? playerArmor[0] : new ItemStack(Material.AIR);
        ItemStack chestplate = playerArmor[1] != null ? playerArmor[1] : new ItemStack(Material.AIR);
        ItemStack leggings = playerArmor[2] != null ? playerArmor[2] : new ItemStack(Material.AIR);
        ItemStack boots = playerArmor[3] != null ? playerArmor[3] : new ItemStack(Material.AIR);

        Config cHelmet = new Config(helmet.getItemMeta().getLocalizedName());
        Config cChestplate = new Config(chestplate.getItemMeta().getLocalizedName());
        Config cLeggings = new Config(leggings.getItemMeta().getLocalizedName());
        Config cBoots = new Config(boots.getItemMeta().getLocalizedName());

        return cHelmet.armorValue + cChestplate.armorValue + cLeggings.armorValue + cBoots.armorValue;

    }

    public Map<String, Double> getMainHandWeaponStats() {

        ItemStack mainHand = userInventory.getItemInMainHand();

        Map<String, Double> weapon = new HashMap<>();

        Config cMainHand = new Config(mainHand.getItemMeta().getLocalizedName());

        weapon.put("Damage", cMainHand.weaponDamage);
        weapon.put("Knockback", cMainHand.weaponKnockback);
        weapon.put("SweepingEdge", cMainHand.weaponSweeping);

        return weapon;

    }

    public Map<String, Double> getOffHandWeaponStats() {

        ItemStack offHand = userInventory.getItemInOffHand();

        Map<String, Double> weapon = new HashMap<>();

        Config cOffHand = new Config(offHand.getItemMeta().getLocalizedName());

        weapon.put("Damage", cOffHand.weaponDamage);
        weapon.put("Knockback", cOffHand.weaponKnockback);
        weapon.put("SweepingEdge", cOffHand.weaponSweeping);

        return weapon;

    }

    public double getHealth() {

        Config config = new Config(className);

        return config.health;

    }

    public float getSpeed() {

        Config config = new Config(className);

        return (float) config.speed;

    }

    public int getSaturationDrain() {

        return classBaseSaturationDrain;

    }

    public int getLevel() {

        return classLevel;

    }

    public int getExperience() {

        return classExperience;

    }

    public String getDisplayName() {

        return displayName;

    }

    public String getClassName() {

        return className;

    }

    public long getLogin() {

        return lastLogin;

    }

    public long getLogout() {

        return lastLogout;

    }

    public boolean hasTown() {

        return resident.hasTown();

    }

    public String getTownName() {

        if (hasTown()) {

            return town.getName();

        } else return "None";

    }

    public Town getTown() {

        if (hasTown()) return town;
        else return null;

    }

    public Map<String, Integer> getTownLocation() {

        Map<String, Integer> map = new HashMap<>();
            
        if (hasTown()) {

            try {

                TownBlock townBlock = town.getHomeBlock();

                map.put("X", townBlock.getX());
                map.put("Z", townBlock.getZ());

            } 
            catch (TownyException e) { 

                e.printStackTrace(); 

                map.put("X", 0); 
                map.put("Y", 0); 
            
            }
        }

        else {

            map.put("X", 0);
            map.put("Z", 0);
                
        }

        return map;


    }
}
