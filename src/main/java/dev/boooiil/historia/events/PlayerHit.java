package dev.boooiil.historia.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dev.boooiil.historia.classes.KillerUser;
import dev.boooiil.historia.classes.TimedUser;
import dev.boooiil.historia.handlers.SpawnKillHandler;
import dev.boooiil.historia.util.Logging;

public class PlayerHit implements Listener {

    @EventHandler
    public void onPlayerKill(EntityDamageByEntityEvent event) {

        Entity victim = event.getEntity();
        Entity hitter = event.getDamager();

        if (victim instanceof Player && hitter instanceof Player) {

            // If the hitter has been killed before
            if (SpawnKillHandler.users.containsKey(hitter.getUniqueId())) {

                TimedUser user = SpawnKillHandler.users.get(hitter.getUniqueId());

                // If the victim has killed the player recently
                if (user.getKillers().containsKey(victim.getUniqueId())) {

                    KillerUser killer = user.getKillers().get(victim.getUniqueId());

                    if (!killer.getHitBack()) {

                        killer.setHitBack(true);

                        if (killer.getKills() >= 3) {

                            Logging.infoToPlayer(
                                    "You have been hit back by " + hitter.getName() + "! You can now kill this player.",
                                    victim.getUniqueId());

                        }
                    }
                }

            }

            // If the victim has been killed before
            if (SpawnKillHandler.users.containsKey(victim.getUniqueId())) {

                TimedUser user = SpawnKillHandler.users.get(victim.getUniqueId());

                if (user.getKillers().containsKey(hitter.getUniqueId())) {

                    KillerUser killer = user.getKillers().get(hitter.getUniqueId());

                    if (killer.getKills() >= 3 && !killer.getHitBack()) {

                        double damage = 0.8 * killer.getKills();
                        Player player = (Player) hitter;

                        player.damage(damage);

                        Logging.warnToPlayer("This user has been killed too many times!", player.getUniqueId());

                    }

                }

            }
        }

    }

}
