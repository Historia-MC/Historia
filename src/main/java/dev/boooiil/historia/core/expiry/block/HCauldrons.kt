package dev.boooiil.historia.core.expiry.block

import dev.boooiil.historia.expiry.HistoriaExpiry
import dev.boooiil.historia.core.expiry.util.Logging
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.persistence.PersistentDataType

private val cauldronKey: NamespacedKey = HistoriaExpiry.getNamespacedKey("cauldron")

class HCauldrons {
    private val cauldrons: MutableMap<Location, HCauldron> = mutableMapOf()
    private val markedForRemoval: MutableSet<HCauldron> = mutableSetOf()
    private val tickFrequency = 10

    init {
        tickCauldrons()
    }

    private fun tickCauldrons() {
        val scheduler = Bukkit.getScheduler()
        scheduler.runTaskTimer(HistoriaExpiry.plugin, Runnable {
            for (cauldron in cauldrons.values) {
                cauldron.tick(tickFrequency)
                if (cauldron.isMarkedForRemoval) {
                    markedForRemoval.add(cauldron)
                }
            }
            cauldrons.values.removeAll(markedForRemoval)
            markedForRemoval.clear()
        }, tickFrequency.toLong(), tickFrequency.toLong())
    }

    fun get(block: Block): HCauldron? = get(block.location)

    fun get(location: Location): HCauldron? {
        val block = location.block
        val type = block.type

        val isCauldron = type == Material.CAULDRON || type == Material.WATER_CAULDRON
                || type == Material.LAVA_CAULDRON || type == Material.POWDER_SNOW_CAULDRON

        if (!isCauldron) {
            Logging.errorToConsole("Tried to get cauldron entity at position with no cauldron")
            return null
        }

        if (!cauldrons.containsKey(location)) {
            val cauldron = HCauldron(location)
            cauldrons[location] = cauldron
            return cauldron
        }

        val cauldron = cauldrons[location]
        if (cauldron in markedForRemoval) {
            markedForRemoval.remove(cauldron)
            val newCauldron = HCauldron(location)
            cauldrons[location] = newCauldron
            return newCauldron
        }
        return cauldron
    }

    fun load(chunk: Chunk) {
        val chunkKey = "${chunk.world.name}_${chunk.x}_${chunk.z}"
        Logging.infoToConsole("Loading chunk: $chunkKey")
        val dataContainer = chunk.persistentDataContainer
        val chunkCauldrons = dataContainer.get(cauldronKey, PersistentDataType.LIST.listTypeFrom(HCauldron.DataType()))
            ?: return

        Logging.infoToConsole("Found ${chunkCauldrons.size} cauldrons in chunk data for $chunkKey")

        for (cauldron in chunkCauldrons) {
            if (cauldron.location in cauldrons) {
                Logging.infoToConsole("Skipping cauldron at ${cauldron.location} - already in memory")
                continue
            }
            cauldrons[cauldron.location] = cauldron

            Logging.infoToConsole("Successfully loaded cauldron at ${cauldron.location} with content ${cauldron.content}")
        }
    }

    fun save(chunk: Chunk) {
        val dataContainer = chunk.persistentDataContainer
        val chunkCauldrons = cauldrons
            .filter { it.key.chunk == chunk }
            .map { it.value }

        dataContainer.set(
            cauldronKey,
            PersistentDataType.LIST.listTypeFrom(HCauldron.DataType()),
            chunkCauldrons
        )

        chunkCauldrons.forEach {
            Logging.infoToConsole("Saving cauldron with content ${it.content}")
            cauldrons.remove(it.location)
        }
    }
}
