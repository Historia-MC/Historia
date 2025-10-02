package dev.boooiil.historia.core.date.calendars

import dev.boooiil.historia.core.date.CalendarDate
import kotlin.math.abs

object JulianCalendar : StandardCalendar() {
    override val startDate = CalendarDate(1211, 12, 25, this)

    override fun daysInMonth(month: Int, year: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month")
        }
    }

    override fun monthsInYear(year: Int): Int = 12

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0
    }

    override fun format(date: CalendarDate): String {
        val month = monthName(date.month)
        val year = if (date.year >= 1) date.year else abs(date.year - 1)
        val era = if (date.year >= 1) "AD" else "BC"
        return "${date.day} $month $year $era"
    }

    fun monthName(month: Int): String {
        return when(month) {
            1 -> "Ianuarii"
            2 -> "Februarii"
            3 -> "Martii"
            4 -> "Aprilis"
            5 -> "Maii"
            6 -> "Iunii"
            7 -> "Julii"
            8 -> "Augusti"
            9 -> "Septembris"
            10 -> "Octobris"
            11 -> "Novembris"
            12 -> "Decembris"
            else -> throw IllegalArgumentException("Invalid month")
        }
    }
}