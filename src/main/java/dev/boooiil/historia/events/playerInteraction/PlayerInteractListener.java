package dev.boooiil.historia.events.playerInteraction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.boooiil.historia.handlers.playerInteraction.RightClickAir;
import dev.boooiil.historia.handlers.playerInteraction.RightClickAnvil;
import dev.boooiil.historia.handlers.playerInteraction.RightClickStonecutter;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerRightClickAir(PlayerInteractEvent event) {


        if (event.getAction() == Action.RIGHT_CLICK_AIR) {

            RightClickAir rightClickAir = new RightClickAir(event);
            rightClickAir.doInteraction();

        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            RightClickAnvil rightClickAnvil = new RightClickAnvil(event);
            rightClickAnvil.doInteraction();

            RightClickStonecutter rightClickStonecutter = new RightClickStonecutter(event);
            rightClickStonecutter.doInteraction();

        }

    }

    
}
