package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.HistoriaCore

/** Keeps track of the server time. */
object ServerCalendar {
    /** Number of ticks in one custom day. */
    val customDayTicks: Int = 144000

    /** Day offset applied to the start date when calculating dates. */
    var dayOffset = 0

    /** Number of days since server start. */
    val daysSinceStart: Int
        get() = (HistoriaCore.server.worlds[0].gameTime / customDayTicks).toInt() + dayOffset

    /** Returns the current date in the specified calendar system */
    fun current(calendar: Calendar = Calendar.Gregorian): CalendarDate {
        return calendar.dateOf(daysSinceStart)
    }
}