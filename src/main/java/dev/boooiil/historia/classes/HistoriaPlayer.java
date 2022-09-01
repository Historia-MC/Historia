package dev.boooiil.historia.classes;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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

    private int level;

    private int baseHealth;
    private int modifiedHealth;

    private long lastLogin;
    private long lastLogout;
    private long playtime;

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

        //TODO: GET TOWN AND NATION VALUES
        //TODO: SET PLAYTIME IN HISTORIA TABLE

        //Base health and multiplier will get determined when we finish the class config.
        //Experience max will just be experience * multiplier.

        this.uuid = uuid;

        //Get an object where the key is a string and the value is also a string.
        //IE: { "key": "value" }, where "key" can be accessed using the .get() method.
        Map<String, String> user = MySQLHandler.getUser(uuid);

        this.className = user.get("class");
        this.level = Integer.parseInt(user.get("level"));
        this.experienceTotal = Float.parseFloat(user.get("experience"));

        this.classConfig = ClassConfig.getConfig(this.className);

        this.baseHealth = this.classConfig.baseHealth;

        this.lastLogin = Long.parseLong(user.get("login"));
        this.lastLogout = Long.parseLong(user.get("logout"));

        //Set this explicitly in the config
        this.modifiedHealth = 0;

        //TODO: Calculate experience gain

        this.onlinePlayer = Bukkit.getPlayer(uuid);
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        
        this.username = getUsername();
        this.playtime = Long.parseLong(user.get("playtime"));

        this.resident = TownyHandler.getResident(uuid);
        this.town = TownyHandler.getTown(uuid);
        this.nation = TownyHandler.getNation(uuid);

    }

    public UUID getUUID() {

        return this.uuid;

    }

    public String getUsername() {

        Logging.infoToConsole(this.onlinePlayer + " " + this.offlinePlayer);

        if (this.onlinePlayer != null) return this.onlinePlayer.getName();
        else if (this.offlinePlayer.getName() != null) return this.offlinePlayer.getName();
        else return "Invalid Username";

    }

    public int getLevel() {

        return this.level;

    }

    public String getClassName() {

        return this.className;

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

    public ClassConfig getClassConfig() {

        return this.classConfig;

    }

    public void applyClassStats() {

        return;

    }

    public void saveCharacter() {

        //TODO: Create method for adding experience

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
}
