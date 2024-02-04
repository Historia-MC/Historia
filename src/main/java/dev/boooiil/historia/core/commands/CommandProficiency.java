package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandProficiency implements CommandExecutor {

    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            return false;
        if (args.length == 0)
            return false;

        if (args[0].equals("set")) {

            Player player = Bukkit.getPlayer(args[1]);

            if (player != null && player.isOnline()) {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), true);

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
