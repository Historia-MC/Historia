package dev.boooiil.historia.discord;

import dev.boooiil.historia.discord.handlers.InteractionHandler;
import dev.boooiil.historia.util.Logging;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;

public class HistoriaDiscord {

    private static final DiscordClient CLIENT = DiscordClient
            .create("MzYxOTk2ODY4MjY1NDQzMzQw.GD9rLX.JL2GVRyDV8-BXc_dUxSW6QZpbR7jfq5gwAeU9I");
    private static final GatewayDiscordClient GATEWAY_CLIENT = CLIENT.login().block();
    
    public static GatewayDiscordClient getClient() {

        return GATEWAY_CLIENT;

    }

    public static void init() {

        setActivity("Online: 0/64");

        InteractionHandler.init();

    }

    public static void setActivity(String activity) {

        getClient().updatePresence(ClientPresence.online(ClientActivity.playing(activity))).subscribe();

    }

    public static void destroy() {

        Logging.infoToConsole("Logging out of discord...");
        GATEWAY_CLIENT.logout().block();
        Logging.infoToConsole("Bot has been logged out.");

    }
}
