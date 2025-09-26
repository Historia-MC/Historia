package dev.boooiil.historia.core.expiry.handlers

import dev.boooiil.historia.expiry.HistoriaExpiry
import dev.boooiil.historia.core.expiry.block.FluidContent
import dev.boooiil.historia.core.expiry.util.Logging
import dev.boooiil.historia.core.expiry.util.PlayerInteractUtil
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object PlayerUseCauldronHandler {
    fun playerUseCauldron(player: Player, block: Block, hand: EquipmentSlot): Boolean {
        val blockType = block.type

        //only water and empty cauldrons should be intercepted
        if (blockType != Material.WATER_CAULDRON && blockType != Material.CAULDRON) {
            return false
        }

        val usedStack = player.inventory.getItem(hand)
        val itemType = usedStack.type

        if (itemType == Material.GLASS_BOTTLE || itemType == Material.BUCKET) {
            val success = tryEmptyCauldron(player, block, hand)
            return true
        }

        if (itemType == Material.POTION || itemType == Material.WATER_BUCKET) {
            val success = tryFillCauldron(player, block, hand)
            return true
        }

        return false
    }

    fun tryFillCauldron(player: Player, block: Block, hand: EquipmentSlot): Boolean {
        val usedStack = player.inventory.getItem(hand)
        val cauldron = HistoriaExpiry.cauldrons.get(block) ?: return false

        val fluid = FluidContent.fromItem(usedStack)
        Logging.debugToConsole(fluid.toString())

        //set bottle/bucket values
        var fluidAmount = 3
        var emptyStack = FluidContent.EMPTY.bucket
        if (usedStack.type == Material.POTION) {
            fluidAmount = 1
            emptyStack = FluidContent.EMPTY.bottle
        }

        //handle fill
        val actionSuccess = cauldron.addFluid(fluid, fluidAmount)
        if (actionSuccess) {
            PlayerInteractUtil.replaceUsedItem(player, hand, emptyStack)
            playItemFillSound(block, usedStack)
        }
        return actionSuccess
    }

    fun tryEmptyCauldron(player: Player, block: Block, hand: EquipmentSlot): Boolean {
        val usedStack = player.inventory.getItem(hand)
        val cauldron = HistoriaExpiry.cauldrons.get(block) ?: return false
        val fluid = cauldron.content

        //set bottle/bucket values
        var fluidAmount = 3
        var filledStack = fluid.bucket
        if (usedStack.type == Material.GLASS_BOTTLE) {
            fluidAmount = 1
            filledStack = fluid.bottle
        }

        //handle empty
        val actionSuccess = cauldron.takeFluid(fluid, fluidAmount)
        if (actionSuccess) {
            PlayerInteractUtil.replaceUsedItem(player, hand, filledStack)
            playItemEmptySound(block, usedStack)
        }
        return actionSuccess
    }

    private fun playItemEmptySound(block: Block, usedStack: ItemStack) {
        val sound = if (usedStack.type == Material.POTION) Sound.ITEM_BOTTLE_EMPTY
                    else Sound.ITEM_BUCKET_EMPTY
        block.world.playSound(block.location, sound, 1f, 1f)
    }

    private fun playItemFillSound(block: Block, usedStack: ItemStack) {
        val sound = if (usedStack.type == Material.POTION) Sound.ITEM_BOTTLE_FILL
                    else Sound.ITEM_BUCKET_FILL
        block.world.playSound(block.location, sound, 1f, 1f)
    }
}
