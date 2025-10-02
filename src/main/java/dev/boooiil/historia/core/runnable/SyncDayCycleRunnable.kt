package dev.boooiil.historia.core.runnable

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.configuration.specific.DateConfig.CUSTOM_DAY_LENGTH
import dev.boooiil.historia.core.configuration.specific.DateConfig.DAY_WEIGHT
import dev.boooiil.historia.core.configuration.specific.DateConfig.NIGHT_WEIGHT
import org.bukkit.GameRule
import org.bukkit.scheduler.BukkitRunnable

class SyncDayCycleRunnable : BukkitRunnable() {
    /** Number of ticks in one vanilla day. */
    private val vanillaDayLength: Int = 24000

    override fun run() {
        if (HistoriaCore.server.worlds[0].getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == true) {
            val customTicks = HistoriaCore.server.worlds[0].gameTime % CUSTOM_DAY_LENGTH

            val totalWeight = DAY_WEIGHT + NIGHT_WEIGHT

            val customDayTicks = CUSTOM_DAY_LENGTH * (DAY_WEIGHT / totalWeight)
            val customNightTicks = CUSTOM_DAY_LENGTH * (NIGHT_WEIGHT / totalWeight)

            val dayTicks = vanillaDayLength / 2
            val nightTicks = vanillaDayLength / 2

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