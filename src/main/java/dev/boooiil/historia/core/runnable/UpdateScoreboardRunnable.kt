package dev.boooiil.historia.core.runnable

import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.scoreboard.ScoreboardBuilder
import dev.boooiil.historia.core.util.NumberUtils
import dev.boooiil.historia.core.util.plus
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class UpdateScoreboardRunnable : BukkitRunnable() {

    override fun run() {
        Bukkit.getOnlinePlayers().forEach { player: Player ->
            val historiaPlayer = PlayerStorage.getPlayer(player.uniqueId)

            player.scoreboard = ScoreboardBuilder()
                .header(Component.text("HISTORIA", NamedTextColor.GOLD).decorate(TextDecoration.BOLD))

                .addLine(Component.empty())

                .addLine(Component.text("Proficiency: ", NamedTextColor.AQUA)
                        + historiaPlayer.getProficiency().displayName.color(NamedTextColor.GRAY))

                .addLine(Component.text("Level: ", NamedTextColor.DARK_AQUA)
                        + Component.text(historiaPlayer.level, NamedTextColor.GRAY))

                .addLine(Component.text("Health: ", NamedTextColor.AQUA)
                        + Component.text("${NumberUtils.roundDouble(player.health, 2)}/${historiaPlayer.baseHealth}", NamedTextColor.GRAY))

                .addLine(Component.text("Hunger: ", NamedTextColor.DARK_AQUA)
                        + Component.text("${NumberUtils.roundDouble(player.foodLevel.toDouble(), 2)}/0", NamedTextColor.GRAY))

                .addLine(Component.text("Experience: ", NamedTextColor.AQUA)
                        + Component.text("${historiaPlayer.currentExperience}/${historiaPlayer.maxExperience}", NamedTextColor.GRAY))

                .addLine(Component.text("Temperature: ", NamedTextColor.DARK_AQUA)
                        + Component.text(historiaPlayer.currentTemperature.toString(), NamedTextColor.GRAY))

                .addLine(Component.text("Weight: ", NamedTextColor.AQUA)
                        + Component.text("0", NamedTextColor.GRAY)) // TODO placeholder weight value

                .build()

            // scoreboardAdapter.addLine(4, ChatColor.DARK_AQUA + "Weapon Class: " +
            // ChatColor.GRAY
            // + historiaPlayer.getProficiency().getStats().getWeaponStats()
            // .getUsableWeaponWeights());
            // scoreboardAdapter.addLine(3, ChatColor.AQUA + "Armor Class: " +
            // ChatColor.GRAY
            // + historiaPlayer.getProficiency().getStats().getArmorStats()
            // .getUsableArmorWeights());
        }
    }
}
