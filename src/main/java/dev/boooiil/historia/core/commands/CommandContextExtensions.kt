package dev.boooiil.historia.core.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

/**
 * Gets a typed argument by name.
 *
 * @param name the argument name
 * @throws IllegalArgumentException if the argument is missing or cannot be cast to [V]
 */
inline fun <reified V> CommandContext<*>.getArgument(name: String): V =
    this.getArgument(name, V::class.java)

/**
 * Gets a typed argument by name, or returns [default] if missing.
 *
 * @param name the argument name
 * @param default the value to return if the argument is missing
 */
inline fun <reified V> CommandContext<*>.getOptionalArgument(name: String, default: V): V {
    return try {
        this.getArgument<V>(name)
    } catch (_: IllegalArgumentException) {
        default
    }
}

/**
 * Gets players from the argument.
 *
 * @param argumentName the player argument name
 * @throws IllegalArgumentException if the argument is missing
 */
fun CommandContext<CommandSourceStack>.getPlayers(argumentName: String): List<Player> {
    val playerSelector = this.getArgument<PlayerSelectorArgumentResolver>(argumentName)
    return playerSelector.resolve(this.source)
}

/**
 * Returns the executor as a [Player].
 *
 * @throws CommandSyntaxException if the executor is not a player
 */
fun CommandContext<CommandSourceStack>.requirePlayerExecutor() = this.source.executor as? Player ?: throw SimpleCommandExceptionType(
    MessageComponentSerializer.message().serialize(Component.text("Command must be executed on or as a player!"))
).create()

/**
 * Gets players from the argument, or falls back to the executor.
 *
 * @param argumentName the player argument name
 * @throws CommandSyntaxException if no argument is given and the executor is not a player
 */
fun CommandContext<CommandSourceStack>.getOptionalPlayers(argumentName: String): List<Player> {
    return try {
        this.getPlayers(argumentName)
    } catch (_: IllegalArgumentException) {
        listOf(this.requirePlayerExecutor())
    }
}