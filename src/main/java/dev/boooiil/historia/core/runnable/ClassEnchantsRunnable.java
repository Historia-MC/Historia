package dev.boooiil.historia.core.runnable;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.scheduler.BukkitRunnable;

public class ClassEnchantsRunnable extends BukkitRunnable {

    @Override
    public void run() {

        HistoriaCore.server().getOnlinePlayers().forEach(player -> {

            if (player.isOnline()) {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

                historiaPlayer.applySkillEnchants(player.getInventory());

            }

        });

    }

}
