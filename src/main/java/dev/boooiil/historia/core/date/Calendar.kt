package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.date.calendars.GregorianCalendar
import dev.boooiil.historia.core.date.calendars.HijriCalendar
import dev.boooiil.historia.core.date.calendars.JulianCalendar

interface Calendar {
    val startDate: CalendarDate

    fun dateOf(daysSinceStart: Int): CalendarDate
    fun format(date: CalendarDate): String

    companion object {
        val Gregorian = GregorianCalendar
        val Hijri = HijriCalendar
        val Julian = JulianCalendar
    }
}