package dev.boooiil.historia.classes;

import java.util.Map;
import java.util.UUID;

import dev.boooiil.historia.mysql.MySQLHandler;

public class HistoriaPlayer {

    private String className;

    private int level;

    private int baseHealth;
    private int healthMultiplier;

    private float baseExperience;
    private float experienceTotal;
    private float experienceMax;
    private float experienceMultiplier;

    private String[] allowedWeapons;
    private String[] allowedTools;
    
    public HistoriaPlayer(UUID uuid) {

        //Base health and multiplier will get determined when we finish the class config.
        //Experience max will just be experience * multiplier.

        Map<String, String> user = MySQLHandler.getUser(uuid);

        className = user.get("class");
        level = Integer.parseInt(user.get("level"));

        experienceTotal = Float.parseFloat(user.get("experience"));

    }

    public void applyClassStats() {

        return;

    }
}
