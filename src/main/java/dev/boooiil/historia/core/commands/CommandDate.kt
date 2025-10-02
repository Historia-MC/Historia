package dev.boooiil.historia.core.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.date.Calendar
import dev.boooiil.historia.core.date.GameDate
import dev.boooiil.historia.core.util.plus
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component

val commandDate: LiteralCommandNode<CommandSourceStack> = Commands.literal("date")
    .then(Commands.literal("query")
        .executes { executeQuery(it) }
        .then(Commands.argument("calendar", HistoriaArgumentTypes.calendar())
            .executes { executeQuery(it) }
        )
    )
    .then(Commands.literal("add")
        .then(Commands.argument("days", IntegerArgumentType.integer())
            .executes { executeAdd(it) }
        )
    )
    .build()

private fun executeAdd(ctx: CommandContext<CommandSourceStack>): Int {
    GameDate.dayOffset += ctx.getArgument<Int>("days")
    ctx.source.sender.sendMessage(Component.text("Set the date to ") + GameDate.current())
    return 1
}

private fun executeQuery(ctx: CommandContext<CommandSourceStack>): Int {
    val calendar = ctx.getOptionalArgument<Calendar>("calendar", Calendar.Gregorian)
    ctx.source.sender.sendMessage(Component.text("The date is ") + GameDate.current(calendar))
    return 1
}