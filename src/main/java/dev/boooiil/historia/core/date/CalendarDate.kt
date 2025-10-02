package dev.boooiil.historia.core.date

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike

/**
 * Used to display a date in the specified calendar system to a player
 */
data class CalendarDate(
    val year: Int,
    val month: Int,
    val day: Int,
    val calendar: Calendar,
) : ComponentLike {

    override fun toString(): String = "$year-$month-$day"
    override fun asComponent(): Component = Component.text(toString())
}