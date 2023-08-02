package dev.boooiil.historia.events.pve;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.handlers.pve.EntityDeathHandler;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (event.getEntity() instanceof Player) return;
        if (!(event.getEntity().getKiller() instanceof Player)) return;

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getKiller().getUniqueId(), false);

        EntityDeathHandler entityDeathHandler = new EntityDeathHandler(event, historiaPlayer);
        entityDeathHandler.doDeath();

    }
    
}
