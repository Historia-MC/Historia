package dev.boooiil.historia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.Config;
import dev.boooiil.historia.mysql.UserData;

public class Class implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && args.length != 0) {

            Player player = (Player) sender;
            UserData user = new UserData(player);

            if (args[0].equals("set") && args.length > 1) {

                if (Config.validClass(args[1])) {
                                
                    user.setClass(args[1]);

                    sender.sendMessage("§7[§9Historia§7] You are now a " + args[1] + ".");

                } else sender.sendMessage("That class was not in the list of classes.");

            } else sender.sendMessage("You must provide a class.");

            if (args[0].equals("remove")) {

                sender.sendMessage("§7[§9Historia§7] You are no longer a " + user.getClassName() + ".");

                user.setClass("None");

            }

            if (args[0].equals("stats")) {

                sender.sendMessage("§7-----------[§9" + sender.getName() + "§7]-----------");
                sender.sendMessage("§7Class: §9" + user.getClassName());
                sender.sendMessage("§7Level: §9" + user.getLevel());
                sender.sendMessage("§7Experience: §9" + user.getExperience() + "§7/§9" + user.getMaxExperience());
                sender.sendMessage("§7Health: §9" + user.getHealth());
                sender.sendMessage("§7Speed: §9" + user.getSpeed());
                sender.sendMessage("§7Evasion: §9" + user.getEvasion());
                sender.sendMessage("§7Weight: §9" + user.getWeightCapacity());
                sender.sendMessage("----------------------------------");

            }

        }

        return false;

    }

}
