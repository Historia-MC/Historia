package dev.boooiil.historia.core.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import dev.boooiil.historia.core.handlers.entity.EntityDeathHandler;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        EntityDeathHandler handler = new EntityDeathHandler(event);

        handler.doDetermineDeathCauseAndRunDeath();

    }
}
