package dev.boooiil.historia.core.expiry.listeners.player

import dev.boooiil.historia.core.configuration.specific.ExpiryConfig
import org.bukkit.block.Biome
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBucketFillEvent

class PlayerBucketFillListener : Listener {
    @EventHandler
    fun onPlayerBucketFill(event: PlayerBucketFillEvent) {
        val biome = event.blockClicked.biome
        if (biome in seawaterBiomes) {
            event.itemStack = ExpiryConfig.seawaterBucket
        }
    }
}

private val seawaterBiomes = listOf(
    Biome.OCEAN,
    Biome.FROZEN_OCEAN,
    Biome.DEEP_OCEAN,
    Biome.WARM_OCEAN,
    Biome.LUKEWARM_OCEAN,
    Biome.COLD_OCEAN,
    Biome.DEEP_LUKEWARM_OCEAN,
    Biome.DEEP_COLD_OCEAN,
    Biome.DEEP_FROZEN_OCEAN,

    Biome.BEACH,
    Biome.SNOWY_BEACH,
    Biome.STONY_SHORE,
)