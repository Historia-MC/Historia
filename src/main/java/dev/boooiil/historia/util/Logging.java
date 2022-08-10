package dev.boooiil.historia.util;

import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.lang.IllegalClassException;

import dev.boooiil.historia.Main;

public class Logging {

    private static final String messagePrefix = "§7[§9Historia§7] ";
    private static final String announcePrefix = "§7[§9Announcement§7] ";
    private static final Logger logger = Main.plugin().getLogger();
    
    private Logging() { throw new IllegalClassException( "Static utility class."); }

    /**
     * Send an info message to console.
     * @param message The message to be sent.
     */
    public static void infoToConsole(String message) {

        logger.info(message);

    }

    /**
     * Send an info message to server.
     * @param message The message to be sent.
     */
    public static void infoToServer(String message) {

        Main.server().broadcastMessage(announcePrefix + "§7" + message);

    }

    /**
     * Send an info message to a player.
     * @param message The message to be sent.
     * @param uuid The UUID of the player.
     */
    public static void infoToPlayer(String message, UUID uuid) {

        Main.server().getPlayer(uuid).sendMessage(messagePrefix + "§7" + message);

    }

    /**
     * Send a warning message to the console.
     * @param message The message to be sent.
     */
    public static void warnToConsole(String message) {

        logger.warning(message);

    }

    /**
     * Send a warning message to server.
     * @param message The message to be sent.
     */
    public static void warnToServer(String message) {

        Main.server().broadcastMessage(announcePrefix + "§6" + message);

    }

    /**
     * Send a warning message to the player.
     * @param message The message to be sent.
     * @param uuid The UUID of the player.
     */
    public static void warnToPlayer(String message, UUID uuid) {

        Main.server().getPlayer(uuid).sendMessage(messagePrefix + "§6" + message);

    }

    /**
     * Send an error message to the console.
     * @param message The message to be sent.
     */
    public static void errorToConsole(String message) {

        logger.severe(message);

    }

    /**
     * Send a warning message to server.
     * @param message The message to be sent.
     */
    public static void errorToServer(String message) {

        Main.server().broadcastMessage(announcePrefix + "§c" + message);

    }

    /**
     * Send an error message to a player.
     * @param message The message to be sent.
     * @param uuid The UUID of the player.
     */
    public static void errorToPlayer(String message, UUID uuid) {

        Main.server().getPlayer(uuid).sendMessage(messagePrefix + "§c" + message);

    }

    public static void debugToConsole(String message) {

        //IF DEBUG ENABLED

    }

}
