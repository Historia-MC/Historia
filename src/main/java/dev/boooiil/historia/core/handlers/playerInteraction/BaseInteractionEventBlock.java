package dev.boooiil.historia.core.handlers.playerInteraction;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
