package dev.boooiil.historia.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Launch implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("launch")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("historia.launch")) {
                    if (args.length == 0) {
                        player.sendMessage(ChatColor.GOLD + "You have been granted the zoomies.");
                        player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
                        return true;
                    } else {
                        try {
                            player.setVelocity(player.getLocation().getDirection().multiply(Integer.parseInt(args[0])).setY(2));
                            player.sendMessage(ChatColor.GOLD + "You have been granted the zoomies.");
                            return true;
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.GOLD + "Argument must be a number.");
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.GOLD + "You can not use this command in console.");
                return true;
            }
        }
        return false;
    }
    
}