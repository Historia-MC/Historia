package dev.boooiil.historia.core.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.items.recipe.itemId
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands


val commandTest: LiteralCommandNode<CommandSourceStack> = Commands.literal("test")
    .then(Commands.literal("itemid")
        .executes {
            val player = it.requirePlayerExecutor()
            val itemId = player.inventory.itemInMainHand.itemId
            player.sendMessage(itemId.toString())
            1
        }
    )
    .build()