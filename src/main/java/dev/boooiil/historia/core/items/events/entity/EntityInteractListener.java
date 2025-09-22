package dev.boooiil.historia.core.items.events.entity;

import dev.boooiil.historia.core.items.handlers.executor.ExecutorTriggerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
