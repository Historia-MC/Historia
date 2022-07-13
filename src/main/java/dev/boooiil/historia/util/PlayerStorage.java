package dev.boooiil.historia.util;

import java.util.HashMap;
import java.util.UUID;

import dev.boooiil.historia.classes.HistoriaPlayer;

public class PlayerStorage {

    public static HashMap<UUID, HistoriaPlayer> players;

    /**
     * Add a player to our storage.
     * 
     * @param uuid - UUID of the player.
     * @param historiaPlayer - {@link HistoriaPlayer} - Player object.
     */
    public static void addPlayer(UUID uuid, HistoriaPlayer historiaPlayer) {

        players.put(uuid, historiaPlayer);

    }

    /**
     * Get a player from our stored player list.
     * 
     * @param uuid - UUID of the player.
     * @return {@link HistoriaPlayer} - The player you are requesting.
     */
    public static HistoriaPlayer getPlayer(UUID uuid) {

        if (players.containsKey(uuid))
            return players.get(uuid);

        else
            return null;

    }

    public static void removePlayer(UUID uuid) {



    }

    /**
     * Save all player data to MySQL.
     */
    public void saveStates() {

        for (UUID uuid : players.keySet()) {

            players.get(uuid).saveCharacter();

        }

    }

}
