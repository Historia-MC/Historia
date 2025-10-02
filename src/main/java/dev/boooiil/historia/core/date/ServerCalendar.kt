package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.HistoriaCore

/** Keeps track of the server time. */
object ServerCalendar {
    /** The number of ticks in one game day. */
    const val GAME_DAY_TICKS: Int = 144000

    /** Offset applied when calculating the current day. */
    var dayOffset = 0

    /** Number of days since server start. */
    val daysSinceStart: Int
        get() = (HistoriaCore.server.worlds[0].gameTime / GAME_DAY_TICKS).toInt() + dayOffset

    /** Returns the current date in the specified calendar system */
    fun current(calendar: Calendar = Calendar.Gregorian): CalendarDate {
        return calendar.dateOf(daysSinceStart)
    }
}