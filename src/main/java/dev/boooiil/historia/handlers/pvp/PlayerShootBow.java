package dev.boooiil.historia.handlers.pvp;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

public class PlayerShootBow {
    
    EntityShootBowEvent event;

    public PlayerShootBow(EntityShootBowEvent event) {
        this.event = event;
    }

    public void doShoot() {
     
        if (!(event.getEntity() instanceof Player)) return;
        if (!event.getConsumable().getItemMeta().hasEnchant(Enchantment.ARROW_FIRE)) return;

        event.getProjectile().setFireTicks(1000);

    }

}
