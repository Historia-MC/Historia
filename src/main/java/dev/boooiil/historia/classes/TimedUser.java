package dev.boooiil.historia.classes;

import java.util.HashMap;
import java.util.UUID;

public class TimedUser {
    
    private int totalDeaths = 0;

    private HashMap<UUID, KillerUser> killers;


    public void addKillToKiller(UUID killer) {

        if (killers.containsKey(killer)) { 
            
            killers.get(killer).addKill();
            totalDeaths ++;

        }
        else {

            KillerUser killerUser = new KillerUser();

            killerUser.addKill();

            killers.put(killer, killerUser);

        }

    }

    public int getKillerKills(UUID uuid) {

        return killers.get(uuid).getKills();

    }

    public HashMap<UUID, KillerUser> getKillers() {

        return killers;

    }

    public int getTotalDeaths() {

        return totalDeaths;

    }

}
