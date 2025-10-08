package dev.boooiil.historia.core.date

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike

/** Used to display a date in the specified calendar system to a player. */
data class CalendarDate(
    val year: Int,
    val month: Int,
    val day: Int,
    val calendar: Calendar,
) : ComponentLike {

    /** Returns the date as a [String] in ISO format: YYYY-MM-DD. */
    override fun toString(): String = "$year-$month-$day"

    /** Returns the date as a [Component] formatted using the calendar's formatting rules. */
    override fun asComponent(): Component = calendar.format(this)
}