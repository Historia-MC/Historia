package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.HistoriaCore

/**
 * Custom game calendar.
 */
object GameDate {
    private const val GAME_DAY_TICKS: Int = 144000
    var dayOffset = 0

    val daysSinceEpoch: Int
        get() = (HistoriaCore.server.worlds[0].gameTime / GAME_DAY_TICKS).toInt() + dayOffset

    fun current(calendar: Calendar = Calendar.Gregorian): CalendarDate {
        return calendar.dateOf(daysSinceEpoch)
    }
}