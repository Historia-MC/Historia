package dev.boooiil.historia.classes;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import dev.boooiil.historia.configuration.ClassConfig;
import dev.boooiil.historia.dependents.towny.TownyHandler;
import dev.boooiil.historia.mysql.MySQLHandler;
import dev.boooiil.historia.util.Logging;

public class HistoriaPlayer {

    private UUID uuid;
    private String username;

    private String className;

    private boolean isValid;
    private boolean isOnline;
    private boolean isResident;
    private boolean hasTown;
    private boolean hasNation;

    private int level;

    private long lastLogin;
    private long lastLogout;
    private long playtime;

    private double currentTemperature;
    private double armorAdjustment;
    private double environmentAdjustment;
    private double maxTemperature;

    private float baseHealth;
    private float modifiedHealth;

    private float baseExperience;
    private float experienceTotal;
    private float experienceMax;

    private ClassConfig classConfig;

    private Player onlinePlayer;
    private OfflinePlayer offlinePlayer;

    private Resident resident;
    private Town town;
    private Nation nation;

    /**
     * Default constrctor, will return invalid player.
     */
    public HistoriaPlayer() {
        this.isValid = false;
    }

    /**
     * General constructor.
     * 
     * @param uuid - UUID of the player.
     */
    public HistoriaPlayer(UUID uuid) {

        // TODO: GET TOWN AND NATION VALUES
        // TODO: SET PLAYTIME IN HISTORIA TABLE

        // Base health and multiplier will get determined when we finish the class
        // config.
        // Experience max will just be experience * multiplier.

        this.uuid = uuid;

        this.onlinePlayer = Bukkit.getPlayer(uuid);
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        // Get an object where the key is a string and the value is also a string.
        // IE: { "key": "value" }, where "key" can be accessed using the .get() method.
        Map<String, String> user = MySQLHandler.getUser(uuid);

        this.className = user.get("class");

        this.isValid = true;
        this.isOnline = this.onlinePlayer == null ? false : true;

        this.level = Integer.parseInt(user.get("level"));
        this.experienceTotal = Float.parseFloat(user.get("experience"));

        this.classConfig = ClassConfig.getConfig(this.className);

        this.baseHealth = this.classConfig.baseHealth;

        this.lastLogin = Long.parseLong(user.get("login"));
        this.lastLogout = Long.parseLong(user.get("logout"));
        this.playtime = Long.parseLong(user.get("playtime"));

        this.armorAdjustment = calculateArmorAdjustment();
        this.environmentAdjustment = calculateEnvironmentAdjustment();

        // Set this explicitly in the config
        this.modifiedHealth = 0;

        // TODO: Calculate experience gain

        this.username = getUsername();

        this.resident = TownyHandler.getResident(uuid);
        this.town = TownyHandler.getTown(uuid);
        this.nation = TownyHandler.getNation(uuid);

        this.isResident = this.resident == null ? false : true;
        this.hasTown = this.town == null ? false : true;
        this.hasNation = this.nation == null ? false : true;

    }

    /**
     * Get the {@link UUID} of the player.
     * 
     * @return {@link UUID}
     */
    public UUID getUUID() {

        return this.uuid;

    }

    /**
     * Get the username of the player.
     * 
     * @return {@link String} - Username of the player.
     */
    public String getUsername() {

        Logging.infoToConsole(this.onlinePlayer + " " + this.offlinePlayer);

        if (this.onlinePlayer != null)
            return this.onlinePlayer.getName();
        else if (this.offlinePlayer.getName() != null)
            return this.offlinePlayer.getName();
        else
            return "Invalid Username";

    }

    /**
     * Get the player's class level.
     * 
     * @return {@link Integer} - Player's class level.
     */
    public int getLevel() {

        return this.level;

    }

    /**
     * Get the player's class name.
     * 
     * @return {@link String} - The player's class name.
     */
    public String getClassName() {

        return this.className;

    }

    /**
     * Check if the player is valid.
     * 
     * @return {@link Boolean}
     */
    public boolean isValid() {

        return this.isValid;

    }

    /**
     * Check if the player is online.
     * 
     * @return {@link Boolean}
     */
    public boolean isOnline() {

        return this.isOnline;

    }

    /**
     * Check if the player has a {@link Resident} object.
     * 
     * @return {@link Boolean}
     */
    public boolean isResident() {

        return this.isResident;

    }

    /**
     * Check if the player has a {@link Town} object.
     * 
     * @return {@link Boolean}
     */
    public boolean hasTown() {

        return this.hasTown;

    }

    /**
     * Check if the player has a {@link Nation} object.
     * 
     * @return {@link Boolean}
     */
    public boolean hasNation() {

        return this.hasNation;

    }

    /**
     * Get the class' base health.
     * 
     * @return {@link Float} The class' base health.
     */
    public float getBaseHealth() {

        return this.baseHealth;

    }

    /**
     * Get the class' modified health.
     * 
     * @return {@link Float} The class' modified health.
     */
    public float getModifiedHealth() {

        return this.modifiedHealth;

    }

    /**
     * Get the class' base experience.
     * 
     * @return {@link Float} The class' base experience.
     */
    public float getBaseExperience() {

        return this.baseExperience;

    }

    /**
     * Get the class' current experience.
     * 
     * @return {@link Float} The class' current experience.
     */
    public float getTotalExperience() {

        return this.experienceTotal;

    }

    /**
     * Get the class' calculated experience max.
     * 
     * @return {@link Float} The class' calculated experience max.
     */
    public float getMaxExperience() {

        return this.experienceMax;

    }

    /**
     * Get the resident object of this player.
     * 
     * @return {@link Resident} - Towny Resident Object
     */
    public Resident getResident() {

        return this.resident;

    }

    /**
     * Get the town object of this player.
     * 
     * @return {@link Town} - Towny Town Object
     */
    public Town getTown() {

        return this.town;

    }

    /**
     * Get the nation object of this player.
     * 
     * @return {@link Nation} - Towny Nation Object
     */
    public Nation getNation() {

        return this.nation;

    }

    /**
     * Get when the player has last logged in.
     * 
     * @return {@link Long} - Player's last login.
     */
    public long getLastLogin() {

        return this.lastLogin;

    }

    /**
     * Get when the player has last logged out.
     * 
     * @return {@link Long} - Player's last logout.
     */
    public long getLastLogout() {

        return this.lastLogout;

    }

    /**
     * Get the player's total playtime..
     * 
     * @return {@link Long} - Player's total playtime.
     */
    public long getPlaytime() {

        return this.playtime;

    }

    /**
     * Get the player's current temperature.
     * 
     * @return {@link Double} - Player's current temperature.
     */
    public double getCurrentTemperature() {

        return this.currentTemperature;

    }

    /**
     * Get the temperature adjustment for armor.
     * 
     * @return {@link Double} - Calculated armor adjustment.
     */
    public double getArmorAdjustment() {

        return this.armorAdjustment;

    }

    /**
     * Get the temperature adjustment for the player's environment.
     * 
     * @return {@link Double} - Calculated environment adjustment.
     */
    public double getEnvironmentAdjustment() {

        return this.environmentAdjustment;

    }

    /**
     * Get the max temperature the player can handle.
     * 
     * @return {@link Double} - Max temperature the player can handle.
     */
    public double getMaxTemperature() {

        return this.maxTemperature;

    }

    /**
     * Get the class configuration of the player.
     * 
     * Genearally, you should not need to do this.
     * 
     * @return {@link ClassConfig} - Class configuration of the player.
     */
    public ClassConfig getClassConfig() {

        return this.classConfig;

    }

    /**
     * Apply the given modifiers.
     */
    public void applyClassStats() {

        return;

    }

    /**
     * Save the character to SQL.
     */
    public void saveCharacter() {

        // TODO: Create method for adding experience

        MySQLHandler.setClassLevel(uuid, level);

    }

    public String toString() {

        String string = "";

        string += "<(" + this.uuid + ") UN:";
        string += this.username + " CN:";
        string += this.className + " LV:";
        string += this.level + " BH:";
        string += this.baseHealth + " MH:";
        string += this.modifiedHealth + " PT:";
        string += this.playtime + " LI:";
        string += this.lastLogin + " LO:";
        string += this.lastLogout + " BE:";
        string += this.baseExperience + " ET:";
        string += this.experienceTotal + " EM:";
        string += this.experienceMax + ">";

        return string;

    }

    /**
     * Print the player's information.
     * 
     * @return {@link String}
     */
    public String printPlayer() {

        return "";

    }

    /**
     * Calculate the temperature adjustemnet for armor.
     * 
     * @return {@link Double}
     */
    public double calculateArmorAdjustment() {

        if (!isOnline())
            return 0;

        PlayerInventory inventory = this.onlinePlayer.getInventory();
        ItemStack[] playerArmor = inventory.getArmorContents();

        double mod = 0;

        for (ItemStack armor : playerArmor) {

            if (armor != null) {

                String material = armor.getType().toString();

                switch (material) {

                    case "IRON_HELMET": {
                        mod += 1.5;
                    }
                    case "IRON_CHESTPLATE": {
                        mod += 3.0;
                    }
                    case "IRON_LEGGINGS": {
                        mod += 2.0;
                    }
                    case "IRON_BOOTS": {
                        mod += 1.0;
                    }

                    case "GOLD_HELMET": {
                        mod += 1.2;
                    }
                    case "GOLD_CHESTPLATE": {
                        mod += 2.6;
                    }
                    case "GOLD_LEGGINGS": {
                        mod += 1.6;
                    }
                    case "GOLD_BOOTS": {
                        mod += 0.6;
                    }

                    case "CHAINMAIL_HELMET": {
                        mod += 0.8;
                    }
                    case "CHAINMAIL_CHESTPLATE": {
                        mod += 1.7;
                    }
                    case "CHAINMAIL_LEGGINGS": {
                        mod += 1.2;
                    }
                    case "CHAINMAIL_BOOTS": {
                        mod += 0.4;
                    }

                    case "LEATHER_HELMET": {
                        mod += 0.4;
                    }
                    case "LEATHER_CHESTPLATE": {
                        mod += 1.2;
                    }
                    case "LEATHER_LEGGINGS": {
                        mod += 0.8;
                    }
                    case "LEATHER_BOOTS": {
                        mod += 0.2;
                    }

                }

            }

        }

        return mod;

    }

    /**
     * Calculate the temperature adjustemnet for the player's environment.
     * 
     * @return {@link Double}
     */
    public double calculateEnvironmentAdjustment() {

        if (!isOnline())
            return 0;

        double currentTime = this.onlinePlayer.getWorld().getTime();
        double mod = 0;

        boolean[] environment = checkSurroundingArea();

        boolean inSkylight = checkInSkylight();
        boolean isInside = !inSkylight; // inSkylight ? true : environment[0];
        boolean isNearHeatSource = environment[1];

        if (isNearHeatSource)
            mod += 3.2;

        // Sunrise
        if (currentTime > 23000 && currentTime < 2000) {

            mod += 0.4;

            if (isInside)
                mod += -1.2;
            else
                mod += 0.6;

        }

        // Noon
        if (currentTime > 2000 && currentTime < 12000) {

            mod = 0.6;

            if (isInside)
                mod += -1.2;
            else
                mod += 1.4;

        }

        // Sunset
        if (currentTime > 12000 && currentTime < 13000) {

            mod = -0.4;

            if (isInside)
                mod += -1.2;
            else
                mod += -0.6;

        }

        // Night
        if (currentTime > 13000 && currentTime < 23000) {

            mod = -0.4;

            if (isInside)
                mod += -1.2;
            else
                mod += -1.0;

        }

        return mod;

    }

    /**
     * Set the player as offline.
     * 
     * <p>
     * This will set the {@link Player} to null.
     * <p>
     * This will set the {@link OfflinePlayer} to its designated object.
     */
    public void setOffline() {

        this.isOnline = false;
        this.onlinePlayer = null;
        this.offlinePlayer = Bukkit.getOfflinePlayer(this.uuid);

    }

    /**
     * Set the player as online.
     * 
     * This will set the {@link OnlinePlayer} to its designated object.
     * This will set the {@link OfflinePlayer} to null.
     */
    public void setOnline() {

        this.isOnline = true;
        this.onlinePlayer = Bukkit.getPlayer(this.uuid);
        this.offlinePlayer = null;

    }

    /**
     * Check if the player has the sun above them.
     * 
     * @return true if there is no block above the player.
     */
    private boolean checkInSkylight() {

        if (!isOnline())
            return false;

        Block block = this.onlinePlayer.getLocation().getBlock();
        boolean found = true;

        for (int i = 0; i < 255; i++) {

            if (!block.getType().equals(Material.AIR)) {

                found = false;
                break;

            }
            ;

        }

        Logging.infoToConsole("Player is in skylight: " + found);

        return found;

    }

    /**
     * Checks if a given player is surrounded by blocks.
     * 
     * @return { ifInEmptySpace, ifAroundFireSource }.
     */
    private boolean[] checkSurroundingArea() {

        // TODO: CALC > PLAYER-Y + 2

        // EMPTY,
        boolean found[] = { false, false };
        int radius = 10;

        if (!isOnline()) {

            found[0] = found[1] = true;

            return found;
        }

        Block block = this.onlinePlayer.getLocation().getBlock();

        for (int i = 0; i < 180; i++) {

            double y = block.getY() + 2;
            double x = block.getX() + Math.sin((double) i) * radius;
            double z = block.getZ() + Math.cos((double) i) * radius;

            Location location = new Location(block.getWorld(), x, y, z);

            Block nBlock = location.getBlock();

            if (nBlock != null) {

                found[0] = true;

            }

            if (nBlock.getType().equals(Material.FIRE) || nBlock.getType().equals(Material.CAMPFIRE)
                    || nBlock.getType().equals(Material.LAVA)) {

                found[1] = true;

            }

        }

        return found;

    }
}
