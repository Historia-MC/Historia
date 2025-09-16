package dev.boooiil.historia.core.commands

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.boooiil.historia.core.registry.Registry
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import java.util.concurrent.CompletableFuture

class RegistryArgument<T : Any>(
    private val registry: Registry<T>
) : CustomArgumentType<T, String> {

    override fun parse(reader: StringReader): T {
        val input = reader.readUnquotedString()

        val key = NamespacedKey.fromString(input) ?: throw SimpleCommandExceptionType(
            MessageComponentSerializer.message().serialize(Component.text("$input is not a namespaced key!"))
        ).create()

        return registry.get(key) ?: throw SimpleCommandExceptionType(
            MessageComponentSerializer.message().serialize(Component.text("Could not find key $key in registry!"))
        ).create()
    }

    override fun getNativeType(): ArgumentType<String> {
        return StringArgumentType.word()
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        val input = builder.remaining.lowercase()

        registry.keys
            .filter { it.asString().startsWith(input) || it.value().startsWith(input) }
            .forEach { builder.suggest(it.asString()) }
        return builder.buildFuture()
    }
}