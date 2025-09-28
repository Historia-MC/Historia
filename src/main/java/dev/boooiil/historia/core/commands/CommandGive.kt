package dev.boooiil.historia.core.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.items.HistoriaItem
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.util.CoreLogger
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import org.bukkit.entity.Player

val commandGive: LiteralCommandNode<CommandSourceStack> = Commands.literal("give")
    .then(Commands.argument("player", ArgumentTypes.player())
        .then(Commands.argument("item", HistoriaArgumentTypes.item())
            .executes(::executeGive)
            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                .executes(::executeGive)
            )
        )
    )
    .then(Commands.argument("item", HistoriaArgumentTypes.item())
        .executes(::executeGive)
        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
            .executes(::executeGive)
        )
    ).build()

private fun executeGive(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayersOrExecutor("player")
    val item = ctx.getArgument<HistoriaItem>("item")
    val amount = ctx.getArgumentOrDefault("amount", 1)

    players.forEach { player ->
        val stack = item.createItemStack(amount)
        player.inventory.addItem(stack)
    }
    return 1
}