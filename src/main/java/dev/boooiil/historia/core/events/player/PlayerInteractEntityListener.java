package dev.boooiil.historia.core.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import dev.boooiil.historia.core.handlers.player.playerInteractEntity.PlayerInteractEntityHandler;

public class PlayerInteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

        PlayerInteractEntityHandler playerInteractEntityHandler = new PlayerInteractEntityHandler(event);
        playerInteractEntityHandler.doDetermineEntityTypeAndRunInteraction();

    }

}
