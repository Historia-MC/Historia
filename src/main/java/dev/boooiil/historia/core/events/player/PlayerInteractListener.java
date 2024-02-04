package dev.boooiil.historia.core.events.player;

import dev.boooiil.historia.core.handlers.player.playerInteract.PlayerInteractHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        PlayerInteractHandler playerInteractHandler = new PlayerInteractHandler(event);

        playerInteractHandler.doDetermineActionTypeAndRunInteraction();

    }

}
