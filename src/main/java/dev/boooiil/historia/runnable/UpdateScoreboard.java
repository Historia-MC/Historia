package dev.boooiil.historia.runnable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.scoreboard.ScoreboardHandler;

public class UpdateScoreboard extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {

            ScoreboardHandler.initializePlayerBoard(player);

        });
    }
    
}
