package dev.boooiil.historia.core.temperature

import dev.boooiil.historia.core.configuration.ConfigurationLoader
import dev.boooiil.historia.core.configuration.specific.TemperatureConfig
import org.bukkit.*
import org.bukkit.Particle.DustOptions
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.pow

class HeatSource(val playerPosition: Location, val block: Block) {

    private val temperatureConfig: TemperatureConfig = ConfigurationLoader.getTemperatureConfig()
    private val DISTANCE_FROM_PLAYER: Double = playerPosition.distance(block.location)
    private val CONFIGURED_HEAT_LEVEL: Double = temperatureConfig.getHeatSourceValue(block.type)
    private val DIMINISH_DISTANCE = temperatureConfig.diminishDistance
    private var calculated: Double = -1.0

    fun canReachPlayer(): Boolean {
        val direct = hasDirectPath(playerPosition, block.location)
        val air = findAirPath(playerPosition.block, block, HashSet())

        return (direct || air)
    }

    fun getHeatValue(): Double {
        val inRange = DISTANCE_FROM_PLAYER < temperatureConfig.searchDistance
        var value = 0.0

        // if existing value, return value
        if (calculated > 0) {
            value = calculated
        } else if (inRange && CONFIGURED_HEAT_LEVEL != null) {
            // Calculate base heat effect using smoother exponential decay
            val effectiveDistance: Double = max(DISTANCE_FROM_PLAYER, 0.5)
            value = CONFIGURED_HEAT_LEVEL * exp(-1.0 * (effectiveDistance / temperatureConfig.searchDistance))

            if (!canReachPlayer()) value *= 0.3

            // Apply additional smooth falloff in transition zone for very gradual temperature changes
            if (DISTANCE_FROM_PLAYER > DIMINISH_DISTANCE) {
                var transitionFactor: Double =
                    1.0 - ((DISTANCE_FROM_PLAYER - DIMINISH_DISTANCE) / (temperatureConfig.searchDistance - DIMINISH_DISTANCE))
                transitionFactor = max(transitionFactor, 0.0)
                // Apply smooth transition curve
                transitionFactor = transitionFactor.pow(2.0) // Quadratic falloff for smoother transition
                value *= transitionFactor
            }
            calculated = value
        }

        return value
    }

    private fun findAirPath(start: Block, end: Block?, visited: MutableSet<Block?>): Boolean {
        if (visited.size > 25) return false // Prevent excessive searching

        if (start == end) return true
        visited.add(start)

        // Check all adjacent blocks
        for (face in BlockFace.entries) {
            val adjacent = start.getRelative(face)
            if (!visited.contains(adjacent) && !adjacent.type.isOccluding()) {
                if (findAirPath(adjacent, end, visited)) {
                    adjacent.world.spawnParticle<DustOptions?>(
                        Particle.DUST, adjacent.location.add(0.5, 0.5, 0.5), 1,
                        DustOptions(Color.RED, 1.0f)
                    )
                    return true
                }
            }
        }
        return false
    }

    private fun hasDirectPath(source: Location, target: Location): Boolean {
        // Get direction vector from source to target
        val direction = target.toVector().subtract(source.toVector())

        // If source is a campfire, add 0.2 to Y component to account for smoke
        if (source.block.type == Material.CAMPFIRE) {
            direction.setY(direction.getY() + 0.5)
        }

        val distance = direction.length()

        // Perform rayTrace from source to target
        val result = source.getWorld().rayTraceBlocks(
            source,
            direction.normalize(),
            distance,
            FluidCollisionMode.NEVER,  // Ignore fluids
            true // Ignore passable blocks
        )

        // If no collision was found, there's a clear path
        // If there was a collision, check if it's at the target block
        return result == null ||
                (result.hitBlock != null &&
                        result.hitBlock!!.location == target.block.location)
    }
}