package dev.boooiil.historia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.expiry.ExpiryManager;
import dev.boooiil.historia.misc.FlameArrowHandler;

public class HistoriaEvents implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        FlameArrowHandler.onShoot(event.getEntity());
    }
    
    @EventHandler
    public void onArrowInBlock(ProjectileHitEvent event) {
        
        //If the arrow didn't hit an entity
        if (event.getHitEntity() == null) FlameArrowHandler.onBlockHit(event.getEntity(), event.getHitBlock());

    }

    @EventHandler
    public void onPlayerItemHeldSwitch(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
        Integer newItemSlot = event.getNewSlot();
        Integer oldItemSlot = event.getPreviousSlot();

        if (previousItem != null) ExpiryManager.initiate(previousItem, player, oldItemSlot);
        if (newItem != null) ExpiryManager.initiate(newItem, player, newItemSlot);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        ExpiryManager.initiate(event.getCurrentItem(), event.getWhoClicked(), event.getSlot());
        
    }

}