package dev.boooiil.historia.core.database.internal;

import dev.boooiil.historia.core.database.sql.tables.HistoriaTable;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for obtaining {@link HistoriaPlayer HistoriaPlayers}
 */
@NullMarked
public class PlayerStorage {

    // It's a HashMap that stores all the players that are currently online
    private static final ConcurrentHashMap<UUID, HistoriaPlayer> players = new ConcurrentHashMap<>();
    // It's a HashMap that stores all the players that are currently online
    private static final ConcurrentHashMap<String, UUID> usernameMap = new ConcurrentHashMap<>();

    /**
     * Add a player to our storage.
     *
     * @param uuid           - UUID of the player.
     * @param historiaPlayer - {@link HistoriaPlayer} - Player object.
     */
    public static void addPlayer(UUID uuid, HistoriaPlayer historiaPlayer) {

        CoreLogger.debugToConsole("Adding player:", historiaPlayer.getUsername(), uuid.toString());

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

    public static void addPlayer(Player player) {
        addPlayer(player.getUniqueId(), new HistoriaPlayer(player.getUniqueId()));
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

        return getPlayer(uuid);

    }

    public static HistoriaPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    /**
     * Get a player from our stored player list.
     *
     * @param uuid - UUID of the player.
     * @return {@link HistoriaPlayer} - The player you are requesting.
     */
    public static HistoriaPlayer getPlayer(UUID uuid) {

        HistoriaPlayer player;

        if (players.containsKey(uuid))
            player = players.get(uuid);
        else {
            player = HistoriaTable.PLAYER.get(uuid);

            if (player == null) {
                player = new HistoriaPlayer(uuid);
                HistoriaTable.UUID.insert(Map.of("uuid", uuid, "username", player.getUsername()));
            }

            addPlayer(uuid, player);
            return player;
        }

        return player;

    }

    public static @Nullable HistoriaPlayer getPlayer(String username) {

        HistoriaPlayer player = null;

        if (usernameMap.containsKey(username)) {
            player = players.get(usernameMap.get(username));
        } else {
            UUID uuid = HistoriaTable.UUID.get(username);

            if (uuid != null) {
                player = HistoriaTable.PLAYER.get(uuid);
            }
        }

        return player;
    }

    public static ConcurrentHashMap<UUID, HistoriaPlayer> getPlayerMap() {
        return players;
    }

    public static ConcurrentHashMap<String, UUID> getUsernameMap() {
        return usernameMap;
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

        HistoriaPlayer player = players.get(uuid);

        player.saveCharacter();
        player.setOnline(false);

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
