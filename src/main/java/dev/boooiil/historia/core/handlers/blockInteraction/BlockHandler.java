package dev.boooiil.historia.core.handlers.blockInteraction;

import dev.boooiil.historia.core.classes.enums.ExperienceTypes.BlockSources;
import dev.boooiil.historia.core.classes.enums.ExperienceTypes.FarmingSources;
import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockHandler extends BaseBlockHandler {

    /**
     * Constructor for BlockHandler class that takes a BlockBreakEvent and a HistoriaPlayer as parameters.
     * 
     * @param event the BlockBreakEvent that triggered the handler
     * @param historiaPlayer the HistoriaPlayer associated with the event
     */
    public BlockHandler(BlockBreakEvent event, HistoriaPlayer historiaPlayer) {

        super(event, historiaPlayer);

    }

    /**
     * Constructor for BlockHandler class that takes a BlockPlaceEvent and a HistoriaPlayer as parameters.
     * 
     * @param event the BlockPlaceEvent that triggered the handler
     * @param historiaPlayer the HistoriaPlayer associated with the event
     */
    public BlockHandler(BlockPlaceEvent event, HistoriaPlayer historiaPlayer) {

        super(event, historiaPlayer);

    }

    /**
     * Method that handles the breaking of a block. It checks if the block is a valid ore or log, and if so, drops the corresponding item(s) and sets the block to air. It also checks if the player has a chance to get double drops and increases their experience accordingly.
     */
    public void doBreak() {

        if (breakEvent.getBlock().getType().toString().contains("LOG")) {

            if (historiaPlayer.getProficiency().getSkills().hasChanceExtraWood()) {

                Float doulbeDropChance = 0.05f;
                Float dropChanceRoll = (float) Math.round((Math.random() * 100)) / 100;

                boolean didDouble = dropChanceRoll <= doulbeDropChance;

                if (didDouble) {

                    breakEvent.setCancelled(true);
                    breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(),
                            breakEvent.getBlock().getDrops().iterator().next());
                    breakEvent.getBlock().getWorld().dropItemNaturally(breakEvent.getBlock().getLocation(),
                            breakEvent.getBlock().getDrops().iterator().next());
                    Logging.infoToPlayer("You have doubled your log drop!", historiaPlayer.getUUID());
                    historiaPlayer.increaseExperience(FarmingSources.CROP_BREAK.getKey());

                }

            }

        }

        else if (breakEvent.getBlock().getType().toString().contains("BEEHIVE")) {

            if (!historiaPlayer.getProficiency().getSkills().canBreakBeehive()) {

                breakEvent.setCancelled(true);
                Logging.infoToPlayer("You have no idea what to do with this thing!", historiaPlayer.getUUID());

            }

        }

        else {
        }

    }

    public void doPlace() {

        // TODO: check for ores

        if (historiaPlayer.getProficiency().getSkills().hasChanceNoConsumeBlock()) {

            placeEvent.setCancelled(true);
            placeEvent.getBlockPlaced().setType(placeEvent.getBlockPlaced().getType());
            Logging.infoToPlayer("You did not consume any resources for this block!", historiaPlayer.getUUID());
            historiaPlayer.increaseExperience(BlockSources.BLOCK_PLACE.getKey());

        }

    }

}
