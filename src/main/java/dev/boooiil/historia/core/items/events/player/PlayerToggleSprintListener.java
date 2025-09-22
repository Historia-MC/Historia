package dev.boooiil.historia.core.items.events.player;

import dev.boooiil.historia.core.items.handlers.executor.ExecutorTriggerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class PlayerToggleSprintListener implements Listener {

    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
