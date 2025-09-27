package dev.boooiil.historia.core.expiry.block

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.configuration.specific.ExpiryConfig
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.CustomDataType
import dev.boooiil.historia.core.util.ParticleUtil
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.data.Levelled
import org.bukkit.block.data.Lightable
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Vector
import java.util.*
import kotlin.math.min

private const val BOIL_TIME_TOTAL = 20 * 10

private val validBlocks = setOf(
    Material.CAULDRON,
    Material.WATER_CAULDRON,
    Material.LAVA_CAULDRON,
    Material.POWDER_SNOW_CAULDRON
)

class HCauldron(
    val location: Location,
    content: FluidContent = fluidContentOf(location.block.type),
    boilTime: Int = 0,
) {
    var boilTime: Int = boilTime
        private set
    var isBoiling: Boolean = false
        private set
    var isMarkedForRemoval: Boolean = false
    var content: FluidContent = content
        set(value) {
            block.type = value.cauldron
            field = value
        }

    val block: Block get() = location.block
    val canBoil: Boolean get() = level == 3 && isHeated && content == FluidContent.SALT_WATER

    constructor(block: Block, content: FluidContent, boilTime: Int) :
            this(block.location, content, boilTime)

    fun tick(ticks: Int) {
        if (content != FluidContent.SALT_WATER) {
            isMarkedForRemoval = true
            isBoiling = false
            return
        }
        isBoiling = canBoil

        val insideCauldron = block.location.add(Vector(0.5, 0.7, 0.5))

        doCanBoilEffects(insideCauldron)

        if (!isBoiling) return

        doBoilEffects(insideCauldron)
        boilTime += ticks

        if (boilTime > BOIL_TIME_TOTAL) {
            doEvaporationEffects(insideCauldron)

            block.type = Material.CAULDRON
            val salt = ExpiryConfig.getSalt(2)
            block.world.dropItem(insideCauldron, salt)

            isBoiling = false
            isMarkedForRemoval = true
        }
    }

    private fun doCanBoilEffects(location: Location) {
        val rnd = Random()
        val particles = rnd.nextInt(1, 2)

        ParticleUtil.spawnParticlesWithVelocity(
            Particle.CLOUD, location, particles,
            0.0, 0.02, 0.0, 0.2, 0.0, 0.2
        )
    }

    private fun doBoilEffects(location: Location) {
        val world = location.world

        val rnd = Random()

        if (rnd.nextFloat() < 0.4) {
            world.playSound(location, Sound.BLOCK_LAVA_AMBIENT, 1f, 2f)
        }

        if (rnd.nextFloat() < 0.6) {
            val particles = rnd.nextInt(0, 2)
            ParticleUtil.spawnParticlesWithVelocity(
                Particle.CLOUD, location, particles,
                0.0, 0.1, 0.0, 0.4, 0.2, 0.4
            )
        }
        if (rnd.nextFloat() < 0.8) {
            val particles = rnd.nextInt(2, 6)
            ParticleUtil.spawnParticlesWithVelocity(
                Particle.BUBBLE_POP, location, particles,
                0.0, 0.1, 0.0, 0.4, 0.2, 0.4
            )
        }
    }

    private fun doEvaporationEffects(location: Location) {
        val world = location.world

        world.playSound(location, Sound.BLOCK_FIRE_EXTINGUISH, 1f, 1f)

        ParticleUtil.spawnParticlesWithVelocity(
            Particle.CLOUD, location, 10,
            0.0, 0.1, 0.0, 0.4, 0.2, 0.4
        )
    }


    /**
     * Adds fluid to the cauldron. If all fluid could not be added,
     * as much as possible will be added.
     *
     * @param fluid the type of fluid to add
     * @param amount the amount of fluid to add
     * @return true if action was successful
     */
    fun addFluid(fluid: FluidContent, amount: Int): Boolean {
        val validFluid = fluid != FluidContent.EMPTY && (fluid == content || content == FluidContent.EMPTY)
        if (amount <= 0 || level == 3 || !validFluid) {
            return false
        }
        if (content == FluidContent.EMPTY) {
            content = fluid
        }
        val newLevel = min(level + amount, 3)
        level = newLevel
        return true
    }

    /**
     * Takes an amount of fluid out of the cauldron. If the cauldron does
     * not have sufficient fluid none will be removed.
     *
     * @param fluid the type of fluid to add
     * @param amount the amount of fluid to add
     * @return true if action was successful
     */
    fun takeFluid(fluid: FluidContent, amount: Int): Boolean {
        if (amount > level || content == FluidContent.EMPTY || fluid != content) {
            return false
        }
        level -= amount
        return true
    }

    var level: Int
        get() = when (val data = block.blockData) {
            is Levelled -> data.level
            else -> 0
        }
        set(value) {
            if (value == 0) {
                content = FluidContent.EMPTY
            }
            val levelled = block.blockData as? Levelled ?: return
            levelled.level = value
            block.setBlockData(levelled)
        }

    val isHeated: Boolean
        get() {
            val heatSources = listOf(
                Material.MAGMA_BLOCK,
                Material.FIRE,
                Material.SOUL_FIRE,
                Material.LAVA
            )

            val underCauldron = block.location.subtract(0.0, 1.0, 0.0)
            val block = underCauldron.block
            val material = block.type

            if (material in heatSources) {
                return true
            }
            if (material == Material.CAMPFIRE || material == Material.SOUL_CAMPFIRE) {
                return (block.state.blockData as Lightable).isLit
            }
            return false
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HCauldron) return false
        return this.location == other.location
    }

    override fun hashCode(): Int = location.hashCode()

    class DataType : PersistentDataType<PersistentDataContainer, HCauldron> {

        override fun getPrimitiveType() = PersistentDataContainer::class.java
        override fun getComplexType() = HCauldron::class.java

        override fun toPrimitive(cauldron: HCauldron, context: PersistentDataAdapterContext): PersistentDataContainer {
            val dataContainer = context.newPersistentDataContainer()

            dataContainer.set(HistoriaCore.getNamespacedKey("location"), CustomDataType.LOCATION, cauldron.location)
            dataContainer.set(
                HistoriaCore.getNamespacedKey("content"),
                PersistentDataType.STRING,
                cauldron.content.name
            )
            dataContainer.set(HistoriaCore.getNamespacedKey("boil-time"), PersistentDataType.INTEGER, cauldron.boilTime)

            CoreLogger.infoToConsole("Serialized cauldron at ${cauldron.location} with content ${cauldron.content}")

            return dataContainer
        }

        override fun fromPrimitive(
            dataContainer: PersistentDataContainer,
            persistentDataAdapterContext: PersistentDataAdapterContext
        ): HCauldron {
            val location = dataContainer.get(HistoriaCore.getNamespacedKey("location"), CustomDataType.LOCATION)
                ?: throw IllegalStateException("Cauldron data missing: location")
            val contentString = dataContainer.get(HistoriaCore.getNamespacedKey("content"), PersistentDataType.STRING)
                ?: throw IllegalStateException("Cauldron data missing: content")
            val content = FluidContent.valueOf(contentString)

            val boilTime: Int =
                dataContainer.getOrDefault(HistoriaCore.getNamespacedKey("boil-time"), PersistentDataType.INTEGER, 0)

            CoreLogger.infoToConsole("Deserialized cauldron at $location with content $contentString")

            return HCauldron(location, content, boilTime)
        }
    }
}