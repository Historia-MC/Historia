package dev.boooiil.historia.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TownyHandler {

    private TownyHandler() { throw new IllegalStateException("Static utility class."); }
    
    /**
     * Checks to see if the player is able to break the block.
     * 
     * @param player - Player breaking the block.
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
     * Checks to see if the player is a resident.
     * 
     * <p>A resident just means Towny has the player's data.
     * 
     * @param playerName - Name of the player.
     * @return - If the player is a resident.
     */

    public static boolean isResident(String playerName) {

        return TownyAPI.getInstance().getDataSource().hasResident(playerName);

    }

    /**
     * Get the resident object from Towny.
     *      * 
     * @param playerName - Name of the player.
     * @return Resident object.
     * 
     * @see <a href="https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.96.4.0/javadoc/">Resident</a>
     */

    public static Resident getResident(String playerName) {

        if (isResident(playerName)) {

            try {

                return TownyAPI.getInstance().getDataSource().getResident(playerName);

            } catch (Exception e) { return null; }

        } else return null;
    }

    /**
     * Checks to see if the player is in a town.
     *  
     * @param playerName - Name of the player.
     * @return - If the player has a town.
     */

    public static boolean hasTown(String playerName) {

        Resident resident = getResident(playerName);

        if (resident != null) {

            return resident.hasTown();

        } return false;

    }

    /**
     * Get the player's town object from Towny.
     *      * 
     * @param playerName - Name of the player.
     * @return Town object.
     * 
     * @see <a href="https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.96.4.0/javadoc/">Town</a>
     */

    public static Town getTown(String playerName) {

        if (hasTown(playerName)) {

            Resident resident = getResident(playerName);

            try { return resident.getTown(); } 

            catch (Exception e) { return null; }

        } else return null;
    }

    /**
     * Get the player's town  name.
     *      * 
     * @param playerName - Name of the player.
     * @return Town name.
     * 
     */

    public static String getTownName(String playerName) {

        if (hasTown(playerName)) return getTown(playerName).getName();
        else return "Wilderness";
    }

    /**
     * Checks to see if the player's town has a home block.
     *  
     * @param playerName - Name of the player.
     * @return - If the player's town has a home block.
     */

    public static boolean hasHomeBlock(String playerName) {

        if (hasTown(playerName)) {

            try {

                TownBlock homeBlock = getTown(playerName).getHomeBlock();

                return homeBlock.hasTown();

            } catch (Exception e) { return false; }
            
        } else return false;

    }

    /**
     * Get the player's town block object from Towny.
     *      * 
     * @param playerName - Name of the player.
     * @return Town block object.
     * 
     * @see <a href="https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.96.4.0/javadoc/">TownBlock</a>
     */

    public static TownBlock getHomeBlock(String playerName) {

        if (hasHomeBlock(playerName)) {

            Town town = getTown(playerName);

            try {

                return town.getHomeBlock();

            } catch (Exception e) { return null; }

        } else return null;

    }

    /**
     * Checks to see if the player's town has a spawn block.
     *  
     * @param playerName - Name of the player.
     * @return - If the player's town has a spawn block.
     */

    public static boolean hasSpawnBlock(String playerName) {

        if (hasTown(playerName)) {

            try {

                Town town = getTown(playerName);

                return town.hasSpawn();

            } catch (Exception e) { return false; }

        } return false;

    }

    /**
     * Get the player's current spawn block location.
     * 
     * @param playerName
     * @return The player's spawn block location.
     * 
     * @see <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Location.html">Location</a>
     */
    
    public static Location getSpawn(String playerName) {

        if (hasTown(playerName) && hasSpawnBlock(playerName)) {

            try {

                Town town = getTown(playerName);

                return town.getSpawn();

            } catch (Exception e) { return null; }

        } else return null;

    }

    /**
     * Checks to see if the player's town is in a nation.
     *  
     * @param playerName - Name of the player.
     * @return - If the player's town is in a nation.
     */

    public static boolean hasNation(String playerName) {

        if (hasTown(playerName)) {

            Town town = getTown(playerName);

            return town.hasNation();

        } else return false;

    }

    /**
     * Get the player's town's nation object from Towny.
     *      * 
     * @param playerName - Name of the player.
     * @return Nation object.
     * 
     * @see <a href="https://javadoc.jitpack.io/com/github/TownyAdvanced/Towny/0.96.4.0/javadoc/">Nation</a>
     */

    public static Nation getNation(String playerName) {

        if (hasNation(playerName)) {

            try {

                Town town = getTown(playerName);

                return town.getNation();

            } catch (Exception e) { return null; }

        } else return null;

    }

    /**
     * Get the player's town's nation name.
     *      * 
     * @param playerName - Name of the player.
     * @return Nation name.
     * 
     */

    public static String getNationName(String playerName) {

        if (hasNation(playerName)) {

            Nation nation = getNation(playerName);

            return nation.getName();

        } else return "None";

    }
}