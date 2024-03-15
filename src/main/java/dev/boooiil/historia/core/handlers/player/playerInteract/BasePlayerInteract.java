package dev.boooiil.historia.core.handlers.player.playerInteract;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

class BasePlayerInteract {

    protected PlayerInteractEvent event;

    BasePlayerInteract(PlayerInteractEvent event) {
        this.event = event;
    }

    protected Player getPlayer() {
        return event.getPlayer();
    }

    protected Action getAction() {
        return event.getAction();
    }

    protected HistoriaPlayer getHistoriaPlayer() {
        return PlayerStorage.getPlayer(getPlayer().getUniqueId());
    }

    protected Block getBlock() {
        return event.getClickedBlock();
    }

    protected ItemStack getHeldItem() {
        return event.getItem();
    }

    protected ItemStack getOffHandItem() {
        return getPlayer().getInventory().getItemInOffHand();
    }

    protected boolean blockIsType(Material material) {
        return getBlock().getType() == material;
    }

}
