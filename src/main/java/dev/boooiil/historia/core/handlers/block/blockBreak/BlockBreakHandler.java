package dev.boooiil.historia.core.handlers.block.blockBreak;

import dev.boooiil.historia.core.handlers.block.BaseBlockHandler;
import dev.boooiil.historia.core.proficiency.experience.FarmingSources;
import dev.boooiil.historia.core.proficiency.skills.SkillType;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakHandler extends BaseBlockHandler {

    /**
     * Constructor for BlockHandler class that takes a BlockBreakEvent and a
     * HistoriaPlayer as parameters.
     * 
     * @param event          the BlockBreakEvent that triggered the handler
     * @param historiaPlayer the HistoriaPlayer associated with the event
     */
    public BlockBreakHandler(BlockBreakEvent event) {

        super(event);

    }

    /**
     * Method that handles the breaking of a block. It checks if the block is a
     * valid ore or log, and if so, drops the corresponding item(s) and sets the
     * block to air. It also checks if the player has a chance to get double drops
     * and increases their experience accordingly.
     */
    public void doBreak() {

        if (breakEvent.getBlock().getType().toString().contains("LOG")) {

            if (historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.CHANCE_EXTRA_WOOD)) {

                float doubleDropChance = 0.05f;
                float dropChanceRoll = (float) Math.round((Math.random() * 100)) / 100;

                boolean didDouble = dropChanceRoll <= doubleDropChance;

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

            if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.BREAK_BEEHIVE)) {

                breakEvent.setCancelled(true);
                Logging.infoToPlayer("You have no idea what to do with this thing!", historiaPlayer.getUUID());

            }

        }

    }

}
