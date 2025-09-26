package dev.boooiil.historia.core.expiry.util

import dev.boooiil.historia.expiry.HistoriaExpiry
import dev.boooiil.historia.core.expiry.configuration.ExpiryConfig
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.*
import java.util.logging.Logger

/**
 * It's a class that sends messages to the console, server, or a player
 */
class Logging private constructor() {
    // It's a private constructor that throws an error if someone tries to
    // instantiate the class.
    init {
        throw IllegalAccessError("Static utility class.")
    }

    companion object {
        private const val MESSAGE_PREFIX = "§7[§9Historia§7]"
        private const val ANNOUNCE_PREFIX = "§7[§9Announcement§7]"

        // private static final String debugPrefix = "§7[§cDebug§7] ";
        private val logger: Logger = HistoriaExpiry.plugin.logger

        /**
         * Send an info message to the console.
         *
         * @param messages The message to be sent.
         */
        fun infoToConsole(vararg messages: String?) {
            var built = ""

            for (message in messages) {
                built += "$message "
            }

            logger.info(built)
        }

        /**
         * Send an info message to server.
         *
         * @param message The message to be sent.
         */
        fun infoToServer(message: String?) {
            HistoriaExpiry.server.sendMessage(Component.text("$ANNOUNCE_PREFIX §7$message"))
        }

        /**
         * Send an info message to a player.
         *
         * @param message The message to be sent.
         * @param uuid    The UUID of the player.
         */
        fun infoToPlayer(message: String?, uuid: UUID) {
            val player = Bukkit.getPlayer(uuid)

            if (player != null && player.isOnline) player.sendMessage("$MESSAGE_PREFIX §7$message")
        }

        /**
         * Send an info message to a player.
         *
         * @param message The message to be sent.
         * @param uuid    The UUID of the player.
         */
        fun infoToPlayerNoPrefix(message: String?, uuid: UUID) {
            val player = Bukkit.getPlayer(uuid)

            if (player != null && player.isOnline) player.sendMessage("§7" + message)
        }

        /**
         * Send a warning message to the console.
         *
         * @param messages The message to be sent.
         */
        fun warnToConsole(vararg messages: String?) {
            var built = ""

            for (message in messages) {
                built += "$message "
            }

            logger.warning(built)
        }

        /**
         * Send a warning message to server.
         *
         * @param message The message to be sent.
         */
        fun warnToServer(message: String?) {
            HistoriaExpiry.server.sendMessage(Component.text("$ANNOUNCE_PREFIX §6$message"))
        }

        /**
         * Send a warning message to the player.
         *
         * @param message The message to be sent.
         * @param uuid    The UUID of the player.
         */
        fun warnToPlayer(message: String?, uuid: UUID) {
            val player = Bukkit.getPlayer(uuid)

            if (player != null && player.isOnline) player.sendMessage("$MESSAGE_PREFIX §6$message")
        }

        /**
         * Send an error message to the console.
         *
         * @param messages The message to be sent.
         */
        fun errorToConsole(vararg messages: String?) {
            var built = ""

            for (message in messages) {
                built += "$message "
            }

            logger.severe(built)
        }

        /**
         * Send a warning message to server.
         *
         * @param message The message to be sent.
         */
        fun errorToServer(message: String?) {
            HistoriaExpiry.server.sendMessage(Component.text("$ANNOUNCE_PREFIX §6$message"))
        }

        /**
         * Send an error message to a player.
         *
         * @param message The message to be sent.
         * @param uuid    The UUID of the player.
         */
        fun errorToPlayer(message: String?, uuid: UUID) {
            val player = Bukkit.getPlayer(uuid)

            if (player != null && player.isOnline) player.sendMessage("$MESSAGE_PREFIX §c$message")
        }

        /**
         * Send a warning message to server.
         *
         * @param messages The messages to be sent.
         */
        @JvmStatic
        fun debugToConsole(vararg messages: String?) {
            if (ExpiryConfig.DEBUG) {
                var built = ""

                for (message in messages) {
                    built += "$message "
                }

                warnToConsole("[DEBUG] $built")
            }
        }
    }
}