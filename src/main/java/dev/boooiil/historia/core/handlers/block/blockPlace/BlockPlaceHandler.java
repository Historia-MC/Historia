package dev.boooiil.historia.core.handlers.block.blockPlace;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.dependents.Permissions;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.skills.SkillType;
import dev.boooiil.historia.core.util.Logging;
import dev.boooiil.historia.core.util.NumberUtils;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceHandler {

    private final BlockPlaceEvent event;

    public BlockPlaceHandler(BlockPlaceEvent event) {

        this.event = event;

    }

    public void doDetermineBlockType() {

        switch (event.getBlock().getType()) {

            case LADDER:
                doLadderBypass();
                break;

            default:
                doConsumptionBypass();
                break;
        }
    }

    private void doLadderBypass() {

        if (event.getBlock().getType() == Material.LADDER) {

            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

            if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.LADDER_BYPASS))
                return;
            if (Permissions.canPlaceBlock(event.getPlayer(), event.getBlock()))
                return;

            event.getPlayer().getWorld().setBlockData(event.getBlock().getLocation(),
                    event.getBlock().getBlockData().clone());
            event.getItemInHand().setAmount(event.getItemInHand().getAmount() - 1);

        }
    }

    private void doConsumptionBypass() {

        // guard against players who cannot place the block
        if (!Permissions.canPlaceBlock(event.getPlayer(), event.getBlock()))
            return;

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        // guard against players that do not have this skill
        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.CHANCE_NO_CONSUME_BLOCK))
            return;

        switch (event.getBlock().getType()) {

            case IRON_BLOCK:
            case DIAMOND_BLOCK:
            case EMERALD_BLOCK:
            case GOLD_BLOCK:
                return;

            default:
                break;
        }

        int chance = 1;
        int random = NumberUtils.randomInt(1, 100);

        if (random > chance)
            return;

        ItemStack item = new ItemStack(event.getBlock().getType(), 1);

        event.getPlayer().getInventory().addItem(item);

        Logging.infoToPlayer("Your skills allowed you to not consume a block!", historiaPlayer.getUUID());
    }

}
