package dev.boooiil.historia.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.configuration.OreConfig.Drop;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void onPlayerKill(BlockBreakEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        Block block = event.getBlock();
        Material material = block.getType();

        Logging.infoToConsole(
            material.toString(),
            "Valid: " + OreConfig.isValidOre(material.toString()),
            historiaPlayer.getClassName()
        );

        if (OreConfig.isValidOre(material.toString())) {

            Drop drop = OreConfig.getDropFromChance(material.toString(), historiaPlayer.className);

            Logging.infoToConsole("[Player Break Block] Drop: " + (drop == null ? "Null" : drop.toString()));

            if (drop != null) {
                
                event.setCancelled(true);
                block.getWorld().dropItemNaturally(block.getLocation(), drop.getItemStack());
                block.setType(Material.AIR);
                
            }

        }

    }

    


}
