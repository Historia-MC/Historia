package dev.boooiil.historia.core.runnable;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TemperaturePollRunnable extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            HistoriaPlayer hp = PlayerStorage.getPlayer(player.getUniqueId());

            hp.getTemperatureCalculator().poll();

        }

    }

}