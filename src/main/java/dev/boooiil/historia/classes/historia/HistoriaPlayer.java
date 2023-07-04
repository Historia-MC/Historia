package dev.boooiil.historia.classes.historia;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.sql.mysql.MySQLHandler;
import dev.boooiil.historia.util.Logging;

/**
 * It's a class that holds all the information about a player
 */
public class HistoriaPlayer extends BasePlayer {

    private String className;

    private boolean isValid;

    private int level;

    private long lastLogin;
    private long lastLogout;
    private long playtime;

    private float maxHealth;
    private float modifiedHealth;

    private double currentTemperature;
    private double armorAdjustment;
    private double environmentAdjustment;
    private double maxTemperature;

    private double baseExperience;
    private double experienceTotal;
    private double experienceMax;

    private HistoriaClass historiaClass;

    protected Server server;


    /**
     * Default constrctor, will return invalid player.
     */
    public HistoriaPlayer() {
        super(null);
        this.isValid = false;
    }

    /**
     * General constructor.
     * 
     * @param uuid - UUID of the player.
     */
    public HistoriaPlayer(UUID uuid, Server server) {

        super(uuid);

        // TODO: GET TOWN AND NATION VALUES
        // TODO: SET PLAYTIME IN HISTORIA TABLE

        // Base health and multiplier will get determined when we finish the class
        // config.
        // Experience max will just be experience * multiplier.

        // Get an object where the key is a string and the value is also a string.
        // IE: { "key": "value" }, where "key" can be accessed using the .get() method.
        Map<String, String> user = MySQLHandler.getUser(uuid);

        this.className = user.get("class");
        this.historiaClass = new HistoriaClass(this.className);

        this.isValid = true;

        this.level = Integer.parseInt(user.get("level"));

        this.baseExperience = Math.pow(this.level - 1, this.historiaClass.getBaseExperienceMod());
        this.experienceTotal = Float.parseFloat(user.get("experience"));
        this.experienceMax = Math.pow(this.level, 1.68);

        this.lastLogin = Long.parseLong(user.get("login"));
        this.lastLogout = Long.parseLong(user.get("logout"));
        this.playtime = Long.parseLong(user.get("playtime"));

        this.armorAdjustment = calculateArmorAdjustment();
        this.environmentAdjustment = calculateEnvironmentAdjustment();

        // Set this explicitly in the config
        this.modifiedHealth = 0;

        // TODO: Calculate experience gain

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
     * Get the class' base health.
     * 
     * @return {@link Float} The class' base health.
     */
    public float getBaseHealth() {

        return this.historiaClass.getBaseHealth();

    }

    /**
     * Get the class' base health.
     * 
     * @return {@link Float} The class' base health.
     */
    public float getMaxHealth() {

        return this.maxHealth;

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
    public double getBaseExperience() {

        return this.baseExperience;

    }

    /**
     * Get the class' current experience.
     * 
     * @return {@link Float} The class' current experience.
     */
    public double getTotalExperience() {

        return this.experienceTotal;

    }

    /**
     * Get the class' calculated experience max.
     * 
     * @return {@link Float} The class' calculated experience max.
     */
    public double getMaxExperience() {

        return this.experienceMax;

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

        MySQLHandler.setClassLevel(this.getUUID(), level);

    }

    public String toString() {

        String string = "";

        string += "<(" + this.getUUID() + ") UN:";
        string += this.getUsername() + " CN:";
        string += this.className + " LV:";
        string += this.level + " BH:";
        string += this.getBaseHealth() + " MH:";
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

        if (!this.isOnline())
            return 0;

        Player player = Bukkit.getPlayer(this.getUUID());

        PlayerInventory inventory = player.getInventory();
        ItemStack[] playerArmor = inventory.getArmorContents();

        double mod = 0;

        // Essential check for plugin functions (realism). The easy fix would be using real weights but management deemed this too "personal".
        if (this.getUsername().equalsIgnoreCase("Mtndew98")) return Double.MAX_VALUE;

        for (ItemStack armor : playerArmor) {

            if (armor != null) {

                String material = armor.getType().toString();
                mod += Config.getArmorConfig().getObject(material).getWeight();

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

        Player player = Bukkit.getPlayer(this.getUUID());

        double currentTime = player.getWorld().getTime();
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
     * Check if the player has the sun above them.
     * 
     * @return true if there is no block above the player.
     */
    private boolean checkInSkylight() {

        if (!isOnline())
            return false;

        Player player = Bukkit.getPlayer(this.getUUID());

        Block block = player.getLocation().getBlock();
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

        Player player = Bukkit.getPlayer(this.getUUID());

        Block block = player.getLocation().getBlock();

        for (int i = 0; i < 180; i++) {

            double y = block.getY() + 2;
            double x = block.getX() + Math.sin((double) i) * radius;
            double z = block.getZ() + Math.cos((double) i) * radius;

            Location location = new Location(block.getWorld(), x, y, z);

            Block nBlock = location.getBlock();

            if (nBlock != null) {

                found[0] = true;

            }

            if (nBlock != null && (nBlock.getType().equals(Material.FIRE) || nBlock.getType().equals(Material.CAMPFIRE)
                    || nBlock.getType().equals(Material.LAVA))) {

                found[1] = true;

            }
            
        }

        return found;

    }
}
