package dev.boooiil.historia.core.scoreboard;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class ScoreboardAdapter {

    private final Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardAdapter() {

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

        if (scoreboardManager == null) {
            throw new NullPointerException("Scoreboard Manager is null!");
        }
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
