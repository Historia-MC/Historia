package dev.boooiil.historia.mysql;

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

            if (playerName == null) {

                playerName = MySQL.getUsername(uuid);

            }

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

            if (!storedName.equals(playerName)) setName();

        } else {

            className = "None";
            classLevel = 1;
            classExperience = 0;
            lastLogin = new Date().getTime();

            MySQL.createUser(uuid, playerName);

        }
    }

    /**
     * Update the player's name in the database to the name given when this object is instanced.
     */

    public void setName() {

        MySQL.setUsername(uuid, playerName);

    }

    /**
     * Update the player's class name in the database with the given class.
     * 
     * <p> This will set experience and level to 0.
     * 
     * <p> This method does not validate if the class exists.
     * 
     * @param className - The class to update.
     */

    public void setClass(String className) {

        MySQL.setClass(uuid, className);

    }
    
    /**
     * Update the player's level in the database with the given number. 
     * 
     * @param level - The level of the class.
     */

    public void setLevel(int level) {

        MySQL.setClassLevel(uuid, level);
    }

    /**
     * Update the player's login date in the database.
     */

    public void setLogin() {

        MySQL.setLogin(uuid);

    }

    /**
     * Update the player's logout date in the database.
     */

    public void setLogout() {

        MySQL.setLogout(uuid);

    }

    /**
     * Get the total amount of armor that the user is wearing.
     * 
     * @return Total armor.
     */

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

    /**
     * Get the config values of the weapon in the player's main hand.
     * 
     * @return Contains: Damage, Knockback, SweepingEdge
     */

    public Map<String, Double> getMainHandWeaponStats() {

        ItemStack mainHand = playerInventory.getItemInMainHand();

        Map<String, Double> weapon = new HashMap<>();

        Map<String, Object> weaponMap = config.getWeaponInfo(mainHand.getItemMeta().getLocalizedName());

        weapon.put("Damage", (Double) weaponMap.get("DAMAGE"));
        weapon.put("Knockback", (Double) weaponMap.get("KNOCKBACK"));
        weapon.put("SweepingEdge", (Double) weaponMap.get("SWEEPING"));

        return weapon;

    }

    /**
     * Get the config values of the weapon in the player's off hand.
     * 
     * @return Contains: Damage, Knockback, SweepingEdge
     */

    public Map<String, Double> getOffHandWeaponStats() {

        ItemStack offHand = playerInventory.getItemInOffHand();

        Map<String, Double> weapon = new HashMap<>();

        Map<String, Object> weaponMap = config.getWeaponInfo(offHand.getItemMeta().getLocalizedName());

        weapon.put("Damage", (Double) weaponMap.get("DAMAGE"));
        weapon.put("Knockback", (Double) weaponMap.get("KNOCKBACK"));
        weapon.put("SweepingEdge", (Double) weaponMap.get("SWEEPING"));

        return weapon;

    }

    /**
     * Get the calculated player's heath.
     * 
     * @return Calculated health.
     */

    public double getHealth() {

        double baseHealth = config.getClassInfo(className).get("HEALTH");
        double maxHealth = config.getClassInfo(className).get("MAX_HEALTH");

        classHealth = (baseHealth + ((maxHealth - baseHealth) / 100)) * getLevel();

        return classHealth;

    }

    /**
     * Get the calculated player's heath on level up.
     * 
     * @return Calculated health.
     */

    public double getHealthOnLevelUp() {

        double baseHealth = config.getClassInfo(className).get("HEALTH");
        double maxHealth = config.getClassInfo(className).get("MAX_HEALTH");
        int nextLevel = getLevel() + 1;

        return (baseHealth + ((maxHealth - baseHealth) / 100)) * nextLevel;

    }

    /**
     * Get the player's base speed.
     * 
     * @return Player's speed.
     */

    public float getSpeed() {

        return Float.parseFloat(config.getClassInfo(className).get("SPEED").toString());

    }

    /**
     * Get the player's base saturation drain.
     * 
     * @return Base speed.
     */

    public int getSaturationDrain() {

        return classBaseSaturationDrain;

    }

    /**
     * Get the player's class level.
     * 
     * @return Class level.
     */

    public int getLevel() {

        return classLevel;

    }

    /**
     * Get the player's class experience.
     * 
     * @return Class experience.
     */

    public int getExperience() {

        return classExperience;

    }

    /**
     * Get the player's max class experience.
     * 
     * @return Max class experience.
     */

    public int getMaxExperience() {

        return (int) Math.pow(getLevel(), 1.68);

    }

    /**
     * Get the player's base evasion.
     * 
     * @return Class evasion.
     */

    public double getEvasion() {

        return config.getClassInfo(className).get("EVASION");

    }

    /**
     * Get the player's base evasion on level up.
     * 
     * @return Class evasion.
     */

    public double getEvasionOnLevelUp() {

        // Will calcuate the user's evasion based on:

        // Users next level

        return getEvasion();

    }

    /**
     * Get the player's base weapon proficiency.
     * 
     * @return Weapon proficiency.
     */

    public double getWeaponProficiency() {

        return config.getClassInfo(className).get("WEAPON_PROFICIENCY");

    }

    /**
     * Get the if the player will hit its target.
     * 
     * @return Whether or not the player hit its target.
     */

    public boolean willHit() {

        // Will calculate whether or not the user will hit based on:

        // Users proficiency with the weapon
        // Users weapon weight
        // Defenders evasion rating

        return false;

    }

    /**
     * Get the player's base bow proficiency.
     * 
     * @return Bow proficiency.
     */

    public double getBowProficiency() {

        return config.getClassInfo(className).get("BOW_PROFICIENCY");

    }

    /**
     * Get the player's base crossbow proficiency.
     * 
     * @return Crossbow proficiency.
     */

    public double getCrossbowProficiency() {

        return config.getClassInfo(className).get("CROSSBOW_PROFICIENCY");

    }

    /**
     * Get the player's base weight capacity.
     * 
     * @return Base weight capacity.
     */

    public double getWeightCapacity() {

        return 40.0;

    }

    /**
     * Get the player's base weight capacity on level up.
     * 
     * @return Base weight capacity on level up.
     */

    public double getWeightCapacityOnLevelUp() {

        return getWeightCapacity();

    }

    /**
     * Get the player's name.
     * 
     * @return Player name.
     */

    public String getPlayerName() {

        return playerName;

    }

    /**
     * Get the player's class name.
     * 
     * @return Player class name.
     */

    public String getClassName() {

        return className;

    }

    /**
     * Get the player's last login.
     * 
     * @return Player's last login.
     */

    public long getLogin() {

        return lastLogin;

    }

    /**
     * Get the player's last logout.
     * 
     * @return Player's last logout.
     */

    public long getLogout() {

        return lastLogout;

    }

    /**
     * Get the player's current town.
     * 
     * @return The player's town or null if no town was found.
     * 
     * @see Town
     */

    public Town getTown() {

        return TownyHandler.getTown(playerName);

    }
    
    /**
     * Get the player's current town name.
     * 
     * @return The player's town name.
     */

    public String getTownName() {

        return TownyHandler.getTownName(playerName);

    }

    /**
     * Get the player's current home block location.
     * 
     * @return The player's home block location.
     * 
     * @see Location
     */

    public Location getHomeBlockLocation() {

        if (TownyHandler.hasHomeBlock(playerName)) {

            TownBlock townBlock = TownyHandler.getHomeBlock(playerName);

            return new Location(Bukkit.getWorld("world"), townBlock.getX(), 64, townBlock.getZ());

        } else {

            return new Location(Bukkit.getWorld("world"), 0, 0, 0);

        }

    }

    /**
     * Get the player's current spawn block location.
     * 
     * @return The player's spawn block location.
     * 
     * @see Location
     */

    public Location getSpawnBlockLocation() {

        if (TownyHandler.hasSpawnBlock(playerName)) {

            return TownyHandler.getSpawn(playerName);

        } else {

            return new Location(Bukkit.getWorld("world"), 0, 0, 0);

        }
    }
}
