package dev.boooiil.historia.core.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.items.HistoriaItem
import dev.boooiil.historia.core.util.plus
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import net.kyori.adventure.text.Component

val commandGive: LiteralCommandNode<CommandSourceStack> = Commands.literal("give")
    .then(Commands.argument("player", ArgumentTypes.player())
        .then(Commands.argument("item", HistoriaArgumentTypes.item())
            .executes { executeGive(it) }
            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                .executes { executeGive(it) }
            )
        )
    ).build()

private fun executeGive(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayers("player")
    val item = ctx.getArgument<HistoriaItem>("item")
    val amount = ctx.getOptionalArgument("amount", 1)

    players.forEach { player ->
        val stack = item.createItemStack(amount)
        player.inventory.addItem(stack)
        ctx.source.sender.sendMessage(
            Component.text("Gave $amount ") + stack.displayName() + Component.text(" to ") + player.displayName()
        )
    }
    return 1
}