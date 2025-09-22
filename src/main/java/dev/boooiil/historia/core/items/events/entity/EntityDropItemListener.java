package dev.boooiil.historia.core.items.events.entity;

import dev.boooiil.historia.core.items.handlers.executor.ExecutorTriggerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

public class EntityDropItemListener implements Listener {

    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
