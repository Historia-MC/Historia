package dev.boooiil.historia.core.items.events.player;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.boooiil.historia.core.items.handlers.executor.ExecutorTriggerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJumpListener implements Listener {

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
