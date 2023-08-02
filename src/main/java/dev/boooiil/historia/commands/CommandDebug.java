package dev.boooiil.historia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;
import dev.boooiil.historia.util.Logging;

/**
 * It's a command that allows a player to view the debug information of another player
 */
public class CommandDebug implements CommandExecutor {
    
    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        args[0] = args[0].toLowerCase();

        if (args.length > 0 && args.length == 2) {

            if (args[0].equals("player")) {

                HistoriaPlayer player = PlayerStorage.getPlayer(args[1], true);

                String message = "----- (" + player.getUsername() + ") -----\n";
                
                message += "UUID: " + player.getUUID() + "\n";
                message += "Online: " + player.isOnline() + "\n";
                message += "Class: " + player.getProficiency().getName() + "\n";
                message += "Level: " + player.getLevel() + "\n";
                message += "Base Health: " + player.getBaseHealth() + "\n";
                message += "Mod Health: " + player.getModifiedHealth() + "\n";
                message += "Experience: " + player.getCurrentExperience() + "\n";
                message += "Temperature: " + player.getCurrentTemperature() + "\n";
                message += "Armor Temp: " + player.getArmorAdjustment() + "\n";
                message += "Env Temp: " + player.getEnvironmentAdjustment() + "\n";

                Logging.infoToPlayer(message, ((Player) sender).getUniqueId());
                Logging.debugToConsole(player.toString());

                return true;

            }
            else return false;

        } else return false;

    }

}
