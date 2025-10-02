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
import kotlin.math.exp

@NullMarked
class TemperatureCalculator(private val internalTemperature: GradualTemperature, private val uuid: UUID) {

    private val player: Player =
        Bukkit.getPlayer(uuid) ?: throw IllegalArgumentException("Player with UUID $uuid not found")
    private val tempConfig: TemperatureConfig = ConfigurationLoader.getTemperatureConfig()
    private var extremeTemperatureCount = 0
    var ambientTemperature = GradualTemperature(internalTemperature.current, internalTemperature.current, 0.1, 0.1)

    /**
     * Entry method for temperature calculation. This is called by the
     * temperature runnable every x seconds as defined.
     *
     * @return the current internal temperature after calculation
     */
    fun poll(): Double {

        var result = heatBlocksValue() + potionValue() + activityValue() +
                weatherValue() + biomeValue() + timeValue()

        if (!isUnderground()) {
            result += sunValue() + weatherValue()
        }

        if (result != ambientTemperature.target) {
            CoreLogger.debugToConsole(
                "Temperature change for " + player.name + ": " + internalTemperature.target
                        + " -> " + result
            )

            ambientTemperature.target = result
            internalTemperature.target = ambientTemperature.progress()
        }

        when {
            internalTemperature.current > tempConfig.maximum -> extremeTemperatureCount++
            internalTemperature.current < tempConfig.minimum -> extremeTemperatureCount--
            extremeTemperatureCount > 0 -> extremeTemperatureCount--
            extremeTemperatureCount < 0 -> extremeTemperatureCount++
        }

        tryDebuff()

        return internalTemperature.progress()
    }

    /**
     * Get the current temperature for this calculator.
     *
     * @return current temperature rounded to 2 decimal places
     */
    fun temperature(): Double {
        return NumberUtils.roundDouble(internalTemperature.current, 2)
    }

    /**
     * Get the current ambient temperature for this calculator.
     *
     * @return current ambient temperature rounded to 2 decimal places
     */
    fun ambient(): Double {
        return NumberUtils.roundDouble(ambientTemperature.current, 2)
    }

    /**
     * Force set the current temperature for this calculator.
     *
     * @param temperature new temperature
     */
    fun setTemperature(temperature: Double) {
        internalTemperature.current = temperature
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
        HashSet<HeatSource>()
        var calculated = 0.0

        val feetLoc = player.location
        val bodyLoc = feetLoc.clone().add(0.0, 1.0, 0.0)
        val headLoc = feetLoc.clone().add(0.0, 1.8, 0.0)
        val parts = listOf(feetLoc, bodyLoc, headLoc)

        for (x in -searchDistance..searchDistance) {
            for (y in -searchDistance..searchDistance) {
                for (z in -searchDistance..searchDistance) {

                    var greatestPart: HeatSource? = null

                    for (part in parts) {
                        val block = part.block.getRelative(x, y, z)
                        val heatSource = HeatSource(part, block)
                        val heatValue = heatSource.getHeatValue()

                        if (greatestPart == null || heatValue > greatestPart.getHeatValue()) {
                            greatestPart = heatSource
                        }
                    }

                    if (greatestPart == null) {
                        continue
                    }

                    // Only add if heat can reach player and value is significant
                    if (greatestPart.getHeatValue() > 0 && greatestPart.canReachPlayer()) {
                        // Stronger exponential decay for realism
                        val distance = player.location.distance(greatestPart.block.location)
                        val decay = exp(-2.0 * (distance / tempConfig.searchDistance))
                        calculated += greatestPart.getHeatValue() * decay
                        spawnParticleLine(player.uniqueId, greatestPart.block, greatestPart.playerPosition)
                    }
                }
            }
        }

        // this is here in case we want to do something with
        // low vs highest heat sources, else we would just
        // add the sources in the loop above
//        for (source in heatSources) {
//            if (highest != null && source.getHeatValue() > 0) {
//                calculated += 0.0
//
//                if (source == highest) {
//                    calculated += highest.getHeatValue()
//                } else {
//                    val factor = exp(highest.getHeatValue() / 40.0) // magic number
//                    calculated += source.getHeatValue() * factor
//                }
//
//                CoreLogger.debugToConsole(
//                    "drawing to heat source: " + source.block.type,
//                    "with distance " + source.playerPosition.distance(source.block.location).toInt()
//                )
//                spawnParticleLine(player.uniqueId, source.block, source.playerPosition)
//            }
//        }

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
        val world = player.world
        val time = world.time
        val biomePair = tempConfig.getBiomeValue(player.location.block.biome)

        // Get time modifier values from config
        val noonMod: Double = biomePair.first
        val nightMod: Double = biomePair.second
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

        val extremeHeat = 10
        val heat = 5
        val cold = -5
        val extremeCold = -10

        // handle extreme hot
        if (extremeTemperatureCount >= extremeHeat) {
            extremeTemperatureCount = extremeHeat // cap it so we don't do too much damage
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
        else if (extremeTemperatureCount <= extremeCold) {
            extremeTemperatureCount = extremeCold // cap it so we don't do too much damage
            CoreLogger.warnToPlayer("You are freezing!", player.uniqueId)
            player.freezeTicks += 45
        }

        // handle hot
        else if (extremeTemperatureCount > heat) {
            player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 20 * 10, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.MINING_FATIGUE, 20 * 10, 1))
            CoreLogger.warnToPlayer("You are feeling very warm.", player.uniqueId)
        }

        // handle cold
        else if (extremeTemperatureCount < cold) {
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