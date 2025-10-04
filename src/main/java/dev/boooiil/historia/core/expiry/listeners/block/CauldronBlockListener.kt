package dev.boooiil.historia.core.expiry.listeners.block

import dev.boooiil.historia.core.expiry.block.FluidContent
import dev.boooiil.historia.core.expiry.block.HCauldrons
import dev.boooiil.historia.core.expiry.block.fluidContentOf
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPistonRetractEvent
import org.bukkit.event.block.CauldronLevelChangeEvent

class CauldronBlockListener : Listener {
    @EventHandler
    fun onCauldronLevelChange(event: CauldronLevelChangeEvent) {
        val cauldron = HCauldrons.get(event.block) ?: return

        //Cauldron emptied
        if (event.newState.type == Material.CAULDRON) {
            cauldron.content = FluidContent.EMPTY
        }

        //Cauldron filled
        if (event.block.type == Material.CAULDRON) {
            if (cauldron.content != FluidContent.SALT_WATER) {
                cauldron.content = fluidContentOf(event.newState.type)
            }
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val cauldron = HCauldrons.get(event.block) ?: return
        cauldron.isMarkedForRemoval = true
    }

    @EventHandler
    fun onPistonExtend(event: BlockPistonExtendEvent) {
        for (block in event.blocks) {
            val cauldron = HCauldrons.get(block)
            cauldron?.isMarkedForRemoval = true
        }
    }

    @EventHandler
    fun onPistonRetract(event: BlockPistonRetractEvent) {
        for (block in event.blocks) {
            val cauldron = HCauldrons.get(block)
            cauldron?.isMarkedForRemoval = true
        }
    }
}
