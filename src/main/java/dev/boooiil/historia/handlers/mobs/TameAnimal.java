package dev.boooiil.historia.handlers.mobs;

import org.bukkit.event.entity.EntityTameEvent;

import dev.boooiil.historia.classes.enums.ExperienceTypes.AnimalSources;
import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.Logging;

public class TameAnimal {
    
    HistoriaPlayer historiaPlayer;
    EntityTameEvent event;

    public TameAnimal(HistoriaPlayer historiaPlayer, EntityTameEvent event) {
        this.historiaPlayer = historiaPlayer;
        this.event = event;
    }

    public void doTameAnimal() {

        if (historiaPlayer.getProficiency().getSkills().canTameAnimals()) {

            historiaPlayer.increaseExperience(AnimalSources.TAME_ANIMAL.getKey());

        } else {

            Logging.infoToPlayer("You have no idea what to do with this thing!", historiaPlayer.getUUID());
            event.setCancelled(true);

        }
        
    }

}
