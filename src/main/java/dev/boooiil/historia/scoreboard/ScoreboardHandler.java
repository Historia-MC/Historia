package dev.boooiil.historia.scoreboard;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.util.Logging;

public class ScoreboardHandler {

    private static ScoreboardManager scoreboardManager;
    private static HashMap<UUID, Scoreboard> scoreboards;

    public static void init() {

        scoreboards = new HashMap<UUID, Scoreboard>();

    }

    public static void initializePlayerBoard(Player player) {

        scoreboardManager = Bukkit.getScoreboardManager();

        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), false);
        Objective objective = scoreboard.registerNewObjective(player.getUniqueId().toString(), Criteria.create("HISTORIA_PROFICIENCY") , "-- PROFICIENCY --");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("Proficiency: " + historiaPlayer.getProficiency().getName()).setScore(11);
        objective.getScore("Level: " + historiaPlayer.getLevel()).setScore(10);;
        objective.getScore("Health: " + player.getHealth() + "/" + historiaPlayer.getBaseHealth()).setScore(9);;
        objective.getScore("Hunger: " + player.getFoodLevel() + "/" + historiaPlayer.getProficiency().getStats().getBaseFood()).setScore(8);
        objective.getScore("Experience: " + historiaPlayer.getCurrentExperience() + "/" + historiaPlayer.getMaxExperience()).setScore(7);
        objective.getScore("Temperature: " + historiaPlayer.getCurrentTemperature()).setScore(6);
        objective.getScore("Weight: " + null).setScore(5);
        objective.getScore("Weapon Class: " + historiaPlayer.getProficiency().getStats().getUsableWeaponTypes()).setScore(4);
        objective.getScore("Armor Class: " + historiaPlayer.getProficiency().getStats().getUsableArmorTypes()).setScore(3);

        scoreboards.put(player.getUniqueId(), scoreboard);
        player.setScoreboard(scoreboard);

    }

    public static void destroyPlayerBoard(Player player) {

        Scoreboard board = scoreboards.remove(player.getUniqueId());

        if (board != null)
            Logging.debugToConsole("Scoreboard for " + player.getName() + " has been destroyed.");

    }
}
