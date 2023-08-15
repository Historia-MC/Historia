package dev.boooiil.historia.core.handlers.blockInteraction;

import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;


public abstract class BaseBlockHandler {
    
    /**
     * The BlockBreakEvent object that represents the block break event.
     */
    protected BlockBreakEvent breakEvent;
    
    /**
     * The BlockPlaceEvent object that represents the block place event.
     */
    protected BlockPlaceEvent placeEvent;
    
    protected BlockEvent event;
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

    public Player getPlayer() {

        if (this.breakEvent != null) {
            return this.breakEvent.getPlayer();
        } else {
            return this.placeEvent.getPlayer();
        }

    }

    public Block getBlock() {
            
            if (this.breakEvent != null) {
                return this.breakEvent.getBlock();
            } else {
                return this.placeEvent.getBlock();
            }
    }
}
