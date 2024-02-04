package dev.boooiil.historia.core.events.player;

import dev.boooiil.historia.core.handlers.playerInteraction.RightClickChicken;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

        RightClickChicken rightClickChicken = new RightClickChicken(event);
        rightClickChicken.doInteraction();

    }

}
