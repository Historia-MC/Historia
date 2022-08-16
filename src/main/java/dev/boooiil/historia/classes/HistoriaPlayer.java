package dev.boooiil.historia.classes;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.boooiil.historia.configuration.ClassConfig;
import dev.boooiil.historia.mysql.MySQLHandler;

public class HistoriaPlayer {

    public UUID uuid;

    public String className;

    public int level;

    public int baseHealth;
    public int modifiedHealth;

    public float baseExperience;
    public float experienceTotal;
    public float experienceMax;

    public ClassConfig classConfig;

    public Player player;
    
    public HistoriaPlayer(UUID uuid) {

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

        //Set this explicitly in the config
        this.modifiedHealth = 0;

        //TODO: Calculate experience gain

        this.player = Bukkit.getPlayer(uuid);

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
}
