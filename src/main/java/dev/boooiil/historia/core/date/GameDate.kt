package dev.boooiil.historia.core.date

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import java.util.*

data class GameDate(
    private val year: Int,
    private val month: Int,
    private val day: Int
) : ComponentLike {

    fun plusDays(days: Int): GameDate {
        var newYear = this.year
        var newMonth = this.month
        var newDayOfMonth = this.day + days

        while (newDayOfMonth > daysOfMonth[newMonth]!!) {
            newDayOfMonth -= daysOfMonth[newMonth]!!
            newMonth++
            if (newMonth > 12) {
                newMonth = 1
                newYear++
            }
        }
        return GameDate(newYear, newMonth, newDayOfMonth)
    }

    fun plusYears(years: Int): GameDate {
        return GameDate(year + years, month, day)
    }

    fun totalDays(): Int {
        var monthDays = 0
        for (m in 1..month) {
            monthDays += daysOfMonth.get(m)!!
        }
        return year * 365 + monthDays + day
    }

    override fun toString(): String {
        return String.format("%02d-%02d-%02d", year, month, day)
    }

    override fun asComponent() = Component.text(toString())

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null || other.javaClass != this.javaClass) return false
        val that = other as GameDate
        return this.year == that.year && this.month == that.month && this.day == that.day
    }

    override fun hashCode(): Int {
        return Objects.hash(year, month, day)
    }

    companion object {
        private val daysOfMonth = mapOf(
            1 to 31, // January
            2 to 28, // February (no leap year)
            3 to 31, // March
            4 to 30, // April
            5 to 31, // May
            6 to 30, // June
            7 to 31, // July
            8 to 31, // August
            9 to 30, // September
            10 to 31, // October
            11 to 30, // November
            12 to 31, // December
        )

        /**
         * @param formattedDate a string in the format YYYY-MM-DD
         * @return the corresponding GameDate
         */
        fun of(formattedDate: String): GameDate {
            val split = formattedDate.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return GameDate(split[0].toInt(), split[1].toInt(), split[2].toInt())
        }
    }
}
