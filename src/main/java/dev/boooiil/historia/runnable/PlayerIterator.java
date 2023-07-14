package dev.boooiil.historia.runnable;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.HistoriaPlugin;
import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerIterator extends BukkitRunnable {

    private final JavaPlugin plugin;

    public PlayerIterator(JavaPlugin plugin) {
        this.plugin = plugin;
    }

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
