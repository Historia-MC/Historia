package dev.boooiil.historia.core.player;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.player.culture.Cultures;
import dev.boooiil.historia.core.proficiency.Proficiency;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.experience.AllSources;
import dev.boooiil.historia.core.proficiency.stats.Stats;
import dev.boooiil.historia.core.proficiency.stats.Stats.BodyStatsType;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.core.util.NumberUtils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

//TODO: Add a method to check the player's armor level and attack level.

/**
 * Represents a player in the Historia plugin, extending {@link BasePlayer}.
 */
@NullMarked
public class HistoriaPlayer extends BasePlayer {

    /** The culture of the user. */
    Cultures culture;

    /** The player's level. */
    private int level;

    /** The last login of the user. */
    private long lastLogin;
    /** The last logout of the user. */
    private long lastLogout;
    /** The playtime of the user. */
    private long playtime;

    /** The max health of the user. */
    private float maxHealth;
    /** The modified health of the user. */
    private float modifiedHealth;

    /** The current temperature of the user. */
    private double currentTemperature;
    /** The max temperature of the user. */
    private double maxTemperature;

    /** The current experience of the user. */
    private double currentExperience;
    /** The max experience of the user. */
    private double maxExperience;

    /** The proficiency of the user. */
    private Proficiency proficiency;
    private Stats stats;

    /** When the user was last saved. */
    private long lastSaved;

    /**
     * Default constructor, will return invalid player.
     */
    public HistoriaPlayer() {
        super(null);

        CoreLogger.debugToConsole("Constructing new HistoriaPlayer object with UUID null.");
    }

    /**
     * Create a default HistoriaUser.
     * 
     * @param uuid - UUID of the player.
     */
    public HistoriaPlayer(UUID uuid) {

        super(uuid);

        CoreLogger.debugToConsole("Constructing new HistoriaPlayer object with UUID " + uuid + ".");

        // Base health and multiplier will get determined when we finish the class
        // config.
        // Experience max will just be experience * multiplier.

        this.culture = Cultures.NONE;
        this.proficiency = new Proficiency(ProficiencyName.NONE.getKey());
        this.level = 1;
        this.currentExperience = 0;
        this.maxExperience = NumberUtils.roundDouble(Math.pow(this.level, 1.68), 2);
        this.lastLogin = 0;
        this.lastLogout = 0;
        this.playtime = 0;

        // Set this explicitly in the config
        this.modifiedHealth = 0;

    }

    /**
     * Initialize a HistoriaPlayer with specified details.
     * 
     * @param uuid        - UUID of the player.
     * @param username    - Username of the player.
     * @param proficiency - Proficiency of the player.
     * @param culture     - Culture of the player.
     * @param level       - Level of the player.
     * @param experience  - Experience of the player.
     * @param login       - Time of the last login.
     * @param logout      - Time of the last logout.
     * @param playtime    - Playtime of the player in seconds.
     */
    public HistoriaPlayer(UUID uuid, String username, ProficiencyName proficiency, Cultures culture,
            int level, double experience, long login, long logout, long playtime) {
        super(uuid);

        this.culture = culture;
        this.username = username;
        this.proficiency = new Proficiency(proficiency.getKey());
        this.level = level;
        this.currentExperience = experience;
        this.lastLogin = login;
        this.lastLogout = logout;
        this.playtime = playtime;

    }

    /**
     * Set the proficiency of the player.
     * 
     * @param proficiency - Proficiency to be set.
     */
    public void setProficiency(ProficiencyName proficiency) {
        this.proficiency = new Proficiency(proficiency.getKey());
    }

    /**
     * Set the culture of the player.
     * 
     * @param culture - Culture to be set.
     */

    public void setCulture(Cultures culture) {
        this.culture = culture;
    }

    /**
     * 
     * Set the username of the player.
     * 
     * @param username - Username to be set.
     */

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Set the last login of the player.
     * 
     * @param lastLogin - Last login time to be set.
     */
    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Set the last logout of the player.
     * 
     * @param lastLogout - Last logout time to be set.
     */
    public void setLastLogout(long lastLogout) {
        this.lastLogout = lastLogout;
    }

    /**
     * Set the modified health of the player.
     * 
     * @param modifiedHealth - Modified health to be set.
     */
    public void setModifiedHealth(float modifiedHealth) {
        this.modifiedHealth = modifiedHealth;
    }

    /**
     * Set current experience of the player.
     * 
     * @param currentExperience - Current experience to be set.
     */
    public void setCurrentExperience(double currentExperience) {
        this.currentExperience = currentExperience;
    }

    /**
     * Set max experience of the player.
     * 
     * @param experienceMax - Max experience to be set.
     */
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

    public Stats getStats() {return this.stats; };

    /**
     * Get the proficiency of the player.
     * 
     * @return the proficiency of the player.
     */
    public Proficiency getProficiency() {

        return this.proficiency;

    }

    /**
     * Get the culture of the player.
     * 
     * @return the culture of the player.
     */
    public Cultures getCulture() {
        return this.culture;
    }

    /**
     * Get the class' base health.
     * 
     * @return {@link Float} The class' base health.
     */
    public float getBaseHealth() {

        return this.proficiency.getStats().getBodyStats().getLevel(BodyStatsType.HEALTH);

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
     * Save the character to SQL.
     */
    public void saveCharacter() {

        HistoriaCore.getDatabaseHandler().saveUser(this);

        // Main.getDatabaseHandler().setProficiency(this.getUUID(),
        // this.getProficiency().getName());
        // Main.getDatabaseHandler().setProficiencyLevel(this.getUUID(),
        // this.getLevel());
        // Main.getDatabaseHandler().setCurrentExperience(this.getUUID(),
        // this.getCurrentExperience());

        this.lastSaved = System.currentTimeMillis();

    }

    /**
     * Get when the user was last saved.
     * 
     * @return when the user was last saved.
     */
    public long getLastSaved() {

        return this.lastSaved;

    }

    /**
     * Set the temperature of the player.
     * 
     * @param temperature The temperature to set.
     */
    public void setTemperature(double temperature) {

        this.currentTemperature = temperature;

    }

    /**
     * Change the proficiency of the user.
     * 
     * @param proficiency The proficiency to set.
     */
    public void changeProficiency(NamespacedKey proficiency) {
        CoreLogger
                .debugToConsole("Player ", this.getUsername(), "(",
                        this.getUUID().toString(),") is changing proficiency to ", proficiency.toString(), ".");

        if (!HistoriaCore.PROFICIENCY_REGISTRY.contains(proficiency)) {
            throw new IllegalArgumentException("Tried to apply proficiency to player" + this.getUsername() +
                    " but the proficiency " + proficiency + " does not exist in the registry.");
        }

        this.proficiency = new Proficiency(proficiency);

        saveCharacter();

        // validate user is online before loading the new stats
        if (isOnline()) {
            CoreLogger.debugToConsole("Player is online, applying new stats.");
            // TODO: handle applying of stats to proficiency change
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

            CoreLogger.infoToPlayer("You have leveled up to level " + getLevel() + "!", this.getUUID());

        } else {

            setCurrentExperience(getCurrentExperience() + incomeModified);

        }

    }

    /**
     * Decreases the player's experience based on the source provided.
     * 
     * @param source The source from which the experience is to be decreased.
     */
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

            CoreLogger.infoToPlayer("You have leveled down to level " + getLevel() + "!", this.getUUID());

        } else if (getLevel() > 1) {

            setCurrentExperience(getCurrentExperience() - incomeModified);

        }

    }

    @Override
    public String toString() {

        CoreLogger.debugToConsole("HP TS");
        CoreLogger.debugToConsole("super", super.toString());

        StringBuilder sb = new StringBuilder();

        sb.append("HistoriaPlayer");
        sb.append("{");
        sb.append("\"basePlayer\":" + super.toString() + ", ");
        sb.append(JSONUtils.fromValue("culture", culture.getNoun().toLowerCase()) + ", ");
        sb.append(JSONUtils.fromValue("level", level) + ", ");
        sb.append(JSONUtils.fromValue("lastLogin", lastLogin) + ", ");
        sb.append(JSONUtils.fromValue("lastLogout", lastLogout) + ", ");
        sb.append(JSONUtils.fromValue("playtime", playtime) + ", ");
        sb.append(JSONUtils.fromValue("maxHealth", maxHealth) + ", ");
        sb.append(JSONUtils.fromValue("modifiedHealth", modifiedHealth) + ", ");
        sb.append(JSONUtils.fromValue("currentTemperature", currentTemperature) + ", ");
        sb.append(JSONUtils.fromValue("maxTemperature", maxTemperature) + ", ");
        sb.append(JSONUtils.fromValue("currentExperience", currentExperience) + ", ");
        sb.append(JSONUtils.fromValue("maxExperience", maxExperience) + ", ");
        sb.append("\"proficiency\":" + proficiency.toString() + ", ");
        sb.append(JSONUtils.fromValue("lastSaved", lastSaved));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {
        CoreLogger.debugToConsole("HP TJ");
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("\"basePlayer\":" + super.toJSON() + ", ");
        sb.append(JSONUtils.fromValue("culture", culture.getNoun().toLowerCase()) + ", ");
        sb.append(JSONUtils.fromValue("level", level) + ", ");
        sb.append(JSONUtils.fromValue("lastLogin", lastLogin) + ", ");
        sb.append(JSONUtils.fromValue("lastLogout", lastLogout) + ", ");
        sb.append(JSONUtils.fromValue("playtime", playtime) + ", ");
        sb.append(JSONUtils.fromValue("maxHealth", maxHealth) + ", ");
        sb.append(JSONUtils.fromValue("modifiedHealth", modifiedHealth) + ", ");
        sb.append(JSONUtils.fromValue("currentTemperature", currentTemperature) + ", ");
        sb.append(JSONUtils.fromValue("maxTemperature", maxTemperature) + ", ");
        sb.append(JSONUtils.fromValue("currentExperience", currentExperience) + ", ");
        sb.append(JSONUtils.fromValue("maxExperience", maxExperience) + ", ");
        sb.append("\"proficiency\":" + proficiency.toJSON() + ", ");
        sb.append(JSONUtils.fromValue("lastSaved", lastSaved));
        sb.append("}");

        return sb.toString();
    }
}
