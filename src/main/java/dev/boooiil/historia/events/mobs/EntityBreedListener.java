package dev.boooiil.historia.events.mobs;

import org.bukkit.entity.Breedable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import dev.boooiil.historia.classes.enums.ExperienceTypes.AnimalSources;
import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.util.Logging;

public class EntityBreedListener implements Listener {

    @EventHandler
    public void onBreedAnimal(EntityBreedEvent event) {
    
        // not sure what other entities can breed
        // shouldn't (TM) be a problem
        Player breeder = (Player) event.getBreeder();
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(breeder.getUniqueId(), false);

        if (historiaPlayer.getProficiency().getSkills().canTameAnimals()) {
            historiaPlayer.increaseExperience(AnimalSources.BREED_ANIMAL.getKey());
        }
        else {

            event.setCancelled(true);
            
            ((Breedable) event.getFather()).setBreed(false);
            ((Breedable) event.getMother()).setBreed(false);

            Logging.infoToPlayer("Maybe there is someone else that can do this...", historiaPlayer.getUUID());
        }
    }

}
