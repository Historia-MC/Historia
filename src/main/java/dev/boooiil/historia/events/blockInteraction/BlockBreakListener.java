package dev.boooiil.historia.events.blockInteraction;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.dependents.towny.TownyHandler;
import dev.boooiil.historia.handlers.blockInteraction.BlockHandler;
import dev.boooiil.historia.handlers.blockInteraction.CropHandler;

/**
 * It's a listener that listens for a player to break a block, and if the block
 * is a valid ore, it
 * drops the item that the ore is supposed to drop.
 */
public class BlockBreakListener implements Listener {

    // It's a method that listens for a player to break a block, and if the block is
    // a valid ore, it
    // drops the item that the ore is supposed to drop.
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        // if the player can't break the block, cancel the event.
        if (!TownyHandler.getPermissionByMaterial(event.getPlayer(), event.getPlayer().getLocation(), event.getBlock().getType())) {

            event.setCancelled(true);

            return;

        }

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        Block block = event.getBlock();

        if (block.getBlockData() instanceof Ageable) {

            CropHandler cropHandler = new CropHandler(event, historiaPlayer);

            cropHandler.doBreak();

        }

        else {

            BlockHandler blockHandler = new BlockHandler(event, historiaPlayer);

            blockHandler.doBreak();

        }

    }

}
