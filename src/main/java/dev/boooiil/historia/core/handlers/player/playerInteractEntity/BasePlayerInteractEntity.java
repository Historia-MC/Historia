package dev.boooiil.historia.core.handlers.player.playerInteractEntity;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

class BasePlayerInteractEntity {

    protected PlayerInteractEntityEvent event;

    BasePlayerInteractEntity(PlayerInteractEntityEvent event) {
        this.event = event;
    }

    protected Entity getEntity() {
        return event.getRightClicked();
    }

    protected Player getPlayer() {
        return event.getPlayer();
    }

    protected HistoriaPlayer getHistoriaPlayer() {
        return PlayerStorage.getPlayer(getPlayer().getUniqueId());
    }

    protected ItemStack getHeldItem() {
        return event.getPlayer().getInventory().getItemInMainHand();
    }

    protected boolean entityIsType(EntityType entityType) {
        return getEntity().getType().equals(entityType);
    }

    protected World getWorld() {
        return getEntity().getWorld();
    }

    protected Location getLocation() {
        return getEntity().getLocation();
    }

}
