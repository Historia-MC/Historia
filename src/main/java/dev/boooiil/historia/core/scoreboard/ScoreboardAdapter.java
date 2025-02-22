package dev.boooiil.historia.core.scoreboard;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.core.util.KyoriUtils;

import java.util.UUID;

/**
 * Scoreboard utility to manipulate player scoreboards.
 */
@NullMarked
public class ScoreboardAdapter {

    /** Scoreboard associated with this adapter. */
    private final Scoreboard scoreboard;
    /** Objective associated with this adapter. */
    private Objective objective;

    public ScoreboardAdapter() {

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

        if (scoreboardManager == null) {
            throw new NullPointerException("Scoreboard Manager is null!");
        }
        scoreboard = scoreboardManager.getNewScoreboard();

    }

    /**
     * Create a header for your scoreboard.
     * <p>
     * HEADER <--
     * </p
     * <p>
     * LINE
     * </p>
     * <p>
     * ...
     * </p>
     * 
     * @param header - the header to set.
     */
    public void createHeader(String header) {

        // TODO: adapt this to paper component

        objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(),
                Criteria.create(UUID.randomUUID().toString()), KyoriUtils.textComponent(header));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    /**
     * Create a line for your scoreboard.
     * <p>
     * HEADER
     * </p
     * <p>
     * LINE <--
     * </p>
     * <p>
     * ...
     * </p>
     * 
     * @param position - the position to set the line.
     * @param content  - the header to set.
     */
    public void addLine(int position, String content) {

        objective.getScore(ChatColor.translateAlternateColorCodes('&', content)).setScore(position);

    }

    /**
     * Add this scoreboard to the player.
     * 
     * @param player - The player to add this scoreboard to.
     */
    public void addToPlayer(Player player) {

        player.setScoreboard(scoreboard);

    }

}
