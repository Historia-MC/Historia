package dev.boooiil.historia.handlers.blockInteraction;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;


public abstract class BaseBlockHandler {
    
    /**
     * The BlockBreakEvent object that represents the block break event.
     */
    protected BlockBreakEvent breakEvent;
    
    /**
     * The BlockPlaceEvent object that represents the block place event.
     */
    protected BlockPlaceEvent placeEvent;
    
    /**
     * The HistoriaPlayer object that represents the player who triggered the block interaction event.
     */
    protected HistoriaPlayer historiaPlayer;

    /**
     * This is the constructor of the BaseBlockHandler class that is used to initialize the common properties.
     * @param event The BlockBreakEvent object that represents the block break event.
     * @param historiaPlayer The HistoriaPlayer object that represents the player who triggered the block interaction event.
     */
    public BaseBlockHandler(BlockBreakEvent event, HistoriaPlayer historiaPlayer) {

        this.breakEvent = event;
        this.historiaPlayer = historiaPlayer;

    }

    /**
     * This is the constructor of the BaseBlockHandler class that is used to initialize the common properties.
     * @param event The BlockPlaceEvent object that represents the block place event.
     * @param historiaPlayer The HistoriaPlayer object that represents the player who triggered the block interaction event.
     */
    public BaseBlockHandler(BlockPlaceEvent event, HistoriaPlayer historiaPlayer) {

        this.placeEvent = event;
        this.historiaPlayer = historiaPlayer;

    }

}
