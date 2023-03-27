package dev.boooiil.historia.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.classes.OreDrop;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

/**
 * It's a listener that listens for a player to break a block, and if the block
 * is a valid ore, it
 * drops the item that the ore is supposed to drop.
 */
public class PlayerBreakBlock implements Listener {

    OreConfig oreConfig = Config.getOreConfig();

    // It's a method that listens for a player to break a block, and if the block is
    // a valid ore, it
    // drops the item that the ore is supposed to drop.
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        Block block = event.getBlock();
        Material material = block.getType();

        Logging.infoToConsole(material.toString());

        if (oreConfig.isValidOre(material.toString())) {

            OreDrop drop = oreConfig.getDropFromChance(material.toString(), historiaPlayer.getClassName());

            if (drop != null) {

                event.setCancelled(true);
                block.getWorld().dropItemNaturally(block.getLocation(), drop);
                block.setType(Material.AIR);

            }

        }

    }

}
