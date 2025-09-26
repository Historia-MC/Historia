package dev.boooiil.historia.core.expiry.listeners.world

import dev.boooiil.historia.expiry.HistoriaExpiry
import dev.boooiil.historia.core.expiry.util.Logging
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent

class ChunkLoadListener : Listener {
    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        val chunkKey = "${event.chunk.world.name}_${event.chunk.x}_${event.chunk.z}"
        Logging.infoToConsole("ChunkLoadEvent fired for $chunkKey (isNewChunk: ${event.isNewChunk})")

        HistoriaExpiry.cauldrons.load(event.getChunk())
    }

    @EventHandler
    fun onChunkUnload(event: ChunkUnloadEvent) {
        val chunkKey = "${event.chunk.world.name}_${event.chunk.x}_${event.chunk.z}"
        Logging.infoToConsole("ChunkSaveEvent fired for $chunkKey (isSaveChunk: ${event.isSaveChunk})")

        HistoriaExpiry.cauldrons.save(event.getChunk())
    }
}
