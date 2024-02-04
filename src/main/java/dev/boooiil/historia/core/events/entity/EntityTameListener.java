package dev.boooiil.historia.core.events.entity;

import dev.boooiil.historia.core.handlers.entity.EntityTameHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class EntityTameListener implements Listener {

    @EventHandler
    public void onEntityTame(EntityTameEvent event) {

        EntityTameHandler entityTameHandler = new EntityTameHandler(event);
        entityTameHandler.doDetermineTamedAnimalAndRunTame();

    }

}
