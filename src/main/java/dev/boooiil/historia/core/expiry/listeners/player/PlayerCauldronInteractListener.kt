package dev.boooiil.historia.core.expiry.listeners.player

import dev.boooiil.historia.core.expiry.handlers.PlayerUseCauldronHandler
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerCauldronInteractListener : Listener {
    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val hand = event.hand ?: return
        val blockType = block.type

        val clickedCauldron =
            blockType == Material.CAULDRON || blockType == Material.WATER_CAULDRON || blockType == Material.LAVA_CAULDRON || blockType == Material.POWDER_SNOW_CAULDRON

        if (clickedCauldron) {
            val shouldCancel = PlayerUseCauldronHandler.playerUseCauldron(event.getPlayer(), block, hand)
            event.isCancelled = shouldCancel
        }
    }
}
