package dev.boooiil.historia.core.runnable;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.classes.proficiency.ClassEnchants;
import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import org.bukkit.scheduler.BukkitRunnable;

public class ClassEnchantsRunnable extends BukkitRunnable {

    @Override
    public void run() {

        Main.server().getOnlinePlayers().forEach(player -> {

            if (player.isOnline()) {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), false);

                ClassEnchants classEnchants = new ClassEnchants(historiaPlayer, player.getInventory());
                classEnchants.doApplyEnchants();

            }

        });

    }

}
