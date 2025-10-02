package dev.boooiil.historia.core.date

import dev.boooiil.historia.core.HistoriaCore
import org.bukkit.GameRule

/** Keeps track of the server time. */
object ServerCalendar {
    /** Number of ticks in one vanilla day. */
    const val VANILLA_DAY_TICKS: Int = 24000

    /** Number of ticks in one custom day. */
    const val CUSTOM_DAY_TICKS: Int = 80 //144000

    /** Weight of the day in day/night ratio. */
    const val dayWeight: Double = 3.0

    /** Weight of the night in day/night ratio. */
    const val nightWeight: Double = 1.0

    /** Day offset applied to the start date when calculating dates. */
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
            val customTicks = HistoriaCore.server.worlds[0].gameTime % CUSTOM_DAY_TICKS

            val totalWeight = dayWeight + nightWeight

            val customDayTicks = CUSTOM_DAY_TICKS * (dayWeight / totalWeight)
            val customNightTicks = CUSTOM_DAY_TICKS * (nightWeight / totalWeight)

            val dayTicks = VANILLA_DAY_TICKS / 2
            val nightTicks = VANILLA_DAY_TICKS / 2

            val vanillaTicks = if (customTicks < customDayTicks) {
                customTicks * dayTicks / customDayTicks
            } else {
                val nightProgress = customTicks - customDayTicks
                dayTicks + (nightProgress * nightTicks / customNightTicks)
            }

            HistoriaCore.server.worlds[0].time = vanillaTicks.toLong()
        }
    }
}