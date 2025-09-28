package dev.boooiil.historia.core.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

inline fun <reified V> CommandContext<*>.getArgument(name: String): V =
    this.getArgument(name, V::class.java)

inline fun <reified V> CommandContext<*>.getOptionalArgument(name: String, default: V): V {
    return try {
        this.getArgument<V>(name)
    } catch (_: IllegalArgumentException) {
        default
    }
}

fun CommandContext<CommandSourceStack>.getPlayers(argumentName: String): List<Player> {
    val playerSelector = this.getArgument<PlayerSelectorArgumentResolver>(argumentName)
    return playerSelector.resolve(this.source)
}

fun CommandContext<CommandSourceStack>.requirePlayerExecutor() = this.source.executor as? Player ?: throw SimpleCommandExceptionType(
    MessageComponentSerializer.message().serialize(Component.text("Command must be executed on or as a player!"))
).create()

fun CommandContext<CommandSourceStack>.getPlayersOrExecutor(argumentName: String): List<Player> {
    return try {
        this.getPlayers(argumentName)
    } catch (_: IllegalArgumentException) {
        listOf(this.requirePlayerExecutor())
    }
}