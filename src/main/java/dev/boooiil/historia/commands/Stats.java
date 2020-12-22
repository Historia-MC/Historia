package dev.boooiil.historia.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.mysql.MySQL;
import dev.boooiil.historia.mysql.UserData;

public class Stats implements CommandExecutor {
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 && isPlayer(sender)) {

            UserData userData = new UserData((Player) sender);

            sender.sendMessage("-----------[" + sender.getName() + "]-----------");
            sender.sendMessage("Class: " + userData.getClassName());
            sender.sendMessage("Level: " + userData.getLevel());
            sender.sendMessage("Experience: " + userData.getExperience() + "/" + userData.getMaxExperience());
            sender.sendMessage("Health: " + userData.getHealth());
            sender.sendMessage("Speed: " + userData.getSpeed());
            sender.sendMessage("Evasion: " + userData.getEvasion());
            sender.sendMessage("Weight: " + userData.getWeightCapacity());
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

                sender.sendMessage("-----------[" + userData.getDisplayName() + "]-----------");
                sender.sendMessage("Class: " + userData.getClassName());
                sender.sendMessage("Experience: " + userData.getExperience() + "/" + userData.getMaxExperience());
                sender.sendMessage("Health: " + userData.getHealth());
                sender.sendMessage("Speed: " + userData.getSpeed());
                sender.sendMessage("Evasion: " + userData.getEvasion());
                sender.sendMessage("Weight: " + userData.getWeightCapacity());
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

                MySQL sql = new MySQL();

                UUID uuid = sql.getUUID(playerName);

                return Bukkit.getOfflinePlayer(uuid);

            } catch (Exception e) { e.printStackTrace(); return player; }

        } else return player;
    }
}
