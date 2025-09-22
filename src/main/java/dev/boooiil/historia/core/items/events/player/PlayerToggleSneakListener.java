package dev.boooiil.historia.core.items.events.player;

import dev.boooiil.historia.core.items.handlers.executor.ExecutorTriggerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
