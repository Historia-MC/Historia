package dev.boooiil.historia.towny;

import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TownyHandler {
    
    public static boolean getPermissions(Player player, Location location, Material material) {

        try {

            return PlayerCacheUtil.getCachePermission(player, location, material, TownyPermission.ActionType.DESTROY);

        } catch (Exception e) {
            System.out.println("§7[§9Historia§7] There was an error checking Towny permissions.");
            return false;
        }

    }
}