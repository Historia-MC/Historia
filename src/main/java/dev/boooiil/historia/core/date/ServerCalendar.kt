package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.configuration.specific.DateConfig

/** Keeps track of the server date. */
object ServerCalendar {
    /** Day offset applied to the start date when calculating dates. */
    var dayOffset = 0

    /** Number of days since server start. */
    val daysSinceStart: Int
        get() = (HistoriaCore.server.worlds[0].gameTime / DateConfig.CUSTOM_DAY_LENGTH).toInt() + dayOffset

    /** Returns the current date in the specified calendar system */
    fun current(calendar: Calendar = Calendar.Gregorian): CalendarDate {
        return calendar.dateOf(daysSinceStart)
    }
}