package dev.boooiil.historia.handlers.playerInteraction;

import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import dev.boooiil.historia.util.NumberUtils;

public class RightClickChicken extends BaseInteractionEventEntity {

    LivingEntity entity;

    public RightClickChicken(PlayerInteractEntityEvent event) {
        super(event);
    }

    @Override
    public void doInteraction() {

        // if not chicken
        if (entityIsType(EntityType.CHICKEN)) return;
        
        // if unable to extract feathers
        if (!this.getHistoriaPlayer().getProficiency().getSkills().canShearChickens()) return;

        // if not holding shears
        if (getHeldItem().getType() != Material.SHEARS) return;

        Ageable ageableEntity = (Ageable) getEntity();

        // if not adult
        if (!ageableEntity.isAdult()) return;

        Damageable damageableItem = (Damageable) getHeldItem().getItemMeta();
        damageableItem.setDamage(damageableItem.getDamage() + 1);

        if (damageableItem.getDamage() >= getHeldItem().getType().getMaxDurability()) {
            getHeldItem().setAmount(0);
            getPlayer().playSound(getLocation(), "entity.item.break", 1, 1);
        }

        ageableEntity.setBaby();
        getWorld().dropItemNaturally(getLocation(), new ItemStack(Material.FEATHER, NumberUtils.randomInt(1, 3)));

    }

}
