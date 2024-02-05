package dev.boooiil.historia.core.handlers.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.skills.SkillType;
import dev.boooiil.historia.core.util.NumberUtils;

public class EntityDeathHandler {

    EntityDeathEvent event;

    public EntityDeathHandler(EntityDeathEvent event) {
        this.event = event;
    }

    public void doDetermineDeathCauseAndRunDeath() {

        switch (event.getEntity().getType()) {
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
            case COW:
            case SHEEP:
            case PIG:
            case HORSE:
                doHarvestLeather();
            case CHICKEN:
                doExtraFeathers();
            default:
                doHarvestBones();
                break;
        }

    }

    private void doHarvestLeather() {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getKiller().getUniqueId(), true);
        List<ItemStack> drops = event.getDrops();

        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.HARVEST_LEATHER)) {
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

    }

    private void doHarvestBones() {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getKiller().getUniqueId(), true);

        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.HARVEST_BONES)) {
            return;
        }

        event.getDrops().add(new ItemStack(Material.BONE, NumberUtils.randomInt(1, 3)));

    }

    private void doExtraFeathers() {

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getKiller().getUniqueId(), true);

        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.CHANCE_EXTRA_FEATHERS)) {
            return;
        }

        float chance = 0.1f;
        float random = NumberUtils.random(0, 1);

        if (random <= chance) {
            event.getDrops().add(new ItemStack(Material.FEATHER, NumberUtils.randomInt(1, 3)));
        }
    }
}
