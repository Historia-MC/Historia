package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStats implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String alias, String[] arguments) {

                arguments[0] = arguments[0].toLowerCase();

                if (arguments.length == 2) {

                        if (arguments[0].equals("player")) {

                                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(arguments[1], true);
                                Player bukkitPlayer = Bukkit.getPlayer(historiaPlayer.getUUID());
                                String message = "";

                                if (bukkitPlayer.isOnline()) {

                                        message += "----- (" + bukkitPlayer.name().examinableName() + ") -----\n";
                                        message += "Proficiency: " + historiaPlayer.getProficiency().getName().getKey()
                                                        + "\n";
                                        message += "Level: " + historiaPlayer.getLevel() + "\n";
                                        message += "Health: " + bukkitPlayer.getHealth() + "/"
                                                        + historiaPlayer.getBaseHealth() + "\n";
                                        message += "Hunger: " + bukkitPlayer.getFoodLevel() + "/"
                                                        + historiaPlayer.getProficiency().getStats().getBaseFood()
                                                        + "\n";
                                        message += "Experience: " + historiaPlayer.getCurrentExperience() + "/"
                                                        + historiaPlayer.getMaxExperience() + "\n";
                                        message += "Temperature: " + historiaPlayer.getCurrentTemperature() + "\n";
                                        message += "Weapon Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getUsableWeaponTypes()
                                                        + "\n";
                                        message += "Armor Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getUsableArmorTypes()
                                                        + "\n";
                                        message += "Experience Sources: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getExperienceSources()
                                                        + "\n";

                                }

                                else {

                                        message += "----- (" + bukkitPlayer.getName() + ") -----\n";
                                        message += "Proficiency: " + historiaPlayer.getProficiency().getName().getKey()
                                                        + "\n";
                                        message += "Level: " + historiaPlayer.getLevel() + "\n";
                                        message += "Health: ??/" + historiaPlayer.getBaseHealth() + "\n";
                                        message += "Experience: " + historiaPlayer.getCurrentExperience() + "/"
                                                        + historiaPlayer.getMaxExperience() + "\n";
                                        message += "Weapon Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getUsableWeaponTypes()
                                                        + "\n";
                                        message += "Armor Class: "
                                                        + historiaPlayer.getProficiency().getStats()
                                                                        .getUsableArmorTypes()
                                                        + "\n";

                                }

                                Logging.infoToPlayerNoPrefix(message, ((Player) sender).getUniqueId());

                                return true;
                        } else
                                return false;
                } else
                        return false;
        }
}
