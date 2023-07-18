package dev.boooiil.historia.handlers.playerInteraction;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;

import org.bukkit.event.block.Action;

public class BaseInteractionEvent {

    protected PlayerInteractEvent event;

    public BaseInteractionEvent(PlayerInteractEvent event) {
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
    
}
