package dev.boooiil.historia.timers;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import dev.boooiil.historia.classes.KillerUser;
import dev.boooiil.historia.classes.TimedUser;
import dev.boooiil.historia.handlers.SpawnKillHandler;

public class SpawnKillTimer {

    /**
     * This will track how many kills have occured by each player to avoid spawn
     * killing.
     */

    public void timer() {

        // This timer will run every second.
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //Iterate over users that have been killed
                for (Map.Entry<UUID, TimedUser> user : SpawnKillHandler.users.entrySet()) {

                    //Iterate over the killers of this user
                    for (Map.Entry<UUID, KillerUser> killer : user.getValue().getKillers().entrySet()) {

                        KillerUser killerUser = killer.getValue();

                        //If the time between the last kill has been over 30 seconds
                        if (new Date().getTime() - killerUser.getLastKill() > 30000) {

                            //Remove the killer from our list of killers
                            user.getValue().getKillers().remove(killer.getKey());

                        }

                    }

                }
                // Do logic here

            }
        }, 0, 1000);

    }

}