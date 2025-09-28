package dev.boooiil.historia.core.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike

operator fun Component.plus(other: ComponentLike) = append(other)