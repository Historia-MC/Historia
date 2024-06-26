package dev.boooiil.historia.core.player;

import dev.boooiil.historia.core.database.DatabaseAdapter;
import dev.boooiil.historia.core.database.mysql.MySQLUserKeys;
import dev.boooiil.historia.core.proficiency.Proficiency;
import dev.boooiil.historia.core.proficiency.experience.AllSources;
import dev.boooiil.historia.core.util.Logging;
import dev.boooiil.historia.core.util.NumberUtils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.UUID;

//TODO: Add a method to check the player's armor level and attack level.

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

        // Base health and multiplier will get determined when we finish the class
        // config.
        // Experience max will just be experience * multiplier.

        // Get an object where the key is a string and the value is also a string.
        // IE: { "key": "value" }, where "key" can be accessed using the .get() method.
        Map<MySQLUserKeys, String> user = DatabaseAdapter.getUser(uuid);

        if (!user.get(MySQLUserKeys.USERNAME).equals("null")) {
            if (this.username == null || !this.username.equals(user.get(MySQLUserKeys.USERNAME))) {
                this.username = user.get(MySQLUserKeys.USERNAME);
            }
        }

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

        Player player = Bukkit.getPlayer(this.getUUID());

        // TODO: Need to make sure that the player is not losing health or food each
        // time they join if base health > 20
        // base health scale: getHealth() / getMaxHealth() * getHealthScale().
        double previousHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (healthAttribute.getBaseValue() != this.getProficiency().getStats().getBaseHealth()) {

            healthAttribute.setBaseValue(this.getProficiency().getStats().getBaseHealth());
            player.setHealth(
                    this.getProficiency().getStats().getBaseHealth() * (player.getHealth() / previousHealth));

        }

        player.setWalkSpeed(0.2f * (float) this.getProficiency().getStats().getBaseSpeed());

        player.setLevel(this.getLevel());

        // just dont bother with this, update food status on consume event
        // player.setFoodLevel(historiaPlayer.getProficiency().getStats().getBaseFood()
        // * (player.getFoodLevel()/20));

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

        this.proficiency = new Proficiency(proficiency);

        saveCharacter();

        // validate user is online before loading the new stats
        if (isOnline()) {
            Logging.debugToConsole("Player is online, applying new stats.");
            this.applyClassStats();
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

    public void applySkillEnchants(Inventory inventory) {

        inventory.iterator().forEachRemaining(item -> {

            if (item == null || item.getItemMeta() == null)
                return;

            if (this.getProficiency().getSkills().hasSkillEnchants()) {

                Enchantment enchant = this.getProficiency().getSkills().getSkillEnchantment(item.getType());
                ItemMeta itemMeta = item.getItemMeta();

                if (enchant != null && !itemMeta.hasEnchant(enchant)) {

                    addSkillEnchantToItem(item, enchant);

                }

                else if (enchant == null && itemMeta.hasEnchants()) {

                    removeSkillEnchantFromItem(item);

                }

            } else {

                if (item.getItemMeta().hasEnchants()) {

                    Logging.debugToConsole(item.getEnchantments().toString());

                    removeSkillEnchantFromItem(item);

                }

            }

        });

    }

    private void addSkillEnchantToItem(ItemStack item, Enchantment enchant) {

        if (item != null && item.getItemMeta() != null) {

            ItemMeta itemMeta = item.getItemMeta();

            Logging.debugToConsole(
                    this.getUsername() + " had an item in their inventory that wasn't enchanted.");

            itemMeta.addEnchant(enchant, 1, true);
            item.setItemMeta(itemMeta);

        }

    }

    private void removeSkillEnchantFromItem(ItemStack item) {

        if (item != null && item.getItemMeta() != null && !item.getItemMeta().hasEnchants()) {

            ItemMeta itemMeta = item.getItemMeta();

            Logging.debugToConsole(
                    this.getUsername() + " had an item in their inventory with an illegal enchant.");

            item.getItemMeta().getEnchants().forEach((enchant, level) -> {

                itemMeta.removeEnchant(enchant);

            });

            item.setItemMeta(itemMeta);

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
