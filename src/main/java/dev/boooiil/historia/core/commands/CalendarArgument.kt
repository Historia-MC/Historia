package dev.boooiil.historia.core.commands

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.boooiil.historia.core.date.Calendar
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import net.kyori.adventure.text.Component
import java.util.concurrent.CompletableFuture

private val calendars = mapOf(
    "gregorian" to Calendar.Gregorian,
    "hijri" to Calendar.Hijri,
    "julian" to Calendar.Julian,
)

class CalendarArgument : CustomArgumentType<Calendar, String> {

    override fun parse(reader: StringReader): Calendar {
        val input = reader.readUnquotedString()

        return calendars[input] ?: throw SimpleCommandExceptionType(
            MessageComponentSerializer.message().serialize(Component.text("$input is not a valid calendar!"))
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

        calendars.keys
            .filter { it.startsWith(input) }
            .forEach { builder.suggest(it) }
        return builder.buildFuture()
    }
}