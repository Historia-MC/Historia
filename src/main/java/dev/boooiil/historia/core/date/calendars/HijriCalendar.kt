package dev.boooiil.historia.core.date.calendars

import dev.boooiil.historia.core.date.CalendarDate

object HijriCalendar : StandardCalendar() {
    override val startDate = CalendarDate(622, 7, 19, this)

    override fun daysInMonth(month: Int, year: Int): Int {
        return when {
            month % 2 == 1 -> 30
            month != 12 -> 29
            else -> if (isLeapYear(year)) 30 else 29
        }
    }

    override fun monthsInYear(year: Int): Int = 12

    private fun isLeapYear(year: Int): Boolean {
        val leapYears = setOf(2,5,7,10,13,16,18,21,24,26,29)
        return (year % 30) in leapYears
    }

    override fun format(date: CalendarDate): String {
        val month = monthName(date.month)
        return "${date.day} $month ${date.year} AH"
    }

    fun monthName(month: Int): String {
        return when(month) {
            1 -> "Muḥarram"
            2 -> "Ṣafar"
            3 -> "Rabī' al-Awwal"
            4 -> "Rabī' al-Thānī"
            5 -> "Jumādā al-Ūlā"
            6 -> "Jumādā al-Ākhirah"
            7 -> "Rajab"
            8 -> "Sha'bān"
            9 -> "Ramaḍān"
            10 -> "Shawwāl"
            11 -> "Dhū al-Qa'dah"
            12 -> "Dhū al-Ḥijjah"
            else -> throw IllegalArgumentException("Invalid month")
        }
    }
}