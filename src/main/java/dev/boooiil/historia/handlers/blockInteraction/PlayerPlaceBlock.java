package dev.boooiil.historia.handlers.blockInteraction;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.dependents.towny.TownyHandler;

public class PlayerPlaceBlock {
    
    private BlockPlaceEvent event;

    public PlayerPlaceBlock(BlockPlaceEvent event) {

        this.event = event;

    }
    
    public void doPlace() {

        if (event.getBlock().getType() == Material.LADDER) {
            
            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

            if (!historiaPlayer.getProficiency().getSkills().hasLadderBypass()) return;
            if (TownyHandler.getPermissionByMaterial(event.getPlayer(), event.getPlayer().getLocation(), event.getBlock().getType())) return;
        
            event.getPlayer().getWorld().setBlockData(event.getBlock().getLocation(), event.getBlock().getBlockData().clone());
            event.getItemInHand().setAmount(event.getItemInHand().getAmount() - 1);

        }
    }
}
