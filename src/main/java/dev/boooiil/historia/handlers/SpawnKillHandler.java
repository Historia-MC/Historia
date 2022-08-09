package dev.boooiil.historia.handlers;

import java.util.HashMap;
import java.util.UUID;

import dev.boooiil.historia.classes.KillerUser;
import dev.boooiil.historia.classes.TimedUser;

public class SpawnKillHandler {
    
    public static HashMap<UUID, TimedUser> users;

    public static void addKill(UUID player, UUID killer) {

        if (users.containsKey(player)) {

            users.get(player).addKillToKiller(killer);

            users.get(player).getKillers().get(killer).setHitBack(false);

        }

        else {

            TimedUser user = new TimedUser();

            user.addKillToKiller(killer);

            users.put(player, user);

        }

    }

    public static void playerHit(UUID player, UUID hitter) {

        if (users.containsKey(hitter)) {

            HashMap<UUID, KillerUser> killers = users.get(hitter).getKillers();

            if (killers.containsKey(player)) killers.get(player).setHitBack(true);

        }

    }

}
