package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTemperature implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("temperature") && args.length > 0 && args[0].equalsIgnoreCase("set")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command can only be run by a player.");
                return true;
            }

            if (args.length != 1) {
                player.sendMessage("Usage: /settemperature <temperature>");
                return true;
            }

            try {
                int temperature = Integer.parseInt(args[1]);
                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());
                historiaPlayer.getTemperature().setMin(temperature);
                player.sendMessage("Temperature set to " + temperature + ".");
                CoreLogger.infoToConsole("Temperature set to " + temperature + ".");
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid temperature: " + args[1]);
            }
        }
        return true;
    }
}