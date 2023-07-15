package dev.boooiil.historia.handlers.blockInteraction;

import org.bukkit.block.data.Ageable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.Logging;

public class CropHandler extends BaseBlockHandler {
    
    /**
     * Constructor for CropHandler when a crop block is broken.
     * @param event The BlockBreakEvent that triggered this handler.
     * @param historiaPlayer The HistoriaPlayer associated with this handler.
     */
    public CropHandler(BlockBreakEvent event, HistoriaPlayer historiaPlayer) {
        
        super(event, historiaPlayer);

    }

    /**
     * Constructor for CropHandler when a crop block is placed.
     * @param event The BlockPlaceEvent that triggered this handler.
     * @param historiaPlayer The HistoriaPlayer associated with this handler.
     */
    public CropHandler(BlockPlaceEvent event, HistoriaPlayer historiaPlayer) {

        super(event, historiaPlayer);

    }

    /**
     * Method to handle the breaking of a crop.
     * Determines if the crop is successfully harvested, and if the harvest is doubled.
     */
    public void doBreak() {

        //TODO: add farmer XP gain

        double harvestChance = historiaPlayer.getProficiency().getStats().getHarvestChance();
        double doubleHarvestChance = historiaPlayer.getProficiency().getStats().getDoubleHarvestChance();
        double harvestChanceRoll = Math.round((Math.random() * 100)) / 100;

        Logging.debugToConsole("Harvest Chance: " + harvestChance);
        Logging.debugToConsole("Harvest Chance Roll: " + harvestChanceRoll);
        Logging.debugToConsole("Double Harvest Chance: " + doubleHarvestChance);
        Logging.debugToConsole(historiaPlayer.toString());

        // if the player has failed to harvest the crop
        if (harvestChanceRoll >= harvestChance) {

            breakEvent.setCancelled(true);
            Logging.infoToPlayer("You failed to harvest the crop!", historiaPlayer.getUUID());

        }

        // if the player is able to double the harvest
        else if (harvestChanceRoll <= doubleHarvestChance) {

            breakEvent.getBlock().getDrops().forEach(item -> {

                breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(), item);

            });
            
            Logging.infoToPlayer("You harvested the crop and doubled the harvest!", historiaPlayer.getUUID());

            historiaPlayer.increaseExperience();

        }

    }

    /**
     * Method to handle the placing of a crop.
     * Determines if the crop is instantly grown.
     */
    public void doPlace() {

        double instantGrowthChance = historiaPlayer.getProficiency().getStats().getInstantGrowthChance();
        double harvestChanceRoll = Math.round((Math.random() * 100)) / 100;

        if (instantGrowthChance >= harvestChanceRoll) {

            Ageable crop = (Ageable) placeEvent.getBlock().getBlockData();

            crop.setAge(crop.getMaximumAge());
            Logging.infoToPlayer("You instantly grew the crop!", historiaPlayer.getUUID());

        }

    }
    
}