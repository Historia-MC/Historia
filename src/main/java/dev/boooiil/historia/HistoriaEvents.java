package dev.boooiil.historia;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import dev.boooiil.historia.alerts.BoatNotify;
import dev.boooiil.historia.expiry.ExpiryManager;
import dev.boooiil.historia.misc.ChickenShearing;
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

        if (event.getPlayer().getInventory().getItem(event.getPreviousSlot()) != null) {
            ExpiryManager.initiate(event.getPlayer().getInventory().getItem(event.getPreviousSlot()), event.getPlayer(), event.getNewSlot());
        }
        if (event.getPlayer().getInventory().getItem(event.getNewSlot()) != null) {
            ExpiryManager.initiate(event.getPlayer().getInventory().getItem(event.getNewSlot()), event.getPlayer(), event.getNewSlot());
        } 

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        ExpiryManager.initiate(event.getCurrentItem(), event.getWhoClicked(), event.getSlot());
        
    }

    @EventHandler
    public void onBoatInteract(PlayerInteractEntityEvent event) {

        if (event.getRightClicked().getType() == EntityType.BOAT) BoatNotify.boatNotify(event.getHand(), event.getPlayer(), event.getRightClicked());
        if (event.getRightClicked().getType() == EntityType.CHICKEN) ChickenShearing.shearChicken(event.getPlayer(), event.getRightClicked());
        
    }
}