package dev.boooiil.historia.util;

import java.util.UUID;

import org.apache.commons.lang.IllegalClassException;

import dev.boooiil.historia.Main;

public class Logging {

    private static final String prefix = "§7[§9Historia§7] ";
    
    private Logging() { throw new IllegalClassException( "Static utility class."); }

    /**
     * Send an info message to console.
     * @param message The message to be sent.
     */
    public static void infoToServer(String message) {

        Main.logger().info(message);

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

        Main.logger().warning(message);

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

        Main.logger().severe(message);

    }

    /**
     * Send an error message to a player.
     * @param message The message to be sent.
     * @param uuid The UUID of the player.
     */
    public static void errorToPlayer(String message, UUID uuid) {

        Main.server().getPlayer(uuid).sendMessage(prefix + "§c" + message);

    }

}
