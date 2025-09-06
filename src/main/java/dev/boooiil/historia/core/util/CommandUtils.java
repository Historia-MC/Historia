package dev.boooiil.historia.core.util;

import dev.boooiil.historia.core.registry.Registry;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandUtils {

    public static List<String> matchPlayerArg(String input) {
        return Bukkit.getServer().getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(input.toLowerCase()))
                .toList();
    }

    public static List<String> matchRegistryArg(Registry<?> registry, String input) {
        if (registry == null) return List.of();
        return registry.keySet().stream()
                .map(NamespacedKey::getKey)
                .filter(key -> key.startsWith(input.toLowerCase()))
                .toList();
    }
}
