package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.HistoriaCore
import org.bukkit.GameRule

/** Keeps track of the server time. */
object ServerCalendar {
    /** The number of ticks in one game day. */
    const val VANILLA_DAY_TICKS: Int = 24000
    const val CUSTOM_DAY_TICKS: Int = 144000

    /** Offset applied when calculating the current day. */
    var dayOffset = 0

    /** Number of days since server start. */
    val daysSinceStart: Int
        get() = (HistoriaCore.server.worlds[0].gameTime / CUSTOM_DAY_TICKS).toInt() + dayOffset

    /** Returns the current date in the specified calendar system */
    fun current(calendar: Calendar = Calendar.Gregorian): CalendarDate {
        return calendar.dateOf(daysSinceStart)
    }

    val customDayTimeRunnable = Runnable {
        if (HistoriaCore.server.worlds[0].getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == true) {
            val dayTicks = HistoriaCore.server.worlds[0].gameTime % CUSTOM_DAY_TICKS
            HistoriaCore.server.worlds[0].time = dayTicks * VANILLA_DAY_TICKS / CUSTOM_DAY_TICKS
        }
    }
}