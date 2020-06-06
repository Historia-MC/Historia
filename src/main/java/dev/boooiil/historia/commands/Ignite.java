package dev.boooiil.historia.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ignite implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ignite")) {
           if (sender instanceof Player) {

                Player player = (Player) sender;

                if (player.hasPermission("historia.ignite")) {
                    
                    try {

                        Player ignitePlayer = Bukkit.getPlayer(args[0]);

                        try {

                            Integer integer = Integer.parseInt(args[1]); 
                            ignitePlayer(ignitePlayer, integer); 
                            player.sendMessage(args[0] + " has been ignited."); 
                            return true; 

                        } catch (Exception e) { player.sendMessage("Please provide a number."); return false; }
                    } catch (Exception e) { player.sendMessage("There was an issue finding that player."); return false; }
                }
            } else {

                    try {

                        Player ignitePlayer = Bukkit.getPlayer(args[0]);
                        
                        try {

                            Integer integer = Integer.parseInt(args[1]); 
                            ignitePlayer(ignitePlayer, integer); 
                            System.out.println(args[0] + " has been ignited."); 
                            return true; 

                        } catch (Exception e) { System.out.println("Please provide a number."); return false; }
                    } catch (Exception e) { System.out.println("There was an issue finding that player."); return false; }

            }
        } return false;
    }

    public void ignitePlayer(Player ignited, Integer length) {
        ignited.setFireTicks(length);
    }
}