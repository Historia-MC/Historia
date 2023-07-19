package dev.boooiil.historia.handlers.playerInteraction;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;

import org.bukkit.event.block.Action;

public class BaseInteractionEventBlock {

    protected PlayerInteractEvent event;

    public BaseInteractionEventBlock(PlayerInteractEvent event) {
        this.event = event;
    }

    public Player getPlayer() {
        return event.getPlayer();
    }

    public Action getAction() {
        return event.getAction();
    }

    public HistoriaPlayer getHistoriaPlayer() {
        return PlayerStorage.getPlayer(getPlayer().getUniqueId(), false);
    }

    public Block getBlock() {
        return event.getClickedBlock();
    }

    public ItemStack getHeldItem() {
        return event.getItem();
    }

    public ItemStack getOffHandItem() {
        return getPlayer().getInventory().getItemInOffHand();
    }

    public void doInteraction() {

    }

    public boolean blockIsType(Material material) {
        return getBlock().getType() == material;
    }
    
}
