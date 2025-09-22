package dev.boooiil.historia.core.items.events.entity;

import dev.boooiil.historia.core.items.handlers.executor.ExecutorTriggerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
