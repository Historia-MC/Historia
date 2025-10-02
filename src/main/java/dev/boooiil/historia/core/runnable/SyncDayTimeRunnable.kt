package dev.boooiil.historia.core.runnable

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.date.ServerCalendar
import dev.boooiil.historia.core.date.ServerCalendar.customDayTicks
import org.bukkit.GameRule
import org.bukkit.scheduler.BukkitRunnable

class SyncDayTimeRunnable : BukkitRunnable() {
    /** Number of ticks in one vanilla day. */
    private val vanillaDayTicks: Int = 24000

    /** Weight of the day in day/night ratio. */
    private val dayWeight: Double = 2.0

    /** Weight of the night in day/night ratio. */
    private val nightWeight: Double = 1.0

    override fun run() {
        if (HistoriaCore.server.worlds[0].getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == true) {
            val customTicks = HistoriaCore.server.worlds[0].gameTime % customDayTicks

            val totalWeight = dayWeight + nightWeight

            val customDayTicks = customDayTicks * (dayWeight / totalWeight)
            val customNightTicks = ServerCalendar.customDayTicks * (nightWeight / totalWeight)

            val dayTicks = vanillaDayTicks / 2
            val nightTicks = vanillaDayTicks / 2

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