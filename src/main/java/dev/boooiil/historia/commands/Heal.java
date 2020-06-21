package dev.boooiil.historia.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

    //Unused, just here for examples.
    //Ignore anything listed.
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("heal")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("historia.heal")) {
                    if (args.length == 0) {

                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.sendMessage(ChatColor.GREEN + "You have been healed.");

                        return true;
                    } else {
                        try {
                            Player obtainedPlayer = Bukkit.getPlayerExact(args[0]);

                            obtainedPlayer.setHealth(20);
                            obtainedPlayer.setFoodLevel(20);

                            obtainedPlayer.sendMessage(ChatColor.GREEN + "You have been healed.");
                            player.sendMessage(ChatColor.RED + "" + obtainedPlayer.getDisplayName() + " has been healed.");

                            return true;
                        } catch (Exception e) {
                            player.sendMessage("Player could not be found.");
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                    return true;
                }
            } else {
                if (args.length == 0) sender.sendMessage(ChatColor.RED + "You must specify a user to heal.");
                try {
                    Player obtainedPlayer = Bukkit.getPlayerExact(args[0]);

                    obtainedPlayer.setHealth(20);
                    obtainedPlayer.setFoodLevel(20);

                    obtainedPlayer.sendMessage(ChatColor.GREEN + "You have been healed.");
                    sender.sendMessage(obtainedPlayer.getDisplayName() + " has been healed.");

                    return true;
                } catch (Exception e) {
                    sender.sendMessage("Player could not be found.");
                    return true;
                }
            }
        }
        return false;
    }
    
}