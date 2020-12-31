package dev.boooiil.historia.mysql;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.palmergames.bukkit.towny.object.Nation;
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
import dev.boooiil.historia.dependents.towny.TownyHandler;

public class UserData {

    /**
     * THIS CLASS WILL LIKELY BE CONVERTED INTO A HANDLER FOR ALL PLAYER INFORMATION
     * 
     * THIS WILL HANDLE IN THE FUTURE: PLAYER HEALTH PLAYER SPEED PLAYER SATURATION
     * PLAYER CLASS
     * 
     */

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
    double classMaxHealth;
    double classCalculatedHealth;
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

            String message = "Could not load info for user " + playerName + " with UUID " + uuid;

            Bukkit.getLogger().warning(message);
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

            Map<String, Double> classInfo = Config.getClassInfo(className);

            classHealth = classInfo.get("HEALTH");
            classMaxHealth = classInfo.get("MAX_HEALTH");
            classCalculatedHealth = (classHealth + ((classMaxHealth - classHealth) / 100)) * classLevel;
            classEvasion = classInfo.get("EVASION");
            classSpeed = Float.parseFloat(classInfo.get("SPEED").toString());
            classWeaponProficiency = classInfo.get("WEAPON_PROFICIENCY");
            classBowProficiency = classInfo.get("BOW_PROFICIENCY");
            classCrossbowProficiency = classInfo.get("CROSSBOW_PROFICIENCY");


            if (playerName != null && !storedName.equals(playerName)) setName();

        } else {

            className = "None";
            classLevel = 1;
            classExperience = 0;
            lastLogin = new Date().getTime();

            MySQL.createUser(uuid, playerName);

            Map<String, Double> classInfo = Config.getClassInfo(className);

            classHealth = classInfo.get("HEALTH");
            classMaxHealth = classInfo.get("MAX_HEALTH");
            classCalculatedHealth = (classHealth + ((classMaxHealth - classHealth) / 100)) * classLevel;
            classEvasion = classInfo.get("EVASION");
            classSpeed = Float.parseFloat(classInfo.get("SPEED").toString());
            classWeaponProficiency = classInfo.get("WEAPON_PROFICIENCY");
            classBowProficiency = classInfo.get("BOW_PROFICIENCY");
            classCrossbowProficiency = classInfo.get("CROSSBOW_PROFICIENCY");

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

        Map<String, Object> helmetMap = Config.getArmorInfo(helmet.getItemMeta().getLocalizedName());
        Map<String, Object> chestplateMap = Config.getArmorInfo(chestplate.getItemMeta().getLocalizedName());
        Map<String, Object> leggingsMap = Config.getArmorInfo(leggings.getItemMeta().getLocalizedName());
        Map<String, Object> bootMap = Config.getArmorInfo(boots.getItemMeta().getLocalizedName());

        double helmetArmor = (double) helmetMap.get("ARMOR");
        double chestplateArmor = (double) chestplateMap.get("ARMOR");
        double leggingsArmor = (double) leggingsMap.get("ARMOR");
        double bootsArmor = (double) bootMap.get("ARMOR");

        return helmetArmor + chestplateArmor + leggingsArmor + bootsArmor;

    }

    /**
     * Get the player's armor in an ItemStack array.
     * 
     * @return Player's armor.
     */

    public ItemStack[] getArmor() {

        return playerInventory.getArmorContents();

    }

    /**
     * Get the Config values of the weapon in the player's main hand.
     * 
     * @return Contains: Damage, Knockback, SweepingEdge
     */

    public Map<String, Double> getMainHandWeaponStats() {

        ItemStack mainHand = playerInventory.getItemInMainHand();

        Map<String, Double> weapon = new HashMap<>();

        Map<String, Object> weaponMap = Config.getWeaponInfo(mainHand.getItemMeta().getLocalizedName());

        weapon.put("Damage", (Double) weaponMap.get("DAMAGE"));
        weapon.put("Knockback", (Double) weaponMap.get("KNOCKBACK"));
        weapon.put("SweepingEdge", (Double) weaponMap.get("SWEEPING"));

        return weapon;

    }

    /**
     * Get the Config values of the weapon in the player's off hand.
     * 
     * @return Contains: Damage, Knockback, SweepingEdge
     */

    public Map<String, Double> getOffHandWeaponStats() {

        ItemStack offHand = playerInventory.getItemInOffHand();

        Map<String, Double> weapon = new HashMap<>();

        Map<String, Object> weaponMap = Config.getWeaponInfo(offHand.getItemMeta().getLocalizedName());

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

        return classCalculatedHealth;

    }

    /**
     * Get the calculated player's heath on level up.
     * 
     * @return Calculated health.
     */

    public double getHealthOnLevelUp() {

        return (classHealth + ((classMaxHealth - classHealth) / 100)) * classLevel + 1;

    }

    /**
     * Get the player's base speed.
     * 
     * @return Player's speed.
     */

    public float getSpeed() {

        return classSpeed - getSpeedPenalty();

    }

    /**
     * Get the speed penalty of the current armor  being worn.
     * 
     * @return Speed penalty.
     */

    public float getSpeedPenalty() {

        List<String> usable = getUsableArmor();
        ItemStack[] armor = getArmor();

        float heavy = (float) 0.02;
        float medium = (float) 0.01;

        int heavyItems = 0;
        int mediumItems = 0;

        for (ItemStack item : armor) {

            if (item != null) {

                String name = item.getItemMeta().getLocalizedName();

                if (!usable.contains(name)) {
    
                    String type = (String) Config.getArmorInfo(name).get("TYPE");
    
                    if (type.equals("Heavy")) heavyItems ++;
                    if (type.equals("Medium")) mediumItems ++;
    
                }
    
            }
        }

        return (heavyItems * heavy) + (mediumItems * medium);

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
     * Get the accuracy of the user's current weapon.
     * 
     * <p> Accuracy will start at 1.0 (100%) then get penalized based on its type.
     * 
     * @return Final accuracy.
     */

    public double getMainHandAccuracy() {

        List<String> types = getWeaponTypes();
        String type = getMainHandType();

        double baseAccuracy = 1.0;
        
        if (!types.contains(type)) {

            if (type.equals("Heavy")) baseAccuracy -= 0.2;
            if (type.equals("Medium")) baseAccuracy -= 0.1;

        }

        return baseAccuracy;

    }

    /**
     * Get the player's base evasion.
     * 
     * @return Class evasion.
     */

    public double getEvasion() {

        List<String> types = getArmorTypes();
        List<String> worn = getWornArmorTypes();

        if (!worn.isEmpty()) {

            for (String type : worn) {

                if (!types.contains(type)) {

                    if (type.equals("Heavy")) classEvasion -= 0.05;
                    if (type.equals("Medium")) classEvasion -= 0.03;

                }
            }

            return classEvasion;

        } 
        
        else return classEvasion;

    }

    public double getHarvestChance() {

        return Config.getClassInfo(className).get("HARVEST_CHANCE");

    }

    public double getDoubleHarvestChance() {

        return Config.getClassInfo(className).get("DOUBLE_HARVEST_CHANCE");

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
     * Get the if the player will hit its target.
     * 
     * @paran defenderEvasion - Base evasion of the user.
     * 
     * @return Whether or not the player hit its target.
     */

    public boolean willHit(double defenderEvasion) {

        // Will calculate whether or not the user will hit based on:

        // Users proficiency with the weapon
        // Users weapon weight
        // Defenders evasion rating

        return false;

    }

    /**
     * List of all armor types the class can use.
     *  
     * @return List of armor types the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public List<String> getArmorTypes() {

        return Config.getClassArmorTypes(className);

    }

    /**
     * List of armor types the class is wearing.
     *  
     * @return List of armor types the class is wearing.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public List<String> getWornArmorTypes() {

        List<String> types = new ArrayList<>();

        for (ItemStack armor : getArmor()) {

            if (armor != null) {

                String type = (String) Config.getArmorInfo(armor.getItemMeta().getLocalizedName()).get("TYPE");

                types.add(type);

            }
        }

        return types;

    }

    /**
     * List of all armor the class can use.
     *  
     * @return List of armor the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public List<String> getUsableArmor() {

        return Config.getUsableArmor(className);

    }

    /**
     * List of all weapon types the class can use.
     *  
     * @return List of weapon types the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public List<String> getWeaponTypes() {

        return Config.getClassWeaponTypes(className);

    }

    /**
     * List of all weapons the class can use.
     *  
     * @return List of weapons the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public List<String> getUsableWeapons() {

        return Config.getUsableWeapons(className);

    }

    /**
     * Get the player's base weapon proficiency.
     * 
     * @return Weapon proficiency.
     */

    public double getWeaponProficiency() {

        return Config.getClassInfo(className).get("WEAPON_PROFICIENCY");

    }

    /**
     * Get the player's base bow proficiency.
     * 
     * @return Bow proficiency.
     */

    public double getBowProficiency() {

        return Config.getClassInfo(className).get("BOW_PROFICIENCY");

    }

    /**
     * Get the player's base crossbow proficiency.
     * 
     * @return Crossbow proficiency.
     */

    public double getCrossbowProficiency() {

        return Config.getClassInfo(className).get("CROSSBOW_PROFICIENCY");

    }

    /**
     * Get the player's base weight capacity.
     * 
     * @return Base weight capacity.
     */

    public double getWeightCapacity() {

        return 40.0;

    }

    public String getMainHandType() {

        return (String) Config.getWeaponInfo(playerInventory.getItemInMainHand().getItemMeta().getLocalizedName()).get("TYPE");

    }

    public String getOffHandType() {

        return (String) Config.getWeaponInfo(playerInventory.getItemInOffHand().getItemMeta().getLocalizedName()).get("TYPE");

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
     * @see <a href ="https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.96.4.0/javadoc/">Town</a>
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
     * @see <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Location.html">Location</a>
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
     * @see <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Location.html">Location</a>
     */

    public Location getSpawnBlockLocation() {

        if (TownyHandler.hasSpawnBlock(playerName)) {

            return TownyHandler.getSpawn(playerName);

        } else {

            return new Location(Bukkit.getWorld("world"), 0, 0, 0);

        }
    }

    /**
     * Get the player's current nation.
     * 
     * @return The player's town or null if no nation was found.
     * 
     * @see <a href ="https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.96.4.0/javadoc/">Nation</a>
     */

    public Nation getNation() {

        return TownyHandler.getNation(playerName);

    }

    /**
     * Get the player's current nation name.
     * 
     * @return The player's nation name or 'None' if no nation was found.
     * 
     * @see <a href ="https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.96.4.0/javadoc/">Nation</a>
     */
    public String getNationName() {

        return TownyHandler.getNationName(playerName);

    }
}
