package dev.boooiil.historia.util;

import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.lang.IllegalClassException;

import dev.boooiil.historia.Main;

public class Logging {

    private static final String prefix = "§7[§9Historia§7] ";
    private static final Logger logger = Main.plugin().getLogger();
    
    private Logging() { throw new IllegalClassException( "Static utility class."); }

    /**
     * Send an info message to console.
     * @param message The message to be sent.
     */
    public static void infoToServer(String message) {

        logger.info(message);

    }

    /**
     * Send an info message to a player.
     * @param message The message to be sent.
     * @param uuid The UUID of the player.
     */
    public static void infoToPlayer(String message, UUID uuid) {

        Main.server().getPlayer(uuid).sendMessage(prefix + "§7" + message);

    }

    /**
     * Send a warning message to the console.
     * @param message The message to be sent.
     */
    public static void warnToServer(String message) {

        logger.warning(message);

    }

    /**
     * Send a warning message to the player.
     * @param message The message to be sent.
     * @param uuid The UUID of the player.
     */
    public static void warnToPlayer(String message, UUID uuid) {

        Main.server().getPlayer(uuid).sendMessage(prefix + "§6" + message);

    }

    /**
     * Send an error message to the console.
     * @param message The message to be sent.
     */
    public static void errorToServer(String message) {

        logger.severe(message);

    }

    /**
     * Send an error message to a player.
     * @param message The message to be sent.
     * @param uuid The UUID of the player.
     */
    public static void errorToPlayer(String message, UUID uuid) {

        Main.server().getPlayer(uuid).sendMessage(prefix + "§c" + message);

    }

    public static void debugToServer(String message) {

        //IF DEBUG ENABLED

    }

}
