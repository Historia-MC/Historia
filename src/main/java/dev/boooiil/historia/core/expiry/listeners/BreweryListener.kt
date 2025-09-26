package dev.boooiil.historia.core.expiry.listeners

import com.dre.brewery.api.events.IngedientAddEvent
import dev.boooiil.historia.expiry.HistoriaExpiry
import dev.boooiil.historia.core.expiry.block.FluidContent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class BreweryListener : Listener {
    @EventHandler
    fun onIngredientAdd(event: IngedientAddEvent) {
        val cauldron = HistoriaExpiry.cauldrons.get(event.block) ?: return

        if (cauldron.content == FluidContent.SALT_WATER) {
            event.isCancelled = true
        }
    }
}
