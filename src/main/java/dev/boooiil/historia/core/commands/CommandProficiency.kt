package dev.boooiil.historia.core.commands

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
        .then(Commands.argument("player", ArgumentTypes.player())
            .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
                .executes { ctx ->
                    val playerSelector = ctx.getArgument<PlayerSelectorArgumentResolver>("player")
                    val proficiency = ctx.getArgument<Proficiency>("proficiency")

                    playerSelector.resolve(ctx.source).forEach { player ->
                        val hPlayer = HistoriaPlayer(player.uniqueId)
                        hPlayer.changeProficiency(proficiency.name)
                        CoreLogger.infoToPlayer("Changed proficiency to ${proficiency.name}", player.uniqueId)
                    }
                    return@executes 1
                }
            )
        )
        .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
            .executes { ctx ->
                val player = ctx.requirePlayerExecutor()
                val proficiency = ctx.getArgument<Proficiency>("proficiency")

                val hPlayer = HistoriaPlayer(player.uniqueId)
                hPlayer.changeProficiency(proficiency.name)
                return@executes 1
            }
        )
    ).build()