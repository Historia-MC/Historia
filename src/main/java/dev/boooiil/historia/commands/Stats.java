package dev.boooiil.historia.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.mysql.MySQLHandler;
import dev.boooiil.historia.mysql.UserData;

public class Stats implements CommandExecutor {
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 && isPlayer(sender)) {

            UserData userData = new UserData((Player) sender);

            sender.sendMessage("§7-----------[§9" + sender.getName() + "§7]-----------");
            sender.sendMessage("§7Class: §9" + userData.getClassName());
            sender.sendMessage("§7Level: §9" + userData.getLevel());
            sender.sendMessage("§7Experience: §9" + userData.getExperience() + "§7/§9" + userData.getMaxExperience());
            sender.sendMessage("§7Health: §9" + userData.getHealth());
            sender.sendMessage("§7Speed: §9" + userData.getSpeed());
            sender.sendMessage("§7Evasion: §9" + userData.getEvasion());
            sender.sendMessage("§7Weight: §9" + userData.getWeightCapacity());
            sender.sendMessage("----------------------------------");

            return true;

        } 
        
        else if (args.length != 0) {

            Object object = getPlayer(args[0]);

            if (object == null) {

                sender.sendMessage("§7[§9Historia§7] That user could not be found.");

                return false;

            } else {

                UserData userData = new UserData(object);

                sender.sendMessage("§7-----------[§9" + userData.getPlayerName() + "§7]-----------");
                sender.sendMessage("§7Class: §9" + userData.getClassName());
                sender.sendMessage("§7Level: §9" + userData.getLevel());
                sender.sendMessage("§7Experience: §9" + userData.getExperience() + "§7/§9" + userData.getMaxExperience());
                sender.sendMessage("§7Health: §9" + userData.getHealth());
                sender.sendMessage("§7Speed: §9" + userData.getSpeed());
                sender.sendMessage("§7Evasion: §9" + userData.getEvasion());
                sender.sendMessage("§7Weight: §9" + userData.getWeightCapacity());
                sender.sendMessage("----------------------------------");

                return true;

            }
        }

        return false;
    }

    private boolean isPlayer(CommandSender sender) {

        return sender instanceof Player;

    }

    private Object getPlayer(String playerName) {

        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {

            try {

                UUID uuid = MySQLHandler.getUUID(playerName);

                return Bukkit.getOfflinePlayer(uuid);

            } catch (Exception e) { e.printStackTrace(); return player; }

        } else return player;
    }
}
