package dev.boooiil.historia.dependents.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * It's a class that handles all of the Towny related methods
 */
public class TownyHandler {

    // It's a private constructor that throws an exception.
    private TownyHandler() {
        throw new IllegalStateException("Static utility class.");
    }

    /**
     * Checks to see if the player is able to break the block.
     * 
     * @param player   - Player breaking the block.
     * @param location - Location of the block.
     * @param material - Material of the block.
     * @return If the player is able to break the block.
     */

    public static boolean getPermissionByMaterial(Player player, Location location, Material material) {

        try {

            return PlayerCacheUtil.getCachePermission(player, location, material, TownyPermission.ActionType.DESTROY);

        } catch (Exception e) {

            System.out.println("§7[§9Historia§7] There was an error checking Towny permissions.");
            return false;

        }

    }

    /**
     * Get the resident object from Towny.
     * *
     * 
     * @param uuid - UUID of the player.
     * @return Resident object.
     * 
     * @see <a href=
     *      "https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.98.3.4/javadoc/com/palmergames/bukkit/towny/object/Resident.html">Resident</a>
     */

    public static Resident getResident(UUID uuid) {

        return TownyAPI.getInstance().getResident(uuid);

    }

    /**
     * Checks to see if the player is in a town.
     * 
     * @param uuid - UUID of the player.
     * @return - If the player has a town.
     */

    public static boolean hasTown(UUID uuid) {

        Resident resident = getResident(uuid);

        if (resident != null) {

            return resident.hasTown();

        }
        return false;

    }

    /**
     * Get the player's town object from Towny.
     * *
     * 
     * @param uuid - UUID of the player.
     * @return Town object.
     * 
     * @see <a href=
     *      "https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.98.3.4/javadoc/com/palmergames/bukkit/towny/object/Town.html">Town</a>
     */

    public static Town getTown(UUID uuid) {

        if (hasTown(uuid)) {

            Resident resident = getResident(uuid);

            try {
                return resident.getTown();
            }

            catch (Exception e) {
                return null;
            }

        } else
            return null;
    }

    /**
     * Get the player's town name.
     * *
     * 
     * @param uuid - UUID of the player.
     * @return Town name.
     * 
     */

    public static String getTownName(UUID uuid) {

        if (hasTown(uuid))
            return getTown(uuid).getName();
        else
            return "Wilderness";
    }

    /**
     * Checks to see if the player's town has a home block.
     * 
     * @param uuid - UUID of the player.
     * @return - If the player's town has a home block.
     */

    public static boolean hasHomeBlock(UUID uuid) {

        if (hasTown(uuid)) {

            try {

                TownBlock homeBlock = getTown(uuid).getHomeBlock();

                return homeBlock.hasTown();

            } catch (Exception e) {
                return false;
            }

        } else
            return false;

    }

    /**
     * Get the player's town block object from Towny.
     * *
     * 
     * @param uuid - UUID of the player.
     * @return Town block object.
     * 
     * @see <a href=
     *      "https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.98.3.4/javadoc/com/palmergames/bukkit/towny/object/TownBlock.html">TownBlock</a>
     */

    public static TownBlock getHomeBlock(UUID uuid) {

        if (hasHomeBlock(uuid)) {

            Town town = getTown(uuid);

            try {

                return town.getHomeBlock();

            } catch (Exception e) {
                return null;
            }

        } else
            return null;

    }

    /**
     * Checks to see if the player's town has a spawn block.
     * 
     * @param uuid - UUID of the player.
     * @return - If the player's town has a spawn block.
     */

    public static boolean hasSpawnBlock(UUID uuid) {

        if (hasTown(uuid)) {

            try {

                Town town = getTown(uuid);

                return town.hasSpawn();

            } catch (Exception e) {
                return false;
            }

        }
        return false;

    }

    /**
     * Get the player's current spawn block location.
     * 
     * @param uuid
     * @return The player's spawn block location.
     * 
     * @see <a href=
     *      "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Location.html">Location</a>
     */

    public static Location getSpawn(UUID uuid) {

        if (hasTown(uuid) && hasSpawnBlock(uuid)) {

            try {

                Town town = getTown(uuid);

                return town.getSpawn();

            } catch (Exception e) {
                return null;
            }

        } else
            return null;

    }

    /**
     * Checks to see if the player's town is in a nation.
     * 
     * @param uuid - UUID of the player.
     * @return - If the player's town is in a nation.
     */

    public static boolean hasNation(UUID uuid) {

        if (hasTown(uuid)) {

            Town town = getTown(uuid);

            return town.hasNation();

        } else
            return false;

    }

    /**
     * Get the player's town's nation object from Towny.
     * *
     * 
     * @param uuid - UUID of the player.
     * @return Nation object.
     * 
     * @see <a href=
     *      "https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.98.3.4/javadoc/com/palmergames/bukkit/towny/object/Nation.html">Nation</a>
     */

    public static Nation getNation(UUID uuid) {

        if (hasNation(uuid)) {

            try {

                Town town = getTown(uuid);

                return town.getNation();

            } catch (Exception e) {
                return null;
            }

        } else
            return null;

    }

    /**
     * Get the player's town's nation name.
     * *
     * 
     * @param uuid - UUID of the player.
     * @return Nation name.
     * 
     */

    public static String getNationName(UUID uuid) {

        if (hasNation(uuid)) {

            Nation nation = getNation(uuid);

            return nation.getName();

        } else
            return "None";

    }

    /**
     * Send a message to all town members.
     * 
     * @param uuid    - UUID of the player.
     * @param message - Message to send.
     */
    public static void sendToTown(UUID uuid, String message) {

        Town town = getTown(uuid);
        String prefix = "[" + town.getName() + "] ";

        if (town != null) {

            town.getResidents().forEach(resident -> {

                if (resident.isOnline()) {

                    resident.getPlayer().sendMessage(prefix + message);

                }

            });

        }

    }

    /**
     * Send a message to all nation members.
     * 
     * @param uuid    - UUID of the player.
     * @param message - Message to send.
     */
    public static void sendToNation(UUID uuid, String message) {

        Nation nation = getNation(uuid);
        String prefix = "[" + nation.getName() + "] ";

        if (nation != null) {

            nation.getResidents().forEach(resident -> {

                if (resident.isOnline()) {

                    resident.getPlayer().sendMessage(prefix + message);

                }

            });

        }
    }
}