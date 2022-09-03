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

    private boolean isOnline;

    private int level;

    private int baseHealth;
    private int modifiedHealth;

    private long lastLogin;
    private long lastLogout;
    private long playtime;

    private double currentTemperature;
    private double armorAdjustment;
    private double environmentAdjustment;
    private double maxTemperature;

    private float baseExperience;
    private float experienceTotal;
    private float experienceMax;

    private ClassConfig classConfig;

    private Player onlinePlayer;
    private OfflinePlayer offlinePlayer;

    private Resident resident;
    private Town town;
    private Nation nation;

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

    }

    public UUID getUUID() {

        return this.uuid;

    }

    public String getUsername() {

        Logging.infoToConsole(this.onlinePlayer + " " + this.offlinePlayer);

        if (this.onlinePlayer != null)
            return this.onlinePlayer.getName();
        else if (this.offlinePlayer.getName() != null)
            return this.offlinePlayer.getName();
        else
            return "Invalid Username";

    }

    public int getLevel() {

        return this.level;

    }

    public String getClassName() {

        return this.className;

    }

    public boolean isOnline() {

        return this.isOnline;

    }

    public Resident getResident() {

        return this.resident;

    }

    public Town getTown() {

        return this.town;

    }

    public Nation getNation() {

        return this.nation;

    }

    public long getLastLogin() {

        return this.lastLogin;

    }

    public long getLastLogout() {

        return this.lastLogout;

    }

    public long getPlaytime() {

        return this.playtime;

    }

    public double getCurrentTemperature() {

        return this.currentTemperature;

    }

    public double getArmorAdjustment() {

        return this.armorAdjustment;

    }

    public double getEnvironmentAdjustment() {

        return this.environmentAdjustment;

    }

    public double getMaxTemperature() {

        return this.maxTemperature;

    }

    public ClassConfig getClassConfig() {

        return this.classConfig;

    }

    public void applyClassStats() {

        return;

    }

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

    public String printPlayer() {

        return "";

    }

    public double calculateArmorAdjustment() {

        if (this.onlinePlayer == null)
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

    public double calculateEnvironmentAdjustment() {

        if (this.onlinePlayer == null)
            return 0;

        double currentTime = this.onlinePlayer.getWorld().getTime();
        double mod = 0;

        boolean[] environment = checkSurroundingArea();

        boolean inSkylight = checkInSkylight();
        boolean isInside = inSkylight ? true : environment[0];
        boolean isNearHeatSource = environment[1];

        if (isNearHeatSource)
            mod += 3.2;
        if (isInside)
            mod += -1.2;

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

    public void setOffline() {

        this.isOnline = false;
        this.onlinePlayer = null;
        this.offlinePlayer = Bukkit.getOfflinePlayer(this.uuid);

    }

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

        if (this.onlinePlayer == null)
            return true;

        Block block = this.onlinePlayer.getLocation().getBlock();
        boolean found = false;

        for (int i = 0; i < 255; i++) {

            if (!block.getType().equals(Material.AIR)) {

                found = true;
                break;

            }
            ;

        }

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

        if (this.onlinePlayer == null) {

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
