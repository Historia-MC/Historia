package dev.boooiil.historia.worldguard;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardHandler {

    public static boolean getPermissions(Player player, Location location) {
        try {

            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            World worldGuardWorld = localPlayer.getWorld();
            com.sk89q.worldedit.util.Location worldGuardLocation = new com.sk89q.worldedit.util.Location(
                    worldGuardWorld, location.getBlockX(), location.getBlockY(), location.getBlockZ());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();

            if (!query.testState(worldGuardLocation, localPlayer, Flags.BUILD)) {
                return false;
            }
            return true;

        } catch (Exception e) {
            System.out.println("§7[§9Historia§7] There was an error checking WorldGuard permissions.");
            return false;
        }

    }

    
}