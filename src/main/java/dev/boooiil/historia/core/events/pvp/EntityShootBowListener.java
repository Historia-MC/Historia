package dev.boooiil.historia.core.events.pvp;

import dev.boooiil.historia.core.handlers.pvp.PlayerShootBow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityShootBowListener implements Listener {
    
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
     
        PlayerShootBow playerShootBow = new PlayerShootBow(event);
        playerShootBow.doShoot();

    }

}
