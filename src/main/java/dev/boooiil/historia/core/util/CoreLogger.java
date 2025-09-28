package dev.boooiil.historia.core.util;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.configuration.specific.GeneralConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Static utility class for logging messages in Historia-Core.
 */
@NullMarked
public class CoreLogger {

    /**
     * Prefix for all logging messages that do not use the bukkit logger.
     */
    private static final String messagePrefix = "§7[§9Historia§7] ";
    /**
     * Announcement prefix.
     */
    private static final String announcePrefix = "§7[§9Announcement§7] ";
    // private static final String debugPrefix = "§7[§cDebug§7] ";

    /**
     * Bukkit logger to send messages to the console.
     */
    private static final Logger logger = Bukkit.getServer() != null ? Bukkit.getLogger() : Logger.getLogger("Historia");

    /**
     * Send an info message to the console.
     *
     * @param messages The message to be sent.
     */
    public static void infoToConsole(String... messages) {

        StringBuilder built = new StringBuilder();

        for (String message : messages) {

            built.append(message).append(" ");

        }

        logger.info(built.toString());

    }

    /**
     * Send an info message to server.
     *
     * @param message The message to be sent.
     */
    public static void infoToServer(String message) {

        HistoriaCore.Companion.getServer().broadcast(Component.text(announcePrefix + "§7" + message));

    }

    /**
     * Send an info message to a player.
     *
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void infoToPlayer(String message, UUID uuid) {

        Player player = HistoriaCore.Companion.getServer().getPlayer(uuid);

        if (player != null && player.isOnline())
            player.sendMessage(messagePrefix + "§7" + message);

    }

    /**
     * Send an info message to a player.
     *
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void infoToPlayerNoPrefix(String message, UUID uuid) {

        Player player = HistoriaCore.Companion.getServer().getPlayer(uuid);

        if (player != null && player.isOnline())
            player.sendMessage("§7" + message);

    }

    /**
     * Send a warning message to the console.
     *
     * @param messages The message to be sent.
     */
    public static void warnToConsole(String... messages) {

        StringBuilder built = new StringBuilder();

        for (String message : messages) {

            built.append(message).append(" ");

        }

        if (logger != null) {
            logger.warning(built.toString());
        } else {
            System.out.println(built.toString());
        }

    }

    /**
     * Send a warning message to server.
     *
     * @param message The message to be sent.
     */
    public static void warnToServer(String message) {

        HistoriaCore.Companion.getServer().broadcast(Component.text(announcePrefix + "§6" + message));

    }

    /**
     * Send a warning message to the player.
     *
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void warnToPlayer(String message, UUID uuid) {

        Player player = HistoriaCore.Companion.getServer().getPlayer(uuid);

        if (player.isOnline())
            player.sendMessage(messagePrefix + "§6" + message);

    }

    /**
     * Send an error message to the console.
     *
     * @param messages The message to be sent.
     */
    public static void errorToConsole(String... messages) {

        StringBuilder built = new StringBuilder();

        for (String message : messages) {

            built.append(message).append(" ");

        }

        if (logger != null)
            logger.severe(built.toString());
        else
            System.err.println(built.toString());
    }

    /**
     * Send a warning message to server.
     *
     * @param message The message to be sent.
     */
    public static void errorToServer(String message) {

        HistoriaCore.Companion.getServer().broadcast(Component.text(announcePrefix + "§c" + message));

    }

    /**
     * Send an error message to a player.
     *
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void errorToPlayer(String message, UUID uuid) {

        Player player = HistoriaCore.Companion.getServer().getPlayer(uuid);

        if (player.isOnline())
            player.sendMessage(messagePrefix + "§c" + message);

    }

    /**
     * Send a warning message to server.
     *
     * @param messages The messages to be sent.
     */
    public static void debugToConsole(String... messages) {

        if (HistoriaCore.isTesting || GeneralConfig.debug) {

            StringBuilder built = new StringBuilder();

            for (String message : messages) {

                built.append(message).append(" ");

            }

            warnToConsole("[DEBUG] " + built);

        }

    }

    public static void verboseToConsole(String... messages) {

        if (
                HistoriaCore.isLoaded &&
                        GeneralConfig.verbose) {

            StringBuilder built = new StringBuilder();

            for (String message : messages) {

                built.append(message).append(" ");

            }

            infoToConsole("[VERBOSE] " + built);

        }

    }

    public static void traceToConsole(String... messages) {

        String result = "";

        for (int i = 2; i < Thread.currentThread().getStackTrace().length; i++) {

            StackTraceElement e = Thread.currentThread().getStackTrace()[i];

            if (!e.getClassName().startsWith("dev.boooiil.historia")) {
                continue;
            }

            String className = e.getClassName();
            String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
            String methodName = e.getMethodName();
            int lineNumber = e.getLineNumber();

            result = simpleClassName + ":" + lineNumber + " " + methodName + " -> " + result;

            if (methodName.equals("<clinit>")) {
                break;
            }

        }

        if (
                HistoriaCore.isLoaded &&
                        GeneralConfig.trace) {

            StringBuilder built = new StringBuilder();

            for (String message : messages) {

                built.append(message).append(" ");

            }

            infoToConsole("[TRACE] " + result + built);

        }

    }
}
