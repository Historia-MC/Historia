package dev.boooiil.historia.core.handlers.playerInteraction;

import dev.boooiil.historia.core.classes.enums.proficiency.SkillType;
import dev.boooiil.historia.core.util.Logging;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class RightClickChicken extends BaseInteractionEventEntity {

    public RightClickChicken(PlayerInteractEntityEvent event) {
        super(event);
    }

    @Override
    public void doInteraction() {

        Logging.debugToConsole("[RightClickChicken] Player " + this.getPlayer().getName() + " right clicked an entity.");
        Logging.debugToConsole("[RightClickChicken] Player " + this.getPlayer().getName() + " has proficiency "
                + this.getHistoriaPlayer().getProficiency().getName() + ".");

        // if not chicken
        if (!entityIsType(EntityType.CHICKEN)) return;

        Logging.debugToConsole("[RightClickChicken] Entity is a chicken.");
        
        // if unable to extract feathers
        if (!this.getHistoriaPlayer().getProficiency().getSkills().hasSkill(SkillType.SHEAR_CHICKEN)) return;

        Logging.debugToConsole("[RightClickChicken] Player can shear chickens.");

        // if not holding shears
        if (getHeldItem().getType() != Material.SHEARS) return;

        Logging.debugToConsole("[RightClickChicken] Player is holding shears.");

        Ageable ageableEntity = (Ageable) getEntity();

        // if not adult
        if (!ageableEntity.isAdult()) return;

        Logging.debugToConsole("[RightClickChicken] Entity is an adult.");

        Damageable damageableItem = (Damageable) getHeldItem().getItemMeta();
        damageableItem.setDamage(damageableItem.getDamage() + 16);

        if (damageableItem.getDamage() >= getHeldItem().getType().getMaxDurability()) {
            getHeldItem().setAmount(0);
            getPlayer().playSound(getLocation(), "entity.item.break", 1, 1);
        }

        getHeldItem().setItemMeta(damageableItem);
        ageableEntity.setBaby();
        getWorld().dropItemNaturally(getLocation(), new ItemStack(Material.FEATHER, NumberUtils.randomInt(1, 3)));

    }

}
