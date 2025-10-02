package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.date.calendars.GregorianCalendar
import dev.boooiil.historia.core.date.calendars.HijriCalendar
import dev.boooiil.historia.core.date.calendars.JulianCalendar
import net.kyori.adventure.text.Component

/** A calendar system used to calculate and format dates. */
interface Calendar {
    /** The server start date represented in this calendar system. */
    val startDate: CalendarDate

    /** Returns the [CalendarDate] corresponding to the given number of days since server start. */
    fun dateOf(daysSinceStart: Int): CalendarDate

    /** Returns the given [CalendarDate] formatted as a [Component] according to this calendar's rules. */
    fun format(date: CalendarDate): Component

    companion object {
        val Gregorian = GregorianCalendar
        val Hijri = HijriCalendar
        val Julian = JulianCalendar
    }
}