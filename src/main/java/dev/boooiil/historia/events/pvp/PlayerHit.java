package dev.boooiil.historia.events.pvp;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dev.boooiil.historia.handlers.pvp.PlayerDamaged;
import dev.boooiil.historia.scoreboard.ScoreboardHandler;

public class PlayerHit implements Listener {
    
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {

        Entity attacker = event.getDamager();
        Entity defender = event.getEntity();

        if (attacker instanceof Player && defender instanceof Player) {

            PlayerDamaged playerDamaged = new PlayerDamaged(event);
            playerDamaged.doAttack();
            playerDamaged.doDefend();

            
            ScoreboardHandler.destroyPlayerBoard((Player) defender);
            ScoreboardHandler.initializePlayerBoard((Player) defender);
        
        }


        
        
    }

}
