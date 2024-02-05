package dev.boooiil.historia.core.runnable;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.scheduler.BukkitRunnable;

public class ClassEnchantsRunnable extends BukkitRunnable {

    @Override
    public void run() {

        Main.server().getOnlinePlayers().forEach(player -> {

            if (player.isOnline()) {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), false);

                historiaPlayer.applySkillEnchants(player.getInventory());

            }

        });

    }

}
