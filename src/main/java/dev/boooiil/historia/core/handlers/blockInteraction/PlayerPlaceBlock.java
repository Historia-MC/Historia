package dev.boooiil.historia.core.handlers.blockInteraction;

import dev.boooiil.historia.core.classes.enums.proficiency.SkillType;
import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.dependents.Permissions;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlaceBlock {
    
    private final BlockPlaceEvent event;

    public PlayerPlaceBlock(BlockPlaceEvent event) {

        this.event = event;

    }
    
    public void doPlace() {

        if (event.getBlock().getType() == Material.LADDER) {
            
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

            if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.LADDER_BYPASS)) return;
            if (Permissions.canPlaceBlock(event.getPlayer(), event.getBlock())) return;
        
            event.getPlayer().getWorld().setBlockData(event.getBlock().getLocation(), event.getBlock().getBlockData().clone());
            event.getItemInHand().setAmount(event.getItemInHand().getAmount() - 1);

        }
    }
}
