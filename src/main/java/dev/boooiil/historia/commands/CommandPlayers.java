package dev.boooiil.historia.commands;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

public class CommandPlayers implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String message = "";

        for (Map.Entry<UUID, HistoriaPlayer> storedPlayer : PlayerStorage.players.entrySet()) {

            HistoriaPlayer historiaPlayer = storedPlayer.getValue();

            message += "Player: " + historiaPlayer.getUsername() + " Level: " + historiaPlayer.level + " Class: " + historiaPlayer.className + "\n";

        }

        for (Player player : Bukkit.getOnlinePlayers()) {

            Logging.infoToPlayer(message, player.getUniqueId());

        }

        return false;

    }

}
