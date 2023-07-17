package dev.boooiil.historia.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.HistoriaPlugin;
import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;

public class PlayerIterator extends BukkitRunnable {

    @Override
    public void run() {

        HistoriaPlugin.server().getOnlinePlayers().forEach(player -> {

            if (player.isOnline()) {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), false);

                ClassEnchants classEnchants = new ClassEnchants(historiaPlayer, player.getInventory());
                classEnchants.doApplyEnchants();

            }

        });

    }

}
