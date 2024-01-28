package dev.boooiil.historia.core.classes.user;

import dev.boooiil.historia.core.classes.enums.database.MySQLUserKeys;
import dev.boooiil.historia.core.classes.enums.experience.AllSources;
import dev.boooiil.historia.core.classes.proficiency.Proficiency;
import dev.boooiil.historia.core.database.DatabaseAdapter;
import dev.boooiil.historia.core.handlers.connection.InitialStatLoader;
import dev.boooiil.historia.core.util.Logging;
import dev.boooiil.historia.core.util.NumberUtils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

//TODO: Add a method to check the player's armor level and attack level.
//TODO: Add methods to track xp gain and loss.

/**
 * It's a class that holds all the information about a player
 */
public class HistoriaPlayer extends BasePlayer {

    private int level;

    private long lastLogin;
    private long lastLogout;
    private long playtime;

    private float maxHealth;
    private float modifiedHealth;

    private double currentTemperature;
    private double maxTemperature;

    private double currentExperience;
    private double maxExperience;

    private Proficiency proficiency;

    private long lastSaved;

    protected Server server;

    /**
     * Default constructor, will return invalid player.
     */
    public HistoriaPlayer() {
        super(null);

        Logging.debugToConsole("Constructing new HistoriaPlayer object with UUID null.");
    }

    /**
     * General constructor.
     * 
     * @param uuid - UUID of the player.
     */
    public HistoriaPlayer(UUID uuid) {

        super(uuid);

        Logging.debugToConsole("Constructing new HistoriaPlayer object with UUID " + uuid + ".");

        // TODO: GET TOWN AND NATION VALUES
        // TODO: SET PLAYTIME IN HISTORIA TABLE

        // Base health and multiplier will get determined when we finish the class
        // config.
        // Experience max will just be experience * multiplier.

        // Get an object where the key is a string and the value is also a string.
        // IE: { "key": "value" }, where "key" can be accessed using the .get() method.
        Map<MySQLUserKeys, String> user = DatabaseAdapter.getUser(uuid);

        this.proficiency = new Proficiency(user.get(MySQLUserKeys.CLASS));

        this.level = Integer.parseInt(user.get(MySQLUserKeys.LEVEL));

        this.level = Math.max(this.level, 1);

        this.currentExperience = Float.parseFloat(user.get(MySQLUserKeys.EXPERIENCE));

        this.currentExperience = this.currentExperience < 0 ? 0 : this.currentExperience;

        this.maxExperience = NumberUtils.roundDouble(Math.pow(this.level, 1.68), 2);

        this.lastLogin = Long.parseLong(user.get(MySQLUserKeys.LOGIN));
        this.lastLogout = Long.parseLong(user.get(MySQLUserKeys.LOGOUT));
        this.playtime = Long.parseLong(user.get(MySQLUserKeys.PLAYTIME));

        // Set this explicitly in the config
        this.modifiedHealth = 0;

        // TODO: Calculate experience gain

    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastLogout(long lastLogout) {
        this.lastLogout = lastLogout;
    }

    public void setModifiedHealth(float modifiedHealth) {
        this.modifiedHealth = modifiedHealth;
    }

    public void setCurrentExperience(double currentExperience) {
        this.currentExperience = currentExperience;
    }

    public void setMaxExperience(double experienceMax) {
        this.maxExperience = experienceMax;
    }

    /**
     * Get the player's class level.
     * 
     * @return {@link Integer} - Player's class level.
     */
    public int getLevel() {

        return this.level;

    }

    public Proficiency getProficiency() {

        return this.proficiency;

    }

    /**
     * Get the class' base health.
     * 
     * @return {@link Float} The class' base health.
     */
    public float getBaseHealth() {

        return this.proficiency.getStats().getBaseHealth();

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
     * Get the class' current experience.
     * 
     * @return {@link Float} The class' current experience.
     */
    public double getCurrentExperience() {

        return NumberUtils.roundDouble(this.currentExperience, 2);

    }

    /**
     * Get the class' calculated experience max.
     * 
     * @return {@link Float} The class' calculated experience max.
     */
    public double getMaxExperience() {

        return this.maxExperience;

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
     * Get the player's total playtime.
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

        // TODO: create method or handler that applies the proficiency stats

    }

    /**
     * Save the character to SQL.
     */
    public void saveCharacter() {

        DatabaseAdapter.setProficiency(this.getUUID(), this.getProficiency().getName());
        DatabaseAdapter.setProficiencyLevel(this.getUUID(), this.getLevel());
        DatabaseAdapter.setCurrentExperience(this.getUUID(), this.getCurrentExperience());

        this.lastSaved = System.currentTimeMillis();

    }

    public long getLastSaved() {

        return this.lastSaved;

    }

    public void setTemperature(double temperature) {

        this.currentTemperature = temperature;

    }

    public void changeProficiency(String proficiency) {
        Logging.debugToConsole("Player " + this.getUsername() + "(" + this.getUUID() + ") is changing proficiency to "
                + proficiency + ".");

        // TODO: Adjust player modifiers based on new proficiency.

        this.proficiency = new Proficiency(proficiency);

        saveCharacter();

        // validate user is online before loading the new stats
        if (isOnline()) {
            Logging.debugToConsole("Player is online, applying new stats.");
            Player player = Bukkit.getPlayer(this.getUUID());
            InitialStatLoader initialStatLoader = new InitialStatLoader(player);
            initialStatLoader.apply();
        }

    }

    /**
     * Increase the player's experience.
     * 
     * @param source - The source of the experience.
     */
    public void increaseExperience(AllSources source) {

        if (source == null)
            return;
        if (!this.proficiency.getStats().hasIncomeSource(source))
            return;

        double incomeValue = this.proficiency.getStats().getIncomeValue(source);
        double incomeModified = incomeValue * this.level / 10;

        if ((getCurrentExperience()) + incomeModified >= getMaxExperience()) {

            double overflow = (getCurrentExperience() + incomeModified) - getMaxExperience();

            setLevel(getLevel() + 1);
            setCurrentExperience(overflow);
            setMaxExperience(NumberUtils.roundDouble(Math.pow(getLevel(), 1.68), 2));
            saveCharacter();

            Logging.infoToPlayer("You have leveled up to level " + getLevel() + "!", this.getUUID());

        } else {

            setCurrentExperience(getCurrentExperience() + incomeModified);

        }

    }

    public void decreaseExperience(AllSources source) {

        if (source == null)
            return;
        if (!this.proficiency.getStats().hasIncomeSource(source))
            return;

        double incomeValue = this.proficiency.getStats().getIncomeValue(source);
        double incomeModified = Math.pow(incomeValue * this.level / 10, 2);

        if ((getCurrentExperience()) - incomeModified <= 0 && getLevel() > 1) {

            setLevel(getLevel() - 1);
            setMaxExperience(NumberUtils.roundDouble(Math.pow(getLevel(), 1.68), 2));

            double overflow = (getCurrentExperience() - incomeModified) - getMaxExperience();

            if (overflow < getMaxExperience() && getLevel() != 1)
                setCurrentExperience(getMaxExperience() - overflow);
            else
                setCurrentExperience(0);

            saveCharacter();

            Logging.infoToPlayer("You have leveled down to level " + getLevel() + "!", this.getUUID());

        } else if (getLevel() > 1) {

            setCurrentExperience(getCurrentExperience() - incomeModified);

        }

    }

    public String toString() {

        String output = super.toString();

        output += "*** HISTORIA PLAYER *** \n";
        output += "Level: " + this.getLevel() + "\n";
        output += "Current Experience: " + this.getCurrentExperience() + "\n";
        output += "Max Experience: " + this.getMaxExperience() + "\n";
        output += "Last Login: " + this.getLastLogin() + "\n";
        output += "Last Logout: " + this.getLastLogout() + "\n";
        output += "Playtime: " + this.getPlaytime() + "\n";
        output += "*********************** \n";
        output += proficiency.toString();

        return output;

    }
}
