package dev.boooiil.historia.runnable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.scoreboard.ScoreboardAdapter;
import dev.boooiil.historia.util.NumberUtils;
import net.md_5.bungee.api.ChatColor;

public class UpdateScoreboard extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {

            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), false);
            ScoreboardAdapter scoreboardAdapter = new ScoreboardAdapter();

            scoreboardAdapter.createHeader(ChatColor.GOLD + "" + ChatColor.BOLD + "HISTORIA");
            scoreboardAdapter.addLine(12, "");
            scoreboardAdapter.addLine(11, ChatColor.AQUA + "Proficiency: " + ChatColor.GRAY + historiaPlayer.getProficiency().getName());
            scoreboardAdapter.addLine(10, ChatColor.DARK_AQUA + "Level: " + ChatColor.GRAY + historiaPlayer.getLevel());
            scoreboardAdapter.addLine(9, ChatColor.AQUA + "Health: " + ChatColor.GRAY + NumberUtils.roundDouble(player.getHealth(), 2) + "/" + historiaPlayer.getBaseHealth());
            scoreboardAdapter.addLine(8, ChatColor.DARK_AQUA + "Hunger: " + ChatColor.GRAY + NumberUtils.roundDouble(player.getFoodLevel(), 2) + "/" + historiaPlayer.getProficiency().getStats().getBaseFood());
            scoreboardAdapter.addLine(7, ChatColor.AQUA + "Experience: " + ChatColor.GRAY + historiaPlayer.getCurrentExperience() + "/" + historiaPlayer.getMaxExperience());
            scoreboardAdapter.addLine(6, ChatColor.DARK_AQUA + "Temperature: " + ChatColor.GRAY + historiaPlayer.getCurrentTemperature());
            scoreboardAdapter.addLine(5, ChatColor.AQUA + "Weight: " + ChatColor.GRAY + null);
            scoreboardAdapter.addLine(4, ChatColor.DARK_AQUA + "Weapon Class: " + ChatColor.GRAY + historiaPlayer.getProficiency().getStats().getUsableWeaponTypes());
            scoreboardAdapter.addLine(3, ChatColor.AQUA + "Armor Class: " + ChatColor.GRAY + historiaPlayer.getProficiency().getStats().getUsableArmorTypes());

            scoreboardAdapter.addToPlayer(player);

        });
    }
    
}
