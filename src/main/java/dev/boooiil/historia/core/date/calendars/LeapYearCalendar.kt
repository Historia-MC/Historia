package dev.boooiil.historia.core.date.calendars

import dev.boooiil.historia.core.date.Calendar
import dev.boooiil.historia.core.date.CalendarDate

abstract class LeapYearCalendar : Calendar {

    override fun dateOf(daysSinceEpoch: Int): CalendarDate {
        require(daysSinceEpoch >= 0) { "daysSinceEpoch must be >= 0" }

        var year = epoch.year
        var month = epoch.month
        var day = epoch.day + daysSinceEpoch

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