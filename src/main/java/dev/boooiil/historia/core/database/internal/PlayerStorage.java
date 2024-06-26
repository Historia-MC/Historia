package dev.boooiil.historia.core.database.internal;

import dev.boooiil.historia.core.database.DatabaseAdapter;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import java.util.HashMap;
import java.util.UUID;

/**
 * It's a HashMap that stores all the players that are currently online
 */
public class PlayerStorage {

    // It's a HashMap that stores all the players that are currently online
    public static HashMap<UUID, HistoriaPlayer> players = new HashMap<>();
    // It's a HashMap that stores all the players that are currently online
    public static HashMap<String, UUID> usernameMap = new HashMap<>();

    /**
     * Add a player to our storage.
     * 
     * @param uuid           - UUID of the player.
     * @param historiaPlayer - {@link HistoriaPlayer} - Player object.
     */
    public static void addPlayer(UUID uuid, HistoriaPlayer historiaPlayer) {

        // If the player has already been logged into the server.
        if (players.containsKey(uuid)) {

            players.get(uuid).setOnline(true);

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
    @Deprecated(forRemoval = true)
    public static HistoriaPlayer getPlayer(UUID uuid, boolean useSQLFallback) {

        if (players.containsKey(uuid))
            return players.get(uuid);

        else
            return new HistoriaPlayer(uuid);

    }

    /**
     * Get a player from our stored player list.
     * 
     * @param uuid           - UUID of the player.
     * @return {@link HistoriaPlayer} - The player you are requesting.
     */
    public static HistoriaPlayer getPlayer(UUID uuid) {

        if (players.containsKey(uuid))
            return players.get(uuid);

        else
            return new HistoriaPlayer(uuid);

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
            return new HistoriaPlayer(DatabaseAdapter.getUUID(username));

        else
            return new HistoriaPlayer();

    }
    
    /**
     * Check if the storage holds the given UUID.
     * 
     * @param uuid - UUID of the player.
     * @return - {@link Boolean}
     */
    public static boolean has(UUID uuid) {

        return players.containsKey(uuid);

    }

    /**
     * Check if the storage holds the given username.
     * 
     * @param username - Username of the player.
     * @return - {@link Boolean}
     */
    public static boolean has(String username) {

        return usernameMap.containsKey(username);

    }

    /**
     * Remove a player from our stored player list.
     * This will be used on logout events.
     * 
     * @param uuid UUID of the player.
     */
    public static void markOffline(UUID uuid) {

        players.get(uuid).saveCharacter();

        players.get(uuid).setOnline(false);

    }

    /**
     * Save all player data to MySQL.
     */
    public static void saveStates() {

        for (HistoriaPlayer player : players.values()) {

            // shouldn't need last logout in theory
            // it is handled by the logout event
            if (player.getLastSaved() < player.getLastLogin()) {

                player.saveCharacter();

            }

        }

    }

}
