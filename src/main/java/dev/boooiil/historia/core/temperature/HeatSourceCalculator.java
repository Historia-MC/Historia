package dev.boooiil.historia.core.temperature;

import dev.boooiil.historia.core.configuration.specific.TemperatureConfig;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//TODO: this file is causing redundant loading of the temperature
// configuration.

public class HeatSourceCalculator {
    private final TemperatureConfig temperatureConfig;

    private final int SCAN_RADIUS; // Increased to match MAX_HEAT_DISTANCE
    private final double MAX_HEAT_DISTANCE = 12.0; // Extended for smoother falloff
    private final double TRANSITION_DISTANCE; // Distance where heat starts to blend with ambient

    public HeatSourceCalculator(TemperatureConfig temperatureConfig) {
        this.temperatureConfig = temperatureConfig;
        this.SCAN_RADIUS = (int) temperatureConfig.getSearchDistance();
        this.TRANSITION_DISTANCE = temperatureConfig.getDiminishDistance();
    }

    public double getHeatSourceEffect(Player player) {
        
        Set<HeatSource> heatSources = new HashSet<>();
        HeatSource highest = null;
        Double calculated = 0.0;

        Location feetLoc = player.getLocation();
        Location bodyLoc = feetLoc.clone().add(0, 1.0, 0);
        Location headLoc = feetLoc.clone().add(0, 1.8, 0);

        double totalHeatEffect = 0.0;
        Block mostInfluentialSource = null;
        double strongestIndividualEffect = 0.0;

        // Scan for heat sources
        for (int x = -SCAN_RADIUS; x <= SCAN_RADIUS; x++) {
            for (int y = -SCAN_RADIUS; y <= SCAN_RADIUS; y++) {
                for (int z = -SCAN_RADIUS; z <= SCAN_RADIUS; z++) {
                    Block block = player.getLocation().getBlock().getRelative(x, y, z);

                    HeatSource hs = new HeatSource(player.getLocation(), block);

                    // if heat source is highest
                    if (highest == null || highest.getHeatValue() < hs.getHeatValue()) highest = hs;
                    heatSources.add(hs);

//                    if (temperatureConfig.getHeatSourceValue(block.getType()) != null) {
//                        Location blockCenter = block.getLocation().add(0.5, 0.5, 0.5);
//
//                        double feetEffect = calculateHeatEffect(block, feetLoc, blockCenter);
//                        double bodyEffect = calculateHeatEffect(block, bodyLoc, blockCenter);
//                        double headEffect = calculateHeatEffect(block, headLoc, blockCenter);
//
//                        // Find the strongest effect for this source
//                        double maxEffect = Math.max(Math.max(feetEffect, bodyEffect), headEffect);
//
//                        if (maxEffect > 0) { // Only consider sources with actual effect
//                            Location blockCenterCheck = block.getLocation().add(0.5, 0.5, 0.5);
//                            Location closest = getClosestBodyPart(blockCenterCheck, feetLoc, bodyLoc, headLoc);
//
//                            // Check if this heat source can reach the player
//                            if (canHeatReachPlayer(blockCenterCheck, closest)) {
//                                // Use the strongest source as primary, add others with diminishing returns
//                                if (maxEffect > strongestIndividualEffect) {
//                                    // New strongest source - add difference to total
//                                    totalHeatEffect += (maxEffect - strongestIndividualEffect);
//                                    strongestIndividualEffect = maxEffect;
//                                    mostInfluentialSource = block;
//                                } else {
//                                    // Weaker source - add with diminishing returns based on current total
//                                    double diminishingFactor = Math.exp(-totalHeatEffect / 40.0);
//                                    totalHeatEffect += maxEffect * diminishingFactor * 0.3; // Weaker sources contribute less
//                                }
//                            }
//                        }
//                    }
                }
            }
        }

        for (HeatSource source : heatSources) {
            calculated += source.getHeatValue();

            if (source.getHeatValue() > 0) {
                spawnParticleLine(player.getUniqueId(), source.getBlock(), source.getPlayerPosition());
            }
        }

        // Show particle line to the most influential heat source
//        if (mostInfluentialSource != null) {
//            spawnParticleLine(player.getUniqueId(), mostInfluentialSource, player.getLocation());
//        }

        return calculated;
    }

    private double calculateHeatEffect(Block source, Location playerLoc, Location blockCenter) {
        double distance = blockCenter.distance(playerLoc);

        // If beyond max distance, no effect
        if (distance > MAX_HEAT_DISTANCE) {
            return 0.0;
        }

        Double maxTemp = temperatureConfig.getHeatSourceValue(source.getType());
        if (maxTemp == null)
            return 0.0;

        // Check if heat can reach player
        boolean canReach = canHeatReachPlayer(blockCenter, playerLoc);

        // Calculate base heat effect using smoother exponential decay
        double effectiveDistance = Math.max(distance, 0.5); // Minimum distance to avoid division issues
        double baseEffect = maxTemp * Math.exp(-1.0 * (effectiveDistance / MAX_HEAT_DISTANCE));

        // Apply line-of-sight modifier
        if (!canReach) {
            // If blocked, reduce effect but don't eliminate entirely for smoother transitions
            baseEffect *= 0.3; // Blocked sources provide 30% of their normal effect
        }

        // Apply additional smooth falloff in transition zone for very gradual temperature changes
        if (distance > TRANSITION_DISTANCE) {
            double transitionFactor = 1.0 - ((distance - TRANSITION_DISTANCE) / (MAX_HEAT_DISTANCE - TRANSITION_DISTANCE));
            transitionFactor = Math.max(transitionFactor, 0.0);
            // Apply smooth transition curve
            transitionFactor = Math.pow(transitionFactor, 2.0); // Quadratic falloff for smoother transition
            baseEffect *= transitionFactor;
        }

        return baseEffect;
    }

    // helper
    public void spawnParticleLine(UUID id, Block block, Location targetLoc) {
        Player player = Bukkit.getPlayer(id);
        if (block == null) {
            return;
        }
        // Always use block center coordinates
        Location blockLoc = block.getLocation().add(0.5, 0.5, 0.5);

        // Calculate distance and direction between target point and block
        double distance = targetLoc.distance(blockLoc);
        Vector direction = blockLoc.subtract(targetLoc).toVector().normalize();

        // Spawn particles along the line
        for (double i = 0; i < distance; i += 0.1) {
            Location particleLoc = targetLoc.clone().add(direction.clone().multiply(i));
            player.spawnParticle(Particle.DUST, particleLoc, 1, new Particle.DustOptions(Color.RED, 1.0F));
        }
    }

    // helper - incorporated
    private boolean canHeatReachPlayer(Location source, Location player) {
        // RayTracing is not supported in tests
        if (dev.boooiil.historia.core.HistoriaCore.isTesting) {
            return true;
        }
        // First check if player is within maximum heat distance
        double distance = source.distance(player);
        if (distance > MAX_HEAT_DISTANCE) {
            return false;
        }

        // Then check for direct line of sight
        if (hasDirectPath(source, player)) {
            return true;
        }
        // Probably not needed now.
        // If no direct path but within range, check for paths around obstacles
        return hasPathAroundObstacles(source, player);
    }

    // helper - incorporated
    private boolean hasDirectPath(Location source, Location target) {
        // Get direction vector from source to target
        Vector direction = target.toVector().subtract(source.toVector());

        // If source is a campfire, add 0.2 to Y component to account for smoke
        if (source.getBlock().getType() == Material.CAMPFIRE) {
            direction.setY(direction.getY() + 0.5);
        }

        double distance = direction.length();

        // Perform rayTrace from source to target
        RayTraceResult result = source.getWorld().rayTraceBlocks(
                source,
                direction.normalize(),
                distance,
                FluidCollisionMode.NEVER, // Ignore fluids
                true // Ignore passable blocks
        );

        // If no collision was found, there's a clear path
        // If there was a collision, check if it's at the target block
        return result == null ||
                (result.getHitBlock() != null &&
                        result.getHitBlock().getLocation().equals(target.getBlock().getLocation()));
    }

    // helper - incorporated
    private boolean hasPathAroundObstacles(Location source, Location target) {

        return findAirPath(source.getBlock(), target.getBlock(), new HashSet<>());
    }

    // helper - incorporated
    private boolean findAirPath(Block start, Block end, Set<Block> visited) {
        if (visited.size() > 25)
            return false; // Prevent excessive searching
        if (start.equals(end))
            return true;
        visited.add(start);

        // Check all adjacent blocks
        for (BlockFace face : BlockFace.values()) {
            Block adjacent = start.getRelative(face);
            if (!visited.contains(adjacent) && !adjacent.getType().isOccluding()) {
                if (findAirPath(adjacent, end, visited)) {
                    adjacent.getWorld().spawnParticle(Particle.DUST, adjacent.getLocation().add(0.5, 0.5, 0.5), 1,
                            new Particle.DustOptions(Color.RED, 1.0F));
                    return true;
                }
            }
        }
        return false;
    }

    // helper
    private Location getClosestBodyPart(Location source, Location feet, Location body, Location head) {
        double feetDist = source.distance(feet);
        double bodyDist = source.distance(body);
        double headDist = source.distance(head);
        if (feetDist <= bodyDist && feetDist <= headDist) {
            return feet;
        } else if (bodyDist <= feetDist && bodyDist <= headDist) {
            return body;
        } else {
            return head;
        }
    }
}