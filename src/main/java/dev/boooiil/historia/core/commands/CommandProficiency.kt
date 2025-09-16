package dev.boooiil.historia.core.commands;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;

import dev.boooiil.historia.core.util.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Set a player's proficiency.
 */
public class CommandProficiency implements TabExecutor {

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

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

                String providedProficiency = args[2];

                NamespacedKey key = HistoriaCore.getNamespacedKey(providedProficiency);

                historiaPlayer.changeProficiency(key);
                historiaPlayer.saveCharacter();

                sender.sendMessage("Changed proficiency to " + providedProficiency + ".");

                return true;

            }

            else {

                HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(((Player) sender).getUniqueId());

                String providedProficiency = args[1];

                NamespacedKey key = HistoriaCore.getNamespacedKey(providedProficiency);

                historiaPlayer.changeProficiency(key);
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return List.of("set");
        if (args.length == 2) return CommandUtils.matchPlayerArg(args[1]);
        if (args.length == 3) return CommandUtils.matchRegistryArg(HistoriaCore.PROFICIENCY_REGISTRY, args[2]);
        return null;
    }
}
