package dev.boooiil.historia.discord;

import dev.boooiil.historia.discord.handlers.InteractionHandler;
import dev.boooiil.historia.util.Logging;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;

/**
 * It's a class that handles the discord client
 */
public class HistoriaDiscord {

    private static final DiscordClient CLIENT = DiscordClient
            .create("MzYxOTk2ODY4MjY1NDQzMzQw.G4SKSu.51wSxeVIGhvOkiThmawJR0xZ2VxH0M0sSs6vMY");
    private static final GatewayDiscordClient GATEWAY_CLIENT = CLIENT.login().block();
    
    /**
     * It returns the GatewayDiscordClient object
     * 
     * @return The GatewayDiscordClient object.
     */
    public static GatewayDiscordClient getClient() {

        return GATEWAY_CLIENT;

    }

    /**
     * It sets the activity to "Online: 0/64" and then calls the InteractionHandler.init() function
     */
    public static void init() {

        setActivity("Online: 0/64");

        InteractionHandler.init();

    }

    /**
     * It sets the activity of the bot to whatever you want
     * 
     * @param activity The activity you want to set.
     */
    public static void setActivity(String activity) {

        getClient().updatePresence(ClientPresence.online(ClientActivity.playing(activity))).subscribe();

    }

    /**
     * It logs out of the discord gateway
     */
    public static void destroy() {

        Logging.infoToConsole("Logging out of discord...");
        GATEWAY_CLIENT.logout().block();
        Logging.infoToConsole("Bot has been logged out.");

    }
}
