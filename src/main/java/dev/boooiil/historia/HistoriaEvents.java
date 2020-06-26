package dev.boooiil.historia;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import dev.boooiil.historia.alerts.BoatNotify;
import dev.boooiil.historia.alerts.DeathNotify;
import dev.boooiil.historia.expiry.ExpiryManager;
import dev.boooiil.historia.misc.ChickenShearing;
import dev.boooiil.historia.misc.FlameArrowHandler;
import dev.boooiil.historia.misc.PreventLavaPickup;

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

    @EventHandler
    public void onPickupLava(PlayerBucketFillEvent event) {
        
        if (event.getBlock().getType() == Material.LAVA) {
            if (PreventLavaPickup.onPickup(event.getPlayer())) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        if (ExpiryManager.handleExpiredFood(event.getItem(), event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        
        DeathNotify.deathAlert(event.getEntity(), event.getDeathMessage());
        
    }
}