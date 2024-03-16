package dev.boooiil.historia.core.handlers.block.blockBreak;

import dev.boooiil.historia.core.handlers.block.BaseBlockHandler;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.experience.BlockSources;
import dev.boooiil.historia.core.proficiency.experience.FarmingSources;
import dev.boooiil.historia.core.proficiency.skills.Skills.SkillType;
import dev.boooiil.historia.core.util.Logging;
import dev.boooiil.historia.core.util.NumberUtils;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakHandler extends BaseBlockHandler {

    /**
     * Constructor for BlockHandler class that takes a BlockBreakEvent and a
     * HistoriaPlayer as parameters.
     * 
     * @param event the BlockBreakEvent that triggered the handler
     */
    public BlockBreakHandler(BlockBreakEvent event) {

        super(event);

    }

    public void doDetermineBlockType() {

        switch (this.getBlock().getType()) {

            case OAK_LOG:
            case SPRUCE_LOG:
            case BIRCH_LOG:
            case JUNGLE_LOG:
            case ACACIA_LOG:
            case DARK_OAK_LOG:
                if (historiaPlayer.getProficiency().getName() == ProficiencyName.LUMBERJACK) {
                    historiaPlayer.increaseExperience(BlockSources.BLOCK_BREAK.getKey());
                }
                doExtraWoodChance();
                break;

            case BEEHIVE:
                doBreakBeehive();
                break;

            case COAL_ORE:
            case IRON_ORE:
            case GOLD_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
                doExtraOreChance();
                if (historiaPlayer.getProficiency().getName() == ProficiencyName.MINER)
                    historiaPlayer.increaseExperience(BlockSources.ORE_BREAK.getKey());
                break;

            case STONE:
            case GRANITE:
            case DIORITE:
            case ANDESITE:
            case DEEPSLATE:
            case GRAVEL:
                if (historiaPlayer.getProficiency().getName() == ProficiencyName.MINER)
                    historiaPlayer.increaseExperience(BlockSources.BLOCK_BREAK.getKey());
                break;

            case WHEAT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case MELON:
            case PUMPKIN:
            case SUGAR_CANE:
            case CACTUS:
            case BAMBOO:
            case NETHER_WART:
            case COCOA:
            case CHORUS_FLOWER:
            case CHORUS_PLANT:
            case KELP:
                // TODO: separate sources for crops that harvest in less quantities
                if (historiaPlayer.getProficiency().getName() == ProficiencyName.FARMER)
                    historiaPlayer.increaseExperience(FarmingSources.CROP_BREAK.getKey());

            default:
                break;
        }
    }

    private void doExtraWoodChance() {

        if (this.historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.CHANCE_EXTRA_WOOD)) {

            float doubleDropChance = 0.05f;
            float dropChanceRoll = NumberUtils.random(0, 1);

            boolean didDouble = dropChanceRoll <= doubleDropChance;

            ItemStack item = new ItemStack(breakEvent.getBlock().getType(), 1);

            historiaPlayer.increaseExperience(FarmingSources.CROP_BREAK.getKey());

            if (didDouble) {

                this.getBlock().getDrops().add(item);

                historiaPlayer.increaseExperience(FarmingSources.CROP_BREAK.getKey());

                Logging.infoToPlayer("You have doubled your log drop!", historiaPlayer.getUUID());

            }

        }
    }

    private void doBreakBeehive() {

        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.BREAK_BEEHIVE)) {

            breakEvent.setCancelled(true);
            Logging.infoToPlayer("You have no idea what to do with this thing!", historiaPlayer.getUUID());

        }

    }

    private void doExtraOreChance() {

        // cant do this here, have to do it in Historia-Ores

    }
}
