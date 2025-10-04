package dev.boooiil.historia.core.commands

import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.util.plus
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import net.kyori.adventure.text.Component

val commandTemperature: LiteralCommandNode<CommandSourceStack> = Commands.literal("temperature")
    .then(
        Commands.literal("set")
            .then(
                Commands.argument("player", ArgumentTypes.player())
                    .then(
                        Commands.argument("temperature", DoubleArgumentType.doubleArg())
                            .executes { executeSet(it) }
                    )
            )
    )
    .then(
        Commands.literal("get")
            .executes { executeGet(it) }
            .then(
                Commands.argument("player", ArgumentTypes.player())
                    .executes { executeGet(it) }
            )
    )
    .build()

private fun executeSet(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayers("player")
    val temperature = ctx.getArgument<Double>("temperature")

    players.forEach { player ->
        val hPlayer = PlayerStorage.getPlayer(player.uniqueId)
        hPlayer.temperatureCalculator.setTemperature(temperature)
        ctx.source.sender.sendMessage(
            Component.text("Set temperature for ") + player.displayName() + Component.text(" to $temperature")
        )
    }
    return 1
}

private fun executeGet(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getOptionalPlayers("player")

    players.forEach { player ->
        val hPlayer = HistoriaPlayer(player.uniqueId)
        ctx.source.sender.sendMessage(player.displayName() + Component.text(" has temperature: ${hPlayer.currentTemperature}"))
    }
    return 1
}