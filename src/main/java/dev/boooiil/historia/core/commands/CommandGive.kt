package dev.boooiil.historia.core.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.items.HistoriaItem
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.util.CoreLogger
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver

val commandGive: LiteralCommandNode<CommandSourceStack> = Commands.literal("give")
    .then(Commands.argument("player", ArgumentTypes.player())
        .then(Commands.argument("item", HistoriaArgumentTypes.item())
            .executes { ctx ->
                val playerSelector = ctx.getArgument<PlayerSelectorArgumentResolver>("player")
                val item = ctx.getArgument<HistoriaItem>("item")

                playerSelector.resolve(ctx.source).forEach { player ->
                    val stack = item.createItemStack()
                    player.inventory.addItem(stack)
                }
                return@executes 1
            }
        )
    )
    .then(Commands.argument("item", HistoriaArgumentTypes.item())
        .executes { ctx ->
            val player = ctx.requirePlayerExecutor()
            val item = ctx.getArgument<HistoriaItem>("item")

            val stack = item.createItemStack()
            player.inventory.addItem(stack)
            return@executes 1
        }
    ).build()