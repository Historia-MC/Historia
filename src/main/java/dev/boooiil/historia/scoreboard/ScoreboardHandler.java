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
import dev.boooiil.historia.util.NumberUtils;
import net.md_5.bungee.api.ChatColor;

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
        Objective objective = scoreboard.registerNewObjective(player.getUniqueId().toString(), Criteria.create("HISTORIA_PROFICIENCY") , ChatColor.BLUE + "" + ChatColor.BOLD + "HISTORIA");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("").setScore(12);
        objective.getScore(ChatColor.BLUE + "Proficiency: " + ChatColor.GRAY + historiaPlayer.getProficiency().getName()).setScore(11);
        objective.getScore(ChatColor.BLUE + "Level: " + ChatColor.GRAY + historiaPlayer.getLevel()).setScore(10);;
        objective.getScore(ChatColor.BLUE + "Health: " + ChatColor.GRAY + NumberUtils.roundDouble(player.getHealth(), 2) + "/" + historiaPlayer.getBaseHealth()).setScore(9);;
        objective.getScore(ChatColor.BLUE + "Hunger: " + ChatColor.GRAY + NumberUtils.roundDouble(player.getFoodLevel(), 2) + "/" + historiaPlayer.getProficiency().getStats().getBaseFood()).setScore(8);
        objective.getScore(ChatColor.BLUE + "Experience: " + ChatColor.GRAY + historiaPlayer.getCurrentExperience() + "/" + historiaPlayer.getMaxExperience()).setScore(7);
        objective.getScore(ChatColor.BLUE + "Temperature: " + ChatColor.GRAY + historiaPlayer.getCurrentTemperature()).setScore(6);
        objective.getScore(ChatColor.BLUE + "Weight: " + ChatColor.GRAY + null).setScore(5);
        objective.getScore(ChatColor.BLUE + "Weapon Class: " + ChatColor.GRAY + historiaPlayer.getProficiency().getStats().getUsableWeaponTypes()).setScore(4);
        objective.getScore(ChatColor.BLUE + "Armor Class: " + ChatColor.GRAY + historiaPlayer.getProficiency().getStats().getUsableArmorTypes()).setScore(3);

        player.setScoreboard(scoreboard);

    }

    public static void destroyPlayerBoard(Player player) {

        Scoreboard board = scoreboards.remove(player.getUniqueId());

        if (board != null) {
            Logging.debugToConsole("Scoreboard for " + player.getName() + " has been destroyed.");
        }


    }
}
