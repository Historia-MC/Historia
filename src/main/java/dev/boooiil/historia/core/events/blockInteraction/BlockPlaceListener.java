package dev.boooiil.historia.core.events.blockInteraction;

import dev.boooiil.historia.core.handlers.blockBreakListener.PlayerPlaceBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        new PlayerPlaceBlock(event).doPlace();

    }

}
