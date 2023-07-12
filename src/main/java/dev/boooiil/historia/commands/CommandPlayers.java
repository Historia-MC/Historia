package dev.boooiil.historia.commands;

import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

/**
 * It's a command that lists all the players in the game
 */
public class CommandPlayers implements CommandExecutor {
    
    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String message = "";

        if (!(sender instanceof Player)) return false;

        for (Map.Entry<UUID, HistoriaPlayer> storedPlayer : PlayerStorage.players.entrySet()) {

            HistoriaPlayer historiaPlayer = storedPlayer.getValue();

            message += "Player: " + historiaPlayer.getUsername() + " Level: " + historiaPlayer.getLevel() + " Class: " + historiaPlayer.getClassName() + "\n";

        }

        Logging.infoToPlayer(message, ((Player) sender).getUniqueId());

        return true;

    }

}
