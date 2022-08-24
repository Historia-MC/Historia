package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.configuration.OreConfig.Drop;
import dev.boooiil.historia.configuration.OreConfig.OreManager;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void onPlayerKill(BlockBreakEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        OreManager oreManager = new OreManager(event.getBlock().getType().name(), historiaPlayer.getClassName());

        Logging.infoToConsole(
            event.getBlock().getType().name(),
            "Valid: " + oreManager.isValid(),
            historiaPlayer.getClassName()
        );

        if (oreManager.isValid()) {

            Drop drop = oreManager.getDropFromChance();

            event.getBlock().getDrops().clear();
            event.getBlock().getDrops().add(drop.getItemStack());

        }

    }

    


}
