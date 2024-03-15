package dev.boooiil.historia.core.handlers.entity;

import org.bukkit.event.entity.EntityTameEvent;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.experience.AnimalSources;
import dev.boooiil.historia.core.proficiency.skills.Skills.SkillType;
import dev.boooiil.historia.core.util.Logging;

public class EntityTameHandler {

    EntityTameEvent event;

    public EntityTameHandler(EntityTameEvent event) {
        this.event = event;
    }

    public void doDetermineTamedAnimalAndRunTame() {

        // this is here in the off chance we want to
        // do something differently with different animals
        switch (event.getEntity().getType()) {
            case WOLF:
            case PARROT:
            case OCELOT:
                doTameAnimal();
                break;

            default:
                break;
        }

    }

    private void doTameAnimal() {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getOwner().getUniqueId());

        if (historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.TAME_ANIMALS)) {

            historiaPlayer.increaseExperience(AnimalSources.TAME_ANIMAL.getKey());

        } else {

            Logging.infoToPlayer("You have no idea what to do with this thing!", historiaPlayer.getUUID());
            event.setCancelled(true);

        }

    }

}
