package dev.boooiil.historia.core.handlers.entity;

import org.bukkit.entity.Breedable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityBreedEvent;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.experience.AnimalSources;
import dev.boooiil.historia.core.proficiency.skills.Skills.SkillType;
import dev.boooiil.historia.core.util.Logging;

public class EntityBreedHandler {

    EntityBreedEvent event;

    public EntityBreedHandler(EntityBreedEvent event) {
        this.event = event;
    }

    public void doDetermineBreedAnimalAndRunBreed() {

        switch (event.getEntity().getType()) {
            case PIG:
            case COW:
            case SHEEP:
            case CHICKEN:
            case RABBIT:
            case TURTLE:
            case FOX:
            case WOLF:
            case PARROT:
            case OCELOT:
                doBreedAnimal();
                break;

            default:
                break;
        }
    }

    private void doBreedAnimal() {
        // not sure what other entities can breed
        // shouldn't (TM) be a problem
        Player breeder = (Player) event.getBreeder();
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(breeder.getUniqueId());

        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.TAME_ANIMALS)) {
            Logging.infoToPlayer("Unfortunately you were never told about the birds and bees.",
                    historiaPlayer.getUUID());
            event.setCancelled(true);

            ((Breedable) event.getFather()).setBreed(false);
            ((Breedable) event.getMother()).setBreed(false);

            return;
        }

        historiaPlayer.increaseExperience(AnimalSources.BREED_ANIMAL.getKey());

    }
}
