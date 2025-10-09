package dev.boooiil.historia.core.condition

import org.bukkit.entity.Player

interface Condition {
    fun satisfied(ctx: Context): Boolean

    data class Context(
        val player: Player? = null,
    )
}