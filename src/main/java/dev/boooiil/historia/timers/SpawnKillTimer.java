package dev.boooiil.historia.timers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.boooiil.historia.classes.KillerUser;
import dev.boooiil.historia.classes.TimedUser;
import dev.boooiil.historia.handlers.SpawnKillHandler;
import dev.boooiil.historia.util.Logging;

public class SpawnKillTimer {

    /**
     * This will track how many kills have occured by each player to avoid spawn
     * killing.
     */

    public static void timer() {

        // This timer will run every second.
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (SpawnKillHandler.users.size() == 0) return;

                HashMap<UUID, List<UUID>> deletableUsers = new HashMap<UUID, List<UUID>>();
                List<UUID> deletableKillers = new ArrayList<UUID>();

                //Iterate over users that have been killed
                for (Map.Entry<UUID, TimedUser> user : SpawnKillHandler.users.entrySet()) {

                    //Iterate over the killers of this user
                    //This is broken because we delete the next value in the map
                    for (Map.Entry<UUID, KillerUser> killer : user.getValue().getKillers().entrySet()) {

                        KillerUser killerUser = killer.getValue();

                        //If the time between the last kill has been over 30 seconds
                        if (new Date().getTime() - killerUser.getLastKill() > 30000) {

                            deletableKillers.add(killer.getKey());
                            deletableUsers.put(user.getKey(), deletableKillers);

                        }

                    }

                }

                delete(deletableUsers);
                // Do logic here

            }
        }, 0, 1000);

    }

    public static void delete(HashMap<UUID, List<UUID>> deletableKillers) {

        // Remove the killer from our list of killers

        for (Map.Entry<UUID, List<UUID>> user : deletableKillers.entrySet()) {

            Player player = Bukkit.getPlayer(user.getKey());
            TimedUser timedUser = SpawnKillHandler.users.get(user.getKey());

            for (UUID killer : user.getValue()) {

                if (timedUser.getKillers().get(killer).getKills() >= 3) {

                    Player killerPlayer = Bukkit.getPlayer(killer);

                    Logging.warnToPlayer(killerPlayer.getName() + " can now kill you without punishment.", user.getKey());
                    Logging.debugToConsole(player.getName() + " can now be killed by " + killerPlayer.getName() + ".");

                }

                Logging.infoToPlayer("Kill protection has expired for " + player.getName() + ".", killer);

                timedUser.getKillers().remove(killer);

            }

            // If the user no longer has any killers
            if (timedUser.getKillers().size() == 0) {

                // Remove the user
                SpawnKillHandler.users.remove(user.getKey());

            }

        }

    }

}