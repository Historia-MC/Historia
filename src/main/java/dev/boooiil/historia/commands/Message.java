package dev.boooiil.historia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message implements CommandExecutor {

    //We might be able to create our own postal management plugin.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("essentials.msg")) {

                sender.sendMessage("§7[§9Historia§7] Please use /letter <message> and /post <user>!");
                return true;
            }
        }
        return false;
    }
}