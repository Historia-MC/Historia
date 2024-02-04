package dev.boooiil.historia.core.events.mobs;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.experience.AnimalSources;
import dev.boooiil.historia.core.proficiency.skills.SkillType;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class EntityBreedListener implements Listener {

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {

        // not sure what other entities can breed
        // shouldn't (TM) be a problem
        Player breeder = (Player) event.getBreeder();
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(breeder.getUniqueId(), false);

        if (historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.TAME_ANIMALS)) {
            historiaPlayer.increaseExperience(AnimalSources.BREED_ANIMAL.getKey());
        } else {

            event.setCancelled(true);

            ((Breedable) event.getFather()).setBreed(false);
            ((Breedable) event.getMother()).setBreed(false);

            Logging.infoToPlayer("Maybe there is someone else that can do this...", historiaPlayer.getUUID());
        }
    }

}
