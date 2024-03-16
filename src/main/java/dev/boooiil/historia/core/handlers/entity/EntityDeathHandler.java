package dev.boooiil.historia.core.handlers.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.experience.AnimalSources;
import dev.boooiil.historia.core.proficiency.experience.CombatSources;
import dev.boooiil.historia.core.proficiency.skills.Skills.SkillType;
import dev.boooiil.historia.core.util.NumberUtils;

public class EntityDeathHandler {

    EntityDeathEvent event;
    DamageCause cause;
    HistoriaPlayer historiaPlayerKiller;
    HistoriaPlayer historiaPlayerVictim;

    public EntityDeathHandler(EntityDeathEvent event) {
        this.event = event;
        this.cause = event.getEntity().getLastDamageCause().getCause();

        if (event.getEntity().getType() == EntityType.PLAYER)
            this.historiaPlayerVictim = PlayerStorage.getPlayer(event.getEntity().getUniqueId());

        if (event.getEntity().getKiller().getType() == EntityType.PLAYER)
            this.historiaPlayerKiller = PlayerStorage.getPlayer(event.getEntity().getKiller().getUniqueId());
    }

    public void doDetermineDeathCauseAndRunDeath() {

        if (historiaPlayerKiller == null) {
            return;
        }

        switch (event.getEntity().getType()) {
            case PLAYER:
                doDetermineCombatExperience();
                break;
            case COW:
            case SHEEP:
            case PIG:
            case CHICKEN:
            case RABBIT:
            case WOLF:
            case OCELOT:
            case HORSE:
            case DONKEY:
            case MULE:
            case LLAMA:
            case PARROT:
            case FOX:
            case POLAR_BEAR:
            case PANDA:
            case TURTLE:
            case DOLPHIN:
            case CAT:
                doDetermineHarvestType();
                break;
            default:
                break;
        }
    }

    private void doDetermineHarvestType() {

        switch (event.getEntity().getType()) {
            case SHEEP:
                doExtraWool();
                doHarvestBones();
                break;
            case COW:
            case PIG:
            case HORSE:
                doHarvestLeather();
                doHarvestBones();
                break;
            case CHICKEN:
                doExtraFeathers();
                doHarvestBones();
                break;
            default:
                doHarvestBones();
                break;
        }

    }

    private void doDetermineCombatExperience() {

        if (historiaPlayerKiller == null || historiaPlayerVictim == null) {
            return;
        }

        // if the killer used a bow
        if (cause == DamageCause.PROJECTILE) {
            // if the player has the ranged kill income source
            if (historiaPlayerKiller.getProficiency().getStats().hasIncomeSource(CombatSources.RANGED_KILL.getKey())) {
                historiaPlayerKiller.increaseExperience(CombatSources.RANGED_KILL.getKey());
            }
        }
        // if the player has the kill income source
        else if (historiaPlayerKiller.getProficiency().getStats().hasIncomeSource(CombatSources.KILL.getKey())) {
            historiaPlayerKiller.increaseExperience(CombatSources.KILL.getKey());
        }

        // death gives -2 experience at the moment
        // that is why we are increasing
        historiaPlayerVictim.increaseExperience(CombatSources.DEATH.getKey());
    }

    private void doHarvestLeather() {

        List<ItemStack> drops = event.getDrops();

        if (!historiaPlayerKiller.getProficiency().getSkills().hasSkill(SkillType.HARVEST_LEATHER)) {
            List<ItemStack> newDrops = new ArrayList<>();

            for (ItemStack drop : drops) {
                if (drop.getType() != Material.LEATHER) {
                    newDrops.add(drop);
                }
            }

            event.getDrops().clear();
            event.getDrops().addAll(newDrops);
            return;
        }

        // cow already drops leather
        if (event.getEntity().getType() == EntityType.COW) {
            return;
        }

        event.getDrops().add(new ItemStack(Material.LEATHER, NumberUtils.randomInt(1, 3)));

        this.historiaPlayerKiller.increaseExperience(AnimalSources.HARVEST_LEATHER.getKey());

    }

    private void doHarvestBones() {

        if (!historiaPlayerKiller.getProficiency().getSkills().hasSkill(SkillType.HARVEST_BONES)) {
            return;
        }

        event.getDrops().add(new ItemStack(Material.BONE, NumberUtils.randomInt(1, 3)));

        historiaPlayerKiller.increaseExperience(AnimalSources.HARVEST_BONES.getKey());

    }

    private void doExtraFeathers() {

        if (!historiaPlayerKiller.getProficiency().getSkills().hasSkill(SkillType.CHANCE_EXTRA_FEATHERS)) {
            return;
        }

        float chance = 0.1f;
        float random = NumberUtils.random(0, 1);

        if (random <= chance) {
            event.getDrops().add(new ItemStack(Material.FEATHER, NumberUtils.randomInt(1, 3)));
            historiaPlayerKiller.increaseExperience(AnimalSources.HARVEST_FEATHERS.getKey());
        }
    }

    private void doExtraWool() {

        if (!historiaPlayerKiller.getProficiency().getSkills().hasSkill(SkillType.CHANCE_EXTRA_WOOL)) {
            return;
        }

        float chance = 0.1f;
        float random = NumberUtils.random(0, 1);

        if (random <= chance) {

            Material woolType = null;

            for (ItemStack itemStack : event.getDrops()) {
                if (itemStack.getType().toString().contains("WOOL")) {
                    woolType = itemStack.getType();
                    break;
                }
            }

            if (woolType == null) {
                return;
            }

            event.getDrops().add(new ItemStack(woolType, NumberUtils.randomInt(1, 3)));

            historiaPlayerKiller.increaseExperience(AnimalSources.HARVEST_WOOL.getKey());
        }
    }
}
