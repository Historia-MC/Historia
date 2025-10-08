package dev.boooiil.historia.core.date.calendars

import dev.boooiil.historia.core.date.Calendar
import dev.boooiil.historia.core.date.CalendarDate

/**
 * Provides common logic to convert a number of days since start to a [CalendarDate].
 * Should be useful for most calendar systems.
 */
abstract class StandardCalendar : Calendar {

    override fun dateOf(daysSinceStart: Int): CalendarDate {
        require(daysSinceStart >= 0) { "daysSinceStart must be >= 0" }

        var year = startDate.year
        var month = startDate.month
        var day = startDate.day + daysSinceStart

        while (day > daysInMonth(month, year)) {
            day -= daysInMonth(month, year)
            month++
            if (month > monthsInYear(year)) {
                month = 1
                year++
            }
        }
        return CalendarDate(year, month, day, this)
    }

    abstract fun daysInMonth(month: Int, year: Int): Int
    abstract fun monthsInYear(year: Int): Int
}