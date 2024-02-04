package dev.boooiil.historia.core.handlers.playerInteraction;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BaseInteractionEventEntity {

    protected PlayerInteractEntityEvent event;

    public BaseInteractionEventEntity(PlayerInteractEntityEvent event) {
        this.event = event;
    }

    public Entity getEntity() {
        return event.getRightClicked();
    }

    public Player getPlayer() {
        return event.getPlayer();
    }

    public HistoriaPlayer getHistoriaPlayer() {
        return PlayerStorage.getPlayer(getPlayer().getUniqueId(), false);
    }

    public ItemStack getHeldItem() {
        return event.getPlayer().getInventory().getItemInMainHand();
    }

    public void doInteraction() {

    }

    public boolean entityIsType(EntityType entityType) {
        return getEntity().getType().equals(entityType);
    }

    public World getWorld() {
        return getEntity().getWorld();
    }

    public Location getLocation() {
        return getEntity().getLocation();
    }

}
