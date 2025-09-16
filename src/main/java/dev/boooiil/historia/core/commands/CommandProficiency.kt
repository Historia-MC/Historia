package dev.boooiil.historia.core.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import org.bukkit.entity.Player

val commandProficiency: LiteralCommandNode<CommandSourceStack> = Commands.literal("proficiency")
    .then(Commands.literal("set")
        .then(Commands.argument("player", ArgumentTypes.player())
            .then(Commands.argument("proficiency", HistoriaArgumentTypes.proficiency())
                .executes { ctx ->
                    val player = ctx.getArgument<Player>("player")
                    val proficiency = ctx.getArgument<Proficiency>("proficiency")

                    val hPlayer = HistoriaPlayer(player.uniqueId)
                    hPlayer.changeProficiency(proficiency.name)
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