package dev.boooiil.historia.events.playerInteraction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import dev.boooiil.historia.handlers.playerInteraction.RightClickChicken;

public class PlayerRightClickEntity implements Listener {

    @EventHandler
    public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) {

        RightClickChicken rightClickChicken = new RightClickChicken(event);

    }
    
}
