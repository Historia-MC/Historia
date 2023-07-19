package dev.boooiil.historia.handlers.pvp;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.util.Logging;

public class PlayerShootBow {
    
    EntityShootBowEvent event;

    public PlayerShootBow(EntityShootBowEvent event) {
        this.event = event;
    }

    public void doShoot() {
     
        if (!(event.getEntity() instanceof Player)) return;
        if (!event.getConsumable().getItemMeta().hasEnchant(Enchantment.ARROW_FIRE)) return;

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getUniqueId(), false);

        if (!historiaPlayer.getProficiency().canUseRanged()) {

            event.setCancelled(true);
            
            // shouldn't need this, just a client bug if the items are missing
            //((Player) event.getEntity()).getInventory().addItem(event.getConsumable());

            Logging.infoToPlayer("You do not know how to use this weapon.", event.getEntity().getUniqueId());
            return;

        }

        else {
            event.getProjectile().setFireTicks(1000);
        }
    }
}
