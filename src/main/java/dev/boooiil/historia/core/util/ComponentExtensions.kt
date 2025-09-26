package dev.boooiil.historia.core.util

import net.kyori.adventure.text.Component

operator fun Component.plus(component: Component) = append(component)