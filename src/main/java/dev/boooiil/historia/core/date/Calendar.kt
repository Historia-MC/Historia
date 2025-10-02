package dev.boooiil.historia.core.date

interface Calendar {
    val epoch: CalendarDate

    fun dateOf(daysSinceEpoch: Int): CalendarDate {
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

    fun daysInMonth(month: Int, year: Int): Int
    fun daysInYear(year: Int): Int
    fun monthsInYear(year: Int): Int

    object Gregorian : Calendar {
        override val epoch = CalendarDate(1212, 1, 1, this)

        override fun daysInMonth(month: Int, year: Int): Int {
            return when (month) {
                1, 3, 5, 7, 8, 10, 12 -> 31
                4, 6, 9, 11 -> 30
                2 -> if (isLeapYear(year)) 29 else 28
                else -> throw IllegalArgumentException("Invalid month")
            }
        }

        override fun daysInYear(year: Int): Int {
            return if (isLeapYear(year)) 366 else 365
        }

        override fun monthsInYear(year: Int): Int {
            return 12
        }

        private fun isLeapYear(year: Int): Boolean {
            return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
        }
    }

    object Julian : Calendar {
        override val epoch = CalendarDate(1211, 12, 25, this)

        override fun daysInMonth(month: Int, year: Int): Int {
            return when (month) {
                1, 3, 5, 7, 8, 10, 12 -> 31
                4, 6, 9, 11 -> 30
                2 -> if (isLeapYear(year)) 29 else 28
                else -> throw IllegalArgumentException("Invalid month")
            }
        }

        override fun daysInYear(year: Int): Int {
            return if (isLeapYear(year)) 366 else 365
        }

        override fun monthsInYear(year: Int): Int = 12

        private fun isLeapYear(year: Int): Boolean {
            return year % 4 == 0
        }
    }

    object Hijri : Calendar {
        override val epoch = CalendarDate(622, 7, 19, this)

        override fun daysInMonth(month: Int, year: Int): Int {
            return when {
                month % 2 == 1 -> 30
                month != 12 -> 29
                else -> if (isLeapYear(year)) 30 else 29
            }
        }

        override fun daysInYear(year: Int): Int {
            return (1..12).sumOf { daysInMonth(it, year) }
        }

        override fun monthsInYear(year: Int): Int = 12

        private fun isLeapYear(year: Int): Boolean {
            val leapYears = setOf(2,5,7,10,13,16,18,21,24,26,29)
            return (year % 30) in leapYears
        }
    }
}