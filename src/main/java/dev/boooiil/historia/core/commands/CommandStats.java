package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Get a player's stats such as level, experience, and proficiency.
 * 
 */
public class CommandStats implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String alias, String[] arguments) {

                arguments[0] = arguments[0].toLowerCase();

                if (arguments.length == 2) {

                        if (arguments[0].equals("player")) {

                                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(arguments[1]);
                                Player bukkitPlayer = Bukkit.getPlayer(historiaPlayer.getUUID());
                                String message = "";

                                if (bukkitPlayer.isOnline()) {

                                        message += "----- (" + bukkitPlayer.name().examinableName() + ") -----\n";
                                        message += "Proficiency: " + historiaPlayer.getProficiency().getKey().getKey()
                                                        + "\n";
                                        message += "Level: " + historiaPlayer.getLevel() + "\n";
                                        message += "Health: " + bukkitPlayer.getHealth() + "/"
                                                        + historiaPlayer.getBaseHealth() + "\n";
                                        message += "Hunger: " + bukkitPlayer.getFoodLevel() + "/"
                                        // this does not exist yet
                                        // historiaPlayer.getProficiency().getStats().getBodyStats().getLevel(BodyStatsType.FOOD)
                                                        + "0\n";
                                        message += "Experience: " + historiaPlayer.getCurrentExperience() + "/"
                                                        + historiaPlayer.getMaxExperience() + "\n";
                                        message += "Temperature: " + historiaPlayer.getCurrentTemperature() + "\n";
                                        message += "Weapon Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getWeaponStats().getUsableWeaponWeights()
                                                        + "\n";
                                        message += "Armor Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getArmorStats().getUsableArmorWeights()
                                                        + "\n";
                                        message += "Experience Sources: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getExperienceSources()
                                                        + "\n";

                                }

                                else {

                                        message += "----- (" + bukkitPlayer.getName() + ") -----\n";
                                        message += "Proficiency: " + historiaPlayer.getProficiency().getKey().getKey()
                                                        + "\n";
                                        message += "Level: " + historiaPlayer.getLevel() + "\n";
                                        message += "Health: ??/" + historiaPlayer.getBaseHealth() + "\n";
                                        message += "Experience: " + historiaPlayer.getCurrentExperience() + "/"
                                                        + historiaPlayer.getMaxExperience() + "\n";
                                        message += "Weapon Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getWeaponStats().getUsableWeaponWeights()
                                                        + "\n";
                                        message += "Armor Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getArmorStats().getUsableArmorWeights()
                                                        + "\n";

                                }

                                CoreLogger.infoToPlayerNoPrefix(message, ((Player) sender).getUniqueId());

                                return true;
                        } else
                                return false;
                } else
                        return false;
        }
}
