package dev.boooiil.historia.events.pve;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.handlers.pve.EntityDeathHandler;
import dev.boooiil.historia.util.Logging;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        Logging.debugToConsole("EntityDeath event created.");
        Logging.debugToConsole("Killer: " + event.getEntity().getKiller().getName() != null ? event.getEntity().getKiller().getName() : "UNKNOWN");
        Logging.debugToConsole("Killed: " + event.getEntity().getName());

        if (event.getEntity() instanceof Player) return;
        if (!(event.getEntity().getKiller() instanceof Player)) return;

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getKiller().getUniqueId(), false);

        EntityDeathHandler entityDeathHandler = new EntityDeathHandler(event, historiaPlayer);
        entityDeathHandler.doDeath();

    }
    
}
