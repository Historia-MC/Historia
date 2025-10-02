package dev.boooiil.historia.core.temperature

import dev.boooiil.historia.core.configuration.ConfigurationLoader
import dev.boooiil.historia.core.configuration.specific.TemperatureConfig
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.NumberUtils
import org.bukkit.*
import org.bukkit.Particle.DustOptions
import org.bukkit.block.Block
import org.bukkit.block.data.Waterlogged
import org.bukkit.block.data.type.TrapDoor
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.jspecify.annotations.NullMarked
import java.util.*

@NullMarked
class TemperatureCalculator(private val gradualTemperature: GradualTemperature, private val uuid: UUID) {

    private val tempConfig: TemperatureConfig = ConfigurationLoader.getTemperatureConfig()
    private var extremeTemperatureCount = 0
    private val player: Player =
        Bukkit.getPlayer(uuid) ?: throw IllegalArgumentException("Player with UUID $uuid not found")

    /**
     * Entry method for temperature calculation. This is called by the
     * temperature runnable every x seconds as defined.
     */
    fun poll(): Double {

        var result = heatBlocksValue() + potionValue() + activityValue() +
                weatherValue() + biomeValue() + timeValue()

        if (!isUnderground()) {
            result += sunValue() + weatherValue()
        }

        if (result != gradualTemperature.target) {
            CoreLogger.debugToConsole(
                "Temperature change for " + player.name + ": " + gradualTemperature.target
                        + " -> " + result
            )

            gradualTemperature.target = result
        }

        when {
            result > tempConfig.maximum -> extremeTemperatureCount++
            result < tempConfig.minimum -> extremeTemperatureCount--
            extremeTemperatureCount > 0 -> extremeTemperatureCount--
            extremeTemperatureCount < 0 -> extremeTemperatureCount++
        }

        tryDebuff()

        return gradualTemperature.progress()
    }

    /**
     * Get the current temperature for this calculator.
     *
     * @return current temperature
     */
    fun temperature(): Double {
        return NumberUtils.roundDouble(gradualTemperature.current, 2)
    }

    /**
     * Force set the current temperature for this calculator.
     *
     * @param temperature new temperature
     */
    fun setTemperature(temperature: Double) {
        gradualTemperature.current = temperature
    }

    /**
     * Calculate temperature change based on player activity
     * e.g. sprinting, swimming, blocking, in water
     *
     * @return temperature change value
     */
    private fun activityValue(): Double {
        var temp = 0.0

        // we should change these to be config values.

        if (player.isSprinting) temp += 2.0
        if (player.isSwimming) temp += 1.5

        if (player.isBlocking) temp += 1.0
        if (player.isInWater) temp += ConfigurationLoader.getTemperatureConfig().waterTemperature
        return temp
    }

    // not sure if we want this
    private fun potionValue(): Double {
        var temp = 0.0

        for (effect in player.activePotionEffects) {
            temp += tempConfig.getPotionValue(effect.type.toString())
        }

        return temp
    }

    private fun heatBlocksValue(): Double {
        val searchDistance = tempConfig.searchDistance.toInt()
        val heatSources: MutableSet<HeatSource> = HashSet<HeatSource>()
        var highest: HeatSource? = null
        var calculated = 0.0

        // Scan for heat sources
        for (x in -searchDistance..searchDistance) {
            for (y in -searchDistance..searchDistance) {
                for (z in -searchDistance..searchDistance) {
                    val block: Block = player.location.block.getRelative(x, y, z)

                    val hs = HeatSource(player.location, block)

                    // if heat source is highest
                    if (highest == null || highest.getHeatValue() < hs.getHeatValue()) highest = hs
                    heatSources.add(hs)

                }
            }
        }

        // this is here in case we want to do something with
        // low vs highest heat sources, else we would just
        // add the sources in the loop above
        for (source in heatSources) {
            calculated += source.getHeatValue()

            if (source.getHeatValue() > 0) {
                CoreLogger.debugToConsole(
                    "drawing to heat source: " + source.block.type,
                    "with distance " + source.playerPosition.distance(source.block.location).toInt()
                )
                spawnParticleLine(player.uniqueId, source.block, source.playerPosition)
            }
        }

        return calculated
    }

    /**
     * Calculate temperature change based on weather conditions
     * e.g. clear, rain
     *
     * @return temperature change value
     */
    private fun weatherValue(): Double {
        val world = player.world

        return if (world.isClearWeather && !isUnderground()) tempConfig.clearValue() else tempConfig.rainValue()
    }

    private fun biomeValue(): Double {
        return tempConfig.getBiomeValue(player.location.block.biome)
    }

    private fun timeValue(): Double {
        val world = player.world
        val time = world.time
        // Get time modifier values from config
        val nightMod: Double = tempConfig.timeNightModifier
        val noonMod: Double = tempConfig.timeNoonModifier
        val range = noonMod - nightMod

        return if (time < 6000) {
            // Morning warming
            nightMod + (range * (time / 6000.0))
        } else if (time < 18000) {
            // Afternoon cooling
            noonMod - (range * ((time - 6000) / 12000.0))
        } else {
            // Night
            nightMod
        }
    }


    // i'm not sure if this is necessary if we are already checking for skylight
    private fun isExposedToSky(): Boolean {
        val block: Block = player.location.block
        val world: World = block.world

        // does not make sense to check if the block player is
        // standing on is solid.
//        if (block.type.isSolid()) {
//            return false
//        }
        // Check the blocks above the player to determine if they are exposed to the sky
        for (y in block.y + 1..world.maxHeight) {
            val aboveBlock = block.world.getBlockAt(block.x, y, block.z)

            if (aboveBlock.type.isAir) {
                continue  // Air blocks don't prevent exposure to the sky
            }

            if (aboveBlock.blockData is Waterlogged) {
                continue  // Waterlogged blocks don't prevent exposure to the sky
            }

            if (aboveBlock.blockData is TrapDoor) {
                continue  // Trapdoors don't prevent exposure to the sky
            }
            // CoreLogger.infoToPlayer("Block above: " + aboveBlock.getType(), uuid);
            return false // Found a non-transparent block, not exposed to the sky
        }
        // CoreLogger.infoToPlayer("Clear", uuid);
        return true // No solid blocks above, exposed to the sky
    }

    private fun sunValue(): Double {

        val skyLight = player.location.block.lightFromSky.toDouble()

        // Get light modifier values from config
        val minModifier: Double = tempConfig.lightMinModifier
        val maxModifier: Double = tempConfig.lightMaxModifier

        val range = maxModifier - minModifier

        if (skyLight == 0.0) {
            return minModifier
        }

        return minModifier + ((skyLight / 15.0) * range)
    }

    private fun isUnderground(): Boolean {
        val block = player.location.block

        // Check if block has skylight and is below surface
        val hasSkylight = block.lightFromSky.toInt() != 0
        val isUnderSeaLevel = block.y < block.world.seaLevel
        val isUnderWater: Boolean = block.world.getBlockAt(block.location.add(0.0, 1.0, 0.0)).isLiquid

        // does not have skylight, or is below sea level and not under water
        // not sure if this is the best way to approach this calc
        // players could be in homes that are below the sea level
        // TODO: needs more testing
        return !hasSkylight || (isUnderSeaLevel && !isUnderWater)

    }

    private fun tryDebuff() {

        // handle extreme hot
        if (extremeTemperatureCount >= 10) {
            CoreLogger.warnToPlayer("You are extremely hot!", player.uniqueId)
            player.damage(extremeTemperatureCount * 0.1)
            val playerLoc = player.location // Player's feet location
            val abovePlayerLoc = player.location.add(0.0, 4.0, 0.0) // 3 blocks above player's feet

            // Calculate distance and direction between player and the location above
            val distance = playerLoc.distance(abovePlayerLoc)
            val direction = abovePlayerLoc.subtract(playerLoc).toVector().normalize()

            // Spawn particles along the line
            var i = 0.0
            while (i < distance) {
                val particleLoc = playerLoc.clone().add(direction.clone().multiply(i))
                player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleLoc, 1)
                i += 0.1
            }
        }

        // handle extreme cold
        else if (extremeTemperatureCount <= -10) {
            // COLD
            CoreLogger.warnToPlayer("You are freezing!", player.uniqueId)
            player.freezeTicks = 40
        }

        // handle hot
        else if (extremeTemperatureCount > 5) {
            player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 20 * 10, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.MINING_FATIGUE, 20 * 10, 1))
            CoreLogger.warnToPlayer("You are feeling very warm.", player.uniqueId)
        }

        // handle cold
        else if (extremeTemperatureCount < -5) {
            player.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 20 * 10, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.WEAKNESS, 20 * 10, 1))
            CoreLogger.warnToPlayer("You are feeling very cold.", player.uniqueId)
        }

    }


    // helper
    private fun spawnParticleLine(id: UUID, block: Block?, targetLoc: Location) {
        if (block == null) {
            return
        }

        // Always use block center coordinates
        val blockLoc = block.location.add(0.5, 0.5, 0.5)

        // Calculate distance and direction between target point and block
        val distance = targetLoc.distance(blockLoc)
        val direction = blockLoc.subtract(targetLoc).toVector().normalize()

        // Spawn particles along the line
        var i = 0.0
        while (i < distance) {
            val particleLoc = targetLoc.clone().add(direction.clone().multiply(i))
            player.spawnParticle(Particle.DUST, particleLoc, 1, DustOptions(Color.RED, 1.0f))
            i += 0.1
        }
    }

}