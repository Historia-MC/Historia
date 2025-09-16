package dev.boooiil.historia.core.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

inline fun <reified V> CommandContext<*>.getArgument(name: String): V =
    this.getArgument(name, V::class.java)

fun CommandContext<CommandSourceStack>.requirePlayerExecutor() = this.source.executor as? Player ?: throw SimpleCommandExceptionType(
    MessageComponentSerializer.message().serialize(Component.text("Command must be executed on or as a player!"))
).create()