package dev.boooiil.historia.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;

public class CommandProficiency implements CommandExecutor {

    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            return false;
        if (args.length == 0)
            return false;

        if (args[0].equals("set")) {

            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(Bukkit.getPlayer(args[1]).getUniqueId(), true);

                String providedProficiency = args[2];

                historiaPlayer.changeProficiency(providedProficiency);
                historiaPlayer.saveCharacter();

                sender.sendMessage("Changed proficiency to " + providedProficiency + ".");

                return true;

            }

            else {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(((Player) sender).getUniqueId(), true);

                String providedProficiency = args[1];

                historiaPlayer.changeProficiency(providedProficiency);
                historiaPlayer.saveCharacter();

                sender.sendMessage("Changed proficiency to " + providedProficiency + ".");

                return true;

            }

        } else {

            sender.sendMessage("Syntax: /proficiency set <proficiency>.");
            sender.sendMessage("Syntax: /proficiency set <player> <proficiency>.");
            return false;
        }

    }
}
