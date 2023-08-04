package dev.boooiil.historia.events.pvp;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dev.boooiil.historia.handlers.pvp.PlayerDamaged;
import dev.boooiil.historia.handlers.pvp.PlayerDecreaseSharpness;
import dev.boooiil.historia.util.Logging;

public class EntityDamageByEntityListener implements Listener {
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        Logging.debugToConsole("EntityDamagedByEntity event created.");
        Logging.debugToConsole("Attacker: " + event.getDamager().getName());
        Logging.debugToConsole("Defender: " + event.getEntity().getName());

        Entity attacker = event.getDamager();
        Entity defender = event.getEntity();

        if (attacker instanceof Player && defender instanceof Player) {

            PlayerDamaged playerDamaged = new PlayerDamaged(event);
            playerDamaged.doAttack();
            playerDamaged.doDefend();

        }

        if (attacker instanceof Player) {

            if (((Player)attacker).getInventory().getItemInMainHand() != null) {

                Logging.debugToConsole("[EntityDamagedByEntity] Player was holding an item.");

                PlayerDecreaseSharpness playerDecreaseSharpness = new PlayerDecreaseSharpness(event);
                playerDecreaseSharpness.doDecrease();

            }

        }

    }

}
