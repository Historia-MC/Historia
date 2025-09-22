package dev.boooiil.historia.core.items.events.entity;

import dev.boooiil.historia.core.items.handlers.executor.ExecutorTriggerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;

public class EntityToggleSwimListener implements Listener {

    @EventHandler
    public void onEntityToggleSwim(EntityToggleSwimEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
