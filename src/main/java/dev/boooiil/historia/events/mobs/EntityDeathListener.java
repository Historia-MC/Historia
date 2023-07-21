package dev.boooiil.historia.events.mobs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onKillEntity(EntityDeathEvent event) {

        if (!(event.getEntity() instanceof Player)) {

            LivingEntity livingEntity = (LivingEntity) event.getEntity();

            if (livingEntity.getKiller() instanceof Player) {

                Player killer = livingEntity.getKiller();
                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(killer.getUniqueId(), false);

                
                //TODO: get if the historiaPlayer can get animal drops
                // We will need to do animals like we do with ores

            }
        }
    }
}
