package dev.boooiil.historia.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;

public class CommandDump implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            sender.sendMessage("Player not found or not online.");
            return true;
        }

        HistoriaPlayer hp = PlayerStorage.getPlayer(player.getUniqueId());

        sender.sendMessage(player.getName() + ": " + hp.toJSON());
        CoreLogger.infoToConsole(hp.toJSON());

        return true;
    }
}
