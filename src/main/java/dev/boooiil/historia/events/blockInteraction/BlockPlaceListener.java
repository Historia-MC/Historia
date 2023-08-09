package dev.boooiil.historia.events.blockInteraction;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import dev.boooiil.historia.handlers.blockInteraction.PlayerPlaceBlock;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        new PlayerPlaceBlock(event).doPlace();

    }


    
}
