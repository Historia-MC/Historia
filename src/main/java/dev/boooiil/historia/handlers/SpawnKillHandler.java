package dev.boooiil.historia.handlers;

import java.util.HashMap;
import java.util.UUID;

import dev.boooiil.historia.classes.KillerUser;
import dev.boooiil.historia.classes.TimedUser;

public class SpawnKillHandler {

    /**
     * Realistically, we might need to change this either into a global death limit or town-based death limit.
     * Currently, we will have it by just player since I do not expect people to just rotate kills.
     */
    
    public static HashMap<UUID, TimedUser> users = new HashMap<UUID, TimedUser>();

    /**
     * Add a kill to a user, will create a new user if one does not exist already.
     * @param player - User that was killed.
     * @param killer - User that killed.
     */
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
