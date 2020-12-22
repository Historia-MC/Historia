package dev.boooiil.historia.mysql;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev.boooiil.historia.Config;
import dev.boooiil.historia.towny.TownyHandler;

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
    Config config = new Config();

    //Assign accessable variables.
    UUID uuid;

    String playerName;
    PlayerInventory playerInventory;

    String className;
    int classLevel;
    int classExperience;
    int classBaseSaturationDrain;
    double classHealth;
    float classSpeed;
    double classEvasion;
    double classWeaponProficiency;
    double classBowProficiency;
    double classCrossbowProficiency;

    long lastLogin;
    long lastLogout;


    public UserData(Object player) {
        
        try {

            if (player instanceof Player) {

                Player user = (Player) player;

                playerName = user.getName();
                playerInventory = user.getInventory();

                uuid = user.getUniqueId();

            }

            else if (player instanceof OfflinePlayer) {

                OfflinePlayer user = (OfflinePlayer) player;

                playerName = user.getName();

                uuid = user.getUniqueId();
                
            } 
            
            else { throw new  ClassCastException("Can not cast type " + Object.class.getName() + " to Player or OfflinePlayer"); }

            Map<String, String> result;

            //Issue a statement that will return all values related to this user's uuid.
            result = sql.getStatement("SELECT * FROM historia WHERE UUID = '" + uuid + "'");

            Date date = new Date();

            //If there is no data stored for that UUID
            if (result.containsKey("UUID")) {

                uuid = UUID.fromString(result.get("UUID"));
                className = result.get("Class");
                classLevel = Integer.parseInt(result.get("Level"));
                classExperience = Integer.parseInt(result.get("Experience"));
                lastLogin = Long.parseLong(result.get("Login"));
                lastLogout = Long.parseLong(result.get("Logout"));

            } else {

                lastLogin = date.getTime();
                className = "None";
                classLevel = 0;
                classExperience = 0;
                lastLogin = new Date().getTime();

                createUser();

            }

        } 
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void createUser() {

        try {

            sql.doStatement("INSERT INTO historia VALUES ('" + uuid + "', '" + playerName + "', 'None', 0, 0, " + lastLogin + ", 0)");

        } catch (SQLException e) { e.printStackTrace(); }
        


    }

    public void setName(UUID uuid, String name) {

        try {

            playerName = name;

            sql.doStatement("UPDATE historia SET Username = '" + playerName + "' WHERE UUID = '" + uuid + "'");

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

        ItemStack[] playerArmor = playerInventory.getArmorContents();

        ItemStack helmet = playerArmor[0] != null ? playerArmor[0] : new ItemStack(Material.AIR);
        ItemStack chestplate = playerArmor[1] != null ? playerArmor[1] : new ItemStack(Material.AIR);
        ItemStack leggings = playerArmor[2] != null ? playerArmor[2] : new ItemStack(Material.AIR);
        ItemStack boots = playerArmor[3] != null ? playerArmor[3] : new ItemStack(Material.AIR);

        Map<String, Object> helmetMap = config.getArmorInfo(helmet.getItemMeta().getLocalizedName());
        Map<String, Object> chestplateMap = config.getArmorInfo(chestplate.getItemMeta().getLocalizedName());
        Map<String, Object> leggingsMap = config.getArmorInfo(leggings.getItemMeta().getLocalizedName());
        Map<String, Object> bootMap = config.getArmorInfo(boots.getItemMeta().getLocalizedName());

        double helmetArmor = (double) helmetMap.get("ARMOR");
        double chestplateArmor = (double) chestplateMap.get("ARMOR");
        double leggingsArmor = (double) leggingsMap.get("ARMOR");
        double bootsArmor = (double) bootMap.get("ARMOR");

        return helmetArmor + chestplateArmor + leggingsArmor + bootsArmor;

    }

    public Map<String, Double> getMainHandWeaponStats() {

        ItemStack mainHand = playerInventory.getItemInMainHand();

        Map<String, Double> weapon = new HashMap<>();

        Map<String, Object> weaponMap = config.getWeaponInfo(mainHand.getItemMeta().getLocalizedName());

        weapon.put("Damage", (Double) weaponMap.get("DAMAGE"));
        weapon.put("Knockback", (Double) weaponMap.get("KNOCKBACK"));
        weapon.put("SweepingEdge", (Double) weaponMap.get("SWEEPING"));

        return weapon;

    }

    public Map<String, Double> getOffHandWeaponStats() {

        ItemStack offHand = playerInventory.getItemInOffHand();

        Map<String, Double> weapon = new HashMap<>();

        Map<String, Object> weaponMap = config.getWeaponInfo(offHand.getItemMeta().getLocalizedName());

        weapon.put("Damage", (Double) weaponMap.get("DAMAGE"));
        weapon.put("Knockback", (Double) weaponMap.get("KNOCKBACK"));
        weapon.put("SweepingEdge", (Double) weaponMap.get("SWEEPING"));

        return weapon;

    }

    public double getHealth() {

        return config.getClassInfo(className).get("HEALTH");

    }

    public double getHealthOnLevelUp() {

        return getHealth();

    }

    public float getSpeed() {

        return Float.parseFloat(config.getClassInfo(className).get("SPEED").toString());

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

    public int getMaxExperience() {

        return getExperience();

    }

    public double getEvasion() {

        return config.getClassInfo(className).get("EVASION");

    }

    public double getEvasionOnLevelUp() {

        //Will calcuate the user's evasion based on:

        //Users next level

        return getEvasion();

    }

    public double getWeaponProficiency() {

        return config.getClassInfo(className).get("WEAPON_PROFICIENCY");

    }

    public boolean willHit() {

        //Will calculate whether or not the user will hit based on:

        //Users proficiency with the weapon
        //Users weapon weight
        //Defenders evasion rating
        
        return false;

    }

    public double getBowProficiency() {

        return config.getClassInfo(className).get("BOW_PROFICIENCY");

    }

    public double getCrossbowProficiency() {

        return config.getClassInfo(className).get("CROSSBOW_PROFICIENCY");

    }

    public double getWeightCapacity() {

        return 0.0;

    }

    public double getWeightCapacityOnLevelUp() {

        return getWeightCapacity();

    }

    public String getDisplayName() {

        return playerName;

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

    public Town getTown() {

        return TownyHandler.getTown(playerName);

    }

    public String getTownName() {

        return TownyHandler.getTownName(playerName);

    }

    public Location getHomeBlockLocation() {

        if (TownyHandler.hasHomeBlock(playerName)) {

            TownBlock townBlock = TownyHandler.getHomeBlock(playerName);

            return new Location(Bukkit.getWorld("world"), townBlock.getX(), 64, townBlock.getZ());

        } else {

            return new Location(Bukkit.getWorld("world"), 0, 0, 0);

        }

    }
    
    public Location getSpawnBlockLocation() {

        if (TownyHandler.hasSpawnBlock(playerName)) {

            return TownyHandler.getSpawn(playerName);

        } else {

            return new Location(Bukkit.getWorld("world"), 0, 0, 0);

        }
    }
}
