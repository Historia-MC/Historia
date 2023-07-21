package dev.boooiil.historia.events.blockInteraction;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.configuration.specific.CropConfig;

public class BlockFromToListener implements Listener {

    @EventHandler
    public void onWaterBreakBlock(BlockFromToEvent event) {

        Block toBlock = event.getToBlock();
        CropConfig cropConfig = ConfigurationLoader.getCropConfig();

        if (cropConfig.isCrop(toBlock.getType())) {
            event.setCancelled(true);;
        }
        
    }
    
}
