package dev.boooiil.historia.core.dependents;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.dependents.towny.TownyHandler;
import dev.boooiil.historia.core.dependents.worldguard.WorldGuardHandler;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Permissions {

    /**
     * It checks if a player has permission to break a block.
     * 
     * @param player The player who is trying to break the block.
     * @param block  The block you want to check.
     * @return A boolean value.
     */
    public static boolean canBreakBlock(Player player, Block block) {

        if (Main.isTesting)
            return true; // TODO: remove this (for testing purposes only

        boolean townyPermission = TownyHandler.getBreakPermissions(player, block.getLocation(), block.getType());
        boolean worldGuardPermission = WorldGuardHandler.getBuildPermissions(player, block.getLocation());

        return (townyPermission && worldGuardPermission) || player.isOp();
    }

    /**
     * It checks if a player has permission to use a block.
     * 
     * @param player The player who is trying to use the block.
     * @param block  The block you want to check.
     * @return A boolean value.
     */
    public static boolean canUseBlock(Player player, Block block) {

        boolean townyPermission = TownyHandler.getUsePermissions(player, block.getLocation(), block.getType());
        boolean worldGuardPermission = WorldGuardHandler.getBuildPermissions(player, block.getLocation());

        return (townyPermission && worldGuardPermission) || player.isOp();
    }

    /**
     * It checks if a player has permission to place a block.
     * 
     * @param player The player who is trying to place the block.
     * @param block  The block you want to check.
     * @return A boolean value.
     */
    public static boolean canPlaceBlock(Player player, Block block) {

        boolean townyPermission = TownyHandler.getPlacePermissions(player, block.getLocation(), block.getType());
        boolean worldGuardPermission = WorldGuardHandler.getBuildPermissions(player, block.getLocation());

        return (townyPermission && worldGuardPermission) || player.isOp();
    }

}
