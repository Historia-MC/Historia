package dev.boooiil.historia.events.playerInteraction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.boooiil.historia.handlers.playerInteraction.RightClickAnvil;

public class PlayerRightClickBlock implements Listener {


    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            RightClickAnvil rightClickAnvil = new RightClickAnvil(event);
            rightClickAnvil.doInteraction();

        }

    }
    
}
