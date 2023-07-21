package dev.boooiil.historia.events.mobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.handlers.mobs.TameAnimal;

public class EntityTameListener implements Listener {

    @EventHandler
    public void onTameAnimal(EntityTameEvent event) {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getOwner().getUniqueId(), false);

        TameAnimal tameAnimal = new TameAnimal(historiaPlayer, event);
        tameAnimal.doTameAnimal();

    }
    
}
