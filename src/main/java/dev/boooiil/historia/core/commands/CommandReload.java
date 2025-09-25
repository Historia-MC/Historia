package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.database.internal.TemperatureStorage;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("temperatura.reload")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }

        try {
            ConfigurationLoader.init();
            // Reset temperature managers for all online players
            for (Player player : Bukkit.getOnlinePlayers()) {
                TemperatureStorage.removeTemperatureManager(player.getUniqueId());
            }
            sender.sendMessage("Temperature plugin configuration reloaded successfully!");
            CoreLogger.infoToConsole("Configuration reloaded by " + sender.getName());
        } catch (Exception e) {
            sender.sendMessage("Error reloading configuration: " + e.getMessage());
            CoreLogger.warnToConsole("Error reloading configuration: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}