package dev.boooiil.historia.core.handlers.mobs;

import dev.boooiil.historia.core.classes.enums.ExperienceTypes.AnimalSources;
import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.event.entity.EntityTameEvent;

public class TameAnimal {
    
    private final HistoriaPlayer historiaPlayer;
    private final EntityTameEvent event;

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
