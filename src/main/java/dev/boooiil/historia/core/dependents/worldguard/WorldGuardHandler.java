package dev.boooiil.historia.core.dependents.worldguard;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

/**
 * WorldGuard Utility class.
 */
@NullMarked
public class WorldGuardHandler {

    /**
     * Checks to see if the player is able to place a block.
     * 
     * @param player   - Player placing the block.
     * @param location - Location of the block.
     * @return - If the player is able to place a block.
     */
    public static boolean getBuildPermissions(Player player, Location location) {

        boolean hasPermission;

        try {

            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            World worldGuardWorld = localPlayer.getWorld();
            com.sk89q.worldedit.util.Location worldGuardLocation = new com.sk89q.worldedit.util.Location(
                    worldGuardWorld, location.getBlockX(), location.getBlockY(), location.getBlockZ());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();

            hasPermission = query.testState(worldGuardLocation, localPlayer, Flags.BLOCK_PLACE);

        } catch (Exception e) {
            CoreLogger.debugToConsole("There was an error checking WorldGuard permissions");
            hasPermission = false;
        }

        return hasPermission;

    }

    /**
     * Checks to see if the player is able to break the block.
     * 
     * @param player   - Player breaking the block.
     * @param location - Location of the block.
     * @return If the player is able to break the block.
     */
    public static boolean getBreakPermissions(Player player, Location location) {

        boolean hasPermission;

        try {

            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            World worldGuardWorld = localPlayer.getWorld();
            com.sk89q.worldedit.util.Location worldGuardLocation = new com.sk89q.worldedit.util.Location(
                    worldGuardWorld, location.getBlockX(), location.getBlockY(), location.getBlockZ());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();

            hasPermission = query.testState(worldGuardLocation, localPlayer, Flags.BLOCK_BREAK);

        } catch (Exception e) {
            CoreLogger.debugToConsole("There was an error checking WorldGuard permissions");
            hasPermission = false;
        }

        return hasPermission;

    }

    /**
     * Checks to see if the player is able to use an item.
     * 
     * @param player   - Player using the item.
     * @param location - Location of the item.
     * @return - If the player is able to use an item.
     */
    public static boolean getUsePermissions(Player player, Location location) {

        boolean hasPermission;

        try {

            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            World worldGuardWorld = localPlayer.getWorld();
            com.sk89q.worldedit.util.Location worldGuardLocation = new com.sk89q.worldedit.util.Location(
                    worldGuardWorld, location.getBlockX(), location.getBlockY(), location.getBlockZ());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();

            hasPermission = query.testState(worldGuardLocation, localPlayer, Flags.USE);

        } catch (Exception e) {
            CoreLogger.debugToConsole("There was an error checking WorldGuard permissions");
            hasPermission = false;
        }

        return hasPermission;

    }

}