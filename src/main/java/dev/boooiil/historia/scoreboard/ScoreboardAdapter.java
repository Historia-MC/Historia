package dev.boooiil.historia.scoreboard;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;

public class ScoreboardAdapter {

    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardAdapter() {

        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();

    }

    public void createHeader(String header) {

        objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(), Criteria.create(UUID.randomUUID().toString()) , header);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    public void addLine(int position, String content) {

        objective.getScore(ChatColor.translateAlternateColorCodes('&', content)).setScore(position);

    }

    public void addToPlayer(Player player) {

        player.setScoreboard(scoreboard);

    }

    
}
