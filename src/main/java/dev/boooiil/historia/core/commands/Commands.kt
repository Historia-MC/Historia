package dev.boooiil.historia.core.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent

object Commands {

    fun register(event: ReloadableRegistrarEvent<Commands>) {
        event.register(commandProficiency)
        event.register(commandGive)
        event.register(commandTemperature)
    }

    fun ReloadableRegistrarEvent<Commands>.register(command: LiteralCommandNode<CommandSourceStack>) {
        this.registrar().register(command)
    }
}