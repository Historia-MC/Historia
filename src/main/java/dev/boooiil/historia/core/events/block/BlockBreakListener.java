package dev.boooiil.historia.core.events.block;

import dev.boooiil.historia.core.dependents.Permissions;
import dev.boooiil.historia.core.handlers.block.blockBreak.BlockBreakHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
        if (!Permissions.canBreakBlock(event.getPlayer(), event.getBlock())) {

            event.setCancelled(true);

            return;

        }

        BlockBreakHandler blockHandler = new BlockBreakHandler(event);

        blockHandler.doBreak();

    }

}
