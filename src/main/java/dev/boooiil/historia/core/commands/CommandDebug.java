package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * It's a command that allows a player to view the debug information of another player
 */
public class CommandDebug implements CommandExecutor {
    
    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        args[0] = args[0].toLowerCase();

        if (args.length == 2) {

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

                Logging.infoToPlayer(message, ((Player) sender).getUniqueId());
                Logging.debugToConsole(player.toString());

                return true;

            }
            else return false;

        } else return false;

    }

}
