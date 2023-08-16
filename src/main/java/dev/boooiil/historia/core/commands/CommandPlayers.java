package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

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

            message += "Player: " + historiaPlayer.getUsername() + " Level: " + historiaPlayer.getLevel() + " Class: " + historiaPlayer.getProficiency().getName() + "\n";

        }

        Logging.infoToPlayer(message, ((Player) sender).getUniqueId());

        return true;

    }

}
