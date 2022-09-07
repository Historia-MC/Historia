package dev.boooiil.historia.util;

import java.util.HashMap;
import java.util.UUID;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.mysql.MySQLHandler;

public class PlayerStorage {

    public static HashMap<UUID, HistoriaPlayer> players = new HashMap<UUID, HistoriaPlayer>();
    public static HashMap<String, UUID> usernameMap = new HashMap<String, UUID>();

    /**
     * Add a player to our storage.
     * 
     * @param uuid           - UUID of the player.
     * @param historiaPlayer - {@link HistoriaPlayer} - Player object.
     */
    public static void addPlayer(UUID uuid, HistoriaPlayer historiaPlayer) {

        // If the player has already been logged into the server.
        if (players.containsKey(uuid)) {

            players.get(uuid).setOnline();

        }

        // Else, create a new HistoriaPlayer and store it in the HashMap
        else {

            players.put(uuid, historiaPlayer);
            usernameMap.put(historiaPlayer.getUsername(), uuid);

        }

    }

    /**
     * Get a player from our stored player list.
     * 
     * @param uuid           - UUID of the player.
     * @param useSQLFallback - Fallback to SQL if the user is not currently on.
     * @return {@link HistoriaPlayer} - The player you are requesting.
     */
    public static HistoriaPlayer getPlayer(UUID uuid, boolean useSQLFallback) {

        if (players.containsKey(uuid))
            return players.get(uuid);

        else if (useSQLFallback)
            return new HistoriaPlayer(uuid);

        else
            return new HistoriaPlayer();

    }

    /**
     * Get a player from our stored player list.
     * 
     * @param username       - Username of the player.
     * @param useSQLFallback - Fallback to SQL if the user is not currently on.
     * @return {@link HistoriaPlayer} - The player you are requesting.
     */
    public static HistoriaPlayer getPlayer(String username, boolean useSQLFallback) {

        if (usernameMap.containsKey(username))
            return players.get(usernameMap.get(username));

        else if (useSQLFallback)
            return new HistoriaPlayer(MySQLHandler.getUUID(username));

        else
            return new HistoriaPlayer();

    }

    public static boolean has(UUID uuid) {

        return players.containsKey(uuid);

    }

    public static boolean has(String username) {

        return usernameMap.containsKey(username);

    }

    /**
     * Remove a player from our stored player list.
     * 
     * This will be used on logout events.
     * 
     * @param uuid UUID of the player.
     */
    public static void markOffline(UUID uuid) {

        players.get(uuid).saveCharacter();

        players.get(uuid).setOffline();

    }

    /**
     * Save all player data to MySQL.
     */
    public static void saveStates() {

        for (UUID uuid : players.keySet()) {

            players.get(uuid).saveCharacter();

        }

    }

}
