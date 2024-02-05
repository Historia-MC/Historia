package dev.boooiil.historia.core.handlers.block.blockPlace;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.dependents.Permissions;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.skills.SkillType;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;

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

}
