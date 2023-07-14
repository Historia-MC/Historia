package dev.boooiil.historia.handlers.mobs;

import org.bukkit.event.entity.EntityTameEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;

public class TameAnimal {
    
    HistoriaPlayer historiaPlayer;
    EntityTameEvent event;

    public TameAnimal(HistoriaPlayer historiaPlayer, EntityTameEvent event) {
        this.historiaPlayer = historiaPlayer;
        this.event = event;
    }

    public void doTameAnimal() {

        if (historiaPlayer.getProficiency().getSkills().canTameAnimals()) {

            event.setCancelled(false);

        } else {

            event.setCancelled(true);

        }
        
    }

}
