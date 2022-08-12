package dev.boooiil.historia.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.boooiil.historia.classes.KillerUser;
import dev.boooiil.historia.classes.TimedUser;
import dev.boooiil.historia.handlers.SpawnKillHandler;

public class PlayerKilled implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (player instanceof Player && killer instanceof Player) {

            // If the user has been killed before
            if (SpawnKillHandler.users.containsKey(player.getUniqueId())) {

                TimedUser timedUser = SpawnKillHandler.users.get(player.getUniqueId());

                // If the killer has killed this user before
                if (timedUser.getKillers().containsKey(killer.getUniqueId())) {

                    KillerUser killerUser = timedUser.getKillers().get(killer.getUniqueId());

                    // If the killer's kills are greater than 3 and they have been hit back
                    if (killerUser.getHitBack() && killerUser.getKills() >= 3) {

                        killerUser.setHitBack(false);
                        return;

                    } else
                        SpawnKillHandler.addKill(player.getUniqueId(), killer.getUniqueId());
                } else
                    SpawnKillHandler.addKill(player.getUniqueId(), killer.getUniqueId());
            } else
                SpawnKillHandler.addKill(player.getUniqueId(), killer.getUniqueId());

        }

    }

}
