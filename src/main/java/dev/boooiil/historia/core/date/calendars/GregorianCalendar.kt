package dev.boooiil.historia.core.date.calendars

import dev.boooiil.historia.core.date.CalendarDate
import kotlin.math.abs

object GregorianCalendar : LeapYearCalendar() {
    override val startDate = CalendarDate(1212, 1, 1, this)

    override fun daysInMonth(month: Int, year: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month")
        }
    }

    override fun monthsInYear(year: Int): Int {
        return 12
    }

    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    override fun format(date: CalendarDate): String {
        val month = monthName(date.month)
        val year = if (date.year >= 1) date.year else abs(date.year - 1)
        val era = if (date.year >= 1) "AD" else "BC"
        return "${date.day} $month $year $era"
    }

    fun monthName(month: Int): String {
        return when(month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> throw IllegalArgumentException("Invalid month")
        }
    }
}