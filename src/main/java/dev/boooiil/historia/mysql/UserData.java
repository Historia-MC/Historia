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
     * THIS WILL HANDLE IN THE FUTURE: PLAYER HEALTH PLAYER SPEED PLAYER SATURATION
     * PLAYER CLASS
     * 
     */

    MySQL sql = new MySQL();
    Config config = new Config();

    // Assign accessable variables.
    UUID uuid;

    String storedName;
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

        else {
            throw new ClassCastException("Can not cast type " + Object.class.getName() + " to Player or OfflinePlayer");
        }

        // Issue a statement that will return all values related to this user's uuid.
        Map<String, String> result = MySQL.getUser(uuid);

        if (result == null) {

            Bukkit.getLogger().warning("Could not load info for user " + playerName + " with UUID " + uuid);
            return;

        }

        // If there is no data stored for that UUID
        if (result.containsKey("UUID")) {

            uuid = UUID.fromString(result.get("UUID"));
            storedName = result.get("Username");
            className = result.get("Class");
            classLevel = Integer.parseInt(result.get("Level"));
            classExperience = Integer.parseInt(result.get("Experience"));
            lastLogin = Long.parseLong(result.get("Login"));
            lastLogout = Long.parseLong(result.get("Logout"));

        } else {

            className = "None";
            classLevel = 1;
            classExperience = 0;
            lastLogin = new Date().getTime();

            MySQL.createUser(uuid, playerName);

        }
    }

    public void setName() {

        MySQL.setUsername(uuid, playerName);

    }

    public void setClass(String className) {

        MySQL.setClass(uuid, className);

    }

    public void setLevel(int level) {

        MySQL.setClassLevel(uuid, level);
    }

    public void setLogin() {

        MySQL.setLogin(uuid);

    }

    public void setLogout() {

        MySQL.setLogout(uuid);

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

        double baseHealth = config.getClassInfo(className).get("HEALTH");
        double maxHealth = config.getClassInfo(className).get("MAX_HEALTH");

        classHealth = (baseHealth + ((maxHealth - baseHealth) / 100)) * getLevel();

        return classHealth;

    }

    public double getHealthOnLevelUp() {

        double baseHealth = config.getClassInfo(className).get("HEALTH");
        double maxHealth = config.getClassInfo(className).get("MAX_HEALTH");
        int nextLevel = getLevel() + 1;

        return (baseHealth + ((maxHealth - baseHealth) / 100)) * nextLevel;

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

        return (int) Math.pow(getLevel(), 1.68);

    }

    public double getEvasion() {

        return config.getClassInfo(className).get("EVASION");

    }

    public double getEvasionOnLevelUp() {

        // Will calcuate the user's evasion based on:

        // Users next level

        return getEvasion();

    }

    public double getWeaponProficiency() {

        return config.getClassInfo(className).get("WEAPON_PROFICIENCY");

    }

    public boolean willHit() {

        // Will calculate whether or not the user will hit based on:

        // Users proficiency with the weapon
        // Users weapon weight
        // Defenders evasion rating

        return false;

    }

    public double getBowProficiency() {

        return config.getClassInfo(className).get("BOW_PROFICIENCY");

    }

    public double getCrossbowProficiency() {

        return config.getClassInfo(className).get("CROSSBOW_PROFICIENCY");

    }

    public double getWeightCapacity() {

        return 40.0;

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
