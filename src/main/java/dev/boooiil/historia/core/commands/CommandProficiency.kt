package dev.boooiil.historia.core.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.util.plus
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import net.kyori.adventure.text.Component

val commandProficiency: LiteralCommandNode<CommandSourceStack> = Commands.literal("proficiency")
    .then(commandSet())
    .then(commandGet())
    .build()

private fun commandSet() = Commands.literal("set")
    .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
        .executes(::executeSet)
    )
    .then(Commands.argument("player", ArgumentTypes.player())
        .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
            .executes(::executeSet)
        )
    )

private fun commandGet() = Commands.literal("get")
    .executes(::executeGet)
    .then(Commands.argument("player", ArgumentTypes.player())
        .executes(::executeGet)
    )

private fun executeSet(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayersOrExecutor("player")
    val proficiency = ctx.getArgument<Proficiency>("proficiency")

    players.forEach { player ->
        val hPlayer = HistoriaPlayer(player.uniqueId)
        hPlayer.changeProficiency(proficiency.name)
        ctx.source.sender.sendMessage(
            Component.text("Set proficiency for ") + player.name() + Component.text(" to ${proficiency.name}")
        )
    }
    return 1
}

private fun executeGet(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayersOrExecutor("player")

    players.forEach { player ->
        val hPlayer = HistoriaPlayer(player.uniqueId)
        val proficiency = hPlayer.proficiency
        ctx.source.sender.sendMessage(player.name() + Component.text(" has proficiency: ${proficiency.name}"))
    }
    return 1
}