package dev.boooiil.historia.core.util

import dev.boooiil.historia.core.scoreboard.ScoreboardBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike

operator fun Component.plus(other: ComponentLike) = append(other)