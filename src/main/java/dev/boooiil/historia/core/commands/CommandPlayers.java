package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

/**
 * List all players in the game with their level and class.
 * 
 */
public class CommandPlayers implements CommandExecutor {

    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        StringBuilder message = new StringBuilder();

        if (!(sender instanceof Player))
            return false;

        for (Map.Entry<UUID, HistoriaPlayer> storedPlayer : PlayerStorage.getPlayerMap().entrySet()) {

            HistoriaPlayer historiaPlayer = storedPlayer.getValue();

            message
                    .append("Player: ")
                    .append(historiaPlayer.getUsername())
                    .append(" Level: ").append(historiaPlayer.getLevel())
                    .append(" Class: ").append(historiaPlayer.getProficiency().getKey().getKey())
                    .append("\n");

        }

        CoreLogger.infoToPlayer(message.toString(), ((Player) sender).getUniqueId());

        return true;

    }

}
