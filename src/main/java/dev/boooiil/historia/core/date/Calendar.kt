package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.date.calendars.GregorianCalendar
import dev.boooiil.historia.core.date.calendars.HijriCalendar
import dev.boooiil.historia.core.date.calendars.JulianCalendar
import net.kyori.adventure.text.Component

interface Calendar {
    val epoch: CalendarDate

    fun dateOf(daysSinceEpoch: Int): CalendarDate
    fun format(date: CalendarDate): String

    companion object {
        val Gregorian = GregorianCalendar
        val Hijri = HijriCalendar
        val Julian = JulianCalendar
    }
}