package dev.boooiil.historia.towny;

import com.palmergames.bukkit.towny.TownyAPI;
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
    
    public static boolean getPermissionByMaterial(Player player, Location location, Material material) {

        try {

            return PlayerCacheUtil.getCachePermission(player, location, material, TownyPermission.ActionType.DESTROY);

        } catch (Exception e) {

            System.out.println("§7[§9Historia§7] There was an error checking Towny permissions.");
            return false;

        }

    }

    public static boolean isResident(String playerName) {

        return TownyAPI.getInstance().getDataSource().hasResident(playerName);

    }

    public static Resident getResident(String playerName) {

        if (isResident(playerName)) {

            try {

                return TownyAPI.getInstance().getDataSource().getResident(playerName);

            } catch (Exception e) { return null; }

        } else return null;
    }

    public static boolean hasTown(String playerName) {

        Resident resident = getResident(playerName);

        if (resident != null) {

            return resident.hasTown();

        } return false;

    }

    public static Town getTown(String playerName) {

        if (hasTown(playerName)) {

            Resident resident = getResident(playerName);

            try { return resident.getTown(); } 

            catch (Exception e) { return null; }

        } else return null;
    }

    public static String getTownName(String playerName) {

        if (hasTown(playerName)) return getTown(playerName).getName();
        else return "Wilderness";
    }

    public static boolean hasHomeBlock(String playerName) {

        if (hasTown(playerName)) {

            try {

                TownBlock homeBlock = getTown(playerName).getHomeBlock();

                return homeBlock.hasTown();

            } catch (Exception e) { return false; }
            
        } else return false;

    }

    public static TownBlock getHomeBlock(String playerName) {

        if (hasHomeBlock(playerName)) {

            Town town = getTown(playerName);

            try {

                return town.getHomeBlock();

            } catch (Exception e) { return null; }

        } else return null;

    }

    public static boolean hasSpawnBlock(String playerName) {

        if (hasTown(playerName)) {

            try {

                Town town = getTown(playerName);

                return town.hasSpawn();

            } catch (Exception e) { return false; }

        } return false;

    }
    
    public static Location getSpawn(String playerName) {

        if (hasTown(playerName) && hasSpawnBlock(playerName)) {

            try {

                Town town = getTown(playerName);

                return town.getSpawn();

            } catch (Exception e) { return null; }

        } else return null;

    }
}