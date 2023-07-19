package dev.boooiil.historia.events.playerInteraction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.boooiil.historia.handlers.playerInteraction.RightClickAir;

public class PlayerRightClickAir implements Listener {

    @EventHandler
    public void onPlayerRightClickAir(PlayerInteractEvent event) {


        if (event.getAction() == Action.RIGHT_CLICK_AIR) {

            RightClickAir rightClickAir = new RightClickAir(event);
            rightClickAir.doInteraction();

        }

    }

    
}
