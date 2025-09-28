package dev.boooiil.historia.core.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.util.CoreLogger
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import org.bukkit.entity.Player

val commandProficiency: LiteralCommandNode<CommandSourceStack> = Commands.literal("proficiency")
    .then(Commands.literal("set")
        .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
            .executes(::executeSet)
        )
        .then(Commands.argument("player", ArgumentTypes.player())
            .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
                .executes(::executeSet)
            )
        )
    ).build()

private fun executeSet(ctx: CommandContext<CommandSourceStack>): Int {
    val players = ctx.getPlayersOrExecutor("player")
    val proficiency = ctx.getArgument<Proficiency>("proficiency")

    players.forEach { player ->
        val hPlayer = HistoriaPlayer(player.uniqueId)
        hPlayer.changeProficiency(proficiency.name)
        CoreLogger.infoToPlayer("Changed proficiency to ${proficiency.name}", player.uniqueId)
    }
    return 1
}