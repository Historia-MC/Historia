package dev.boooiil.historia.core.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import dev.boooiil.historia.core.handlers.entity.EntityBreedHandler;

public class EntityBreedListener implements Listener {

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {

        EntityBreedHandler handler = new EntityBreedHandler(event);

        handler.doDetermineBreedAnimalAndRunBreed();

    }

}
