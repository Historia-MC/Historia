package dev.boooiil.historia.core.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.plus
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import net.kyori.adventure.text.Component

val commandProficiency: LiteralCommandNode<CommandSourceStack> = Commands.literal("proficiency")
    .then(Commands.literal("set")
        .then(Commands.argument("player", ArgumentTypes.player())
            .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
                .executes { executeSet(it) }
            )
        )
    )
    .then(Commands.literal("get")
        .executes { executeGet(it) }
        .then(Commands.argument("player", ArgumentTypes.player())
            .executes { executeGet(it) }
        )
    )
    .then(Commands.literal("list")
        .executes { executeList(it) }
    )
    .build()

private fun executeSet(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayers("player")
    val proficiency = ctx.getArgument<Proficiency>("proficiency")

    players.forEach { player ->
        val hPlayer = HistoriaPlayer(player.uniqueId)
        hPlayer.changeProficiency(proficiency.key)
        ctx.source.sender.sendMessage(
            Component.text("Set proficiency for ") + player.displayName() + Component.text(" to ") + proficiency.displayName
        )
    }
    return 1
}

private fun executeGet(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayersOrExecutor("player")

    players.forEach { player ->
        val hPlayer = HistoriaPlayer(player.uniqueId)
        val proficiency = hPlayer.proficiency
        ctx.source.sender.sendMessage(player.displayName() + Component.text(" has proficiency: ") + proficiency.displayName)
    }
    return 1
}

private fun executeList(ctx: CommandContext<CommandSourceStack>): Int {
    val message = Component.text("Proficiencies: ")
        .append(RegistryHolder.PROFICIENCY_REGISTRY.values
            .sortedBy { it.displayName.toString().lowercase() }
            .map { it.displayName }
            .reduceOrNull { acc, comp -> acc + Component.text(", ") + comp }
            ?: Component.text("")
        )
    ctx.source.sender.sendMessage(message)
    return 1
}