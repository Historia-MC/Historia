package dev.boooiil.historia.core.events.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import dev.boooiil.historia.core.handlers.block.blockPlace.BlockPlaceHandler;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        new BlockPlaceHandler(event).doPlace();

    }

}
