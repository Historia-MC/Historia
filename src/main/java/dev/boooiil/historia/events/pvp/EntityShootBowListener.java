package dev.boooiil.historia.events.pvp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import dev.boooiil.historia.handlers.pvp.PlayerShootBow;

public class EntityShootBowListener implements Listener {
    
    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
     
        PlayerShootBow playerShootBow = new PlayerShootBow(event);
        playerShootBow.doShoot();

    }

}
