package dev.boooiil.historia.core.runnable;

import dev.boooiil.historia.core.database.internal.TemperatureStorage;
import dev.boooiil.historia.core.temperature.TemperatureManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TemperaturePollRunnable extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            TemperatureManager temperatureManager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
            temperatureManager.updateTemperature();
        }

    }

}