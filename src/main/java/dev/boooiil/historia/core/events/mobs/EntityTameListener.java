package dev.boooiil.historia.core.events.mobs;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.handlers.mobs.TameAnimal;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class EntityTameListener implements Listener {

    @EventHandler
    public void onEntityTame(EntityTameEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getOwner().getUniqueId(), false);

        TameAnimal tameAnimal = new TameAnimal(historiaPlayer, event);
        tameAnimal.doTameAnimal();

    }

}
