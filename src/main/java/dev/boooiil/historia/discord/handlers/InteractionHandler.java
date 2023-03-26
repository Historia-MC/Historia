package dev.boooiil.historia.discord.handlers;

import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.discord.interactions.GetPlayer;
import dev.boooiil.historia.util.Logging;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Guild;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Flux;

/**
 * It's a class that handles all the interactions that the bot can do
 */
public class InteractionHandler {

    private static final GatewayDiscordClient CLIENT = HistoriaDiscord.getClient();

    /**
     * It initializes all the commands in the guilds
     */
    public static void init() {

        Logging.infoToConsole("Initializing commands in guilds...");
        initializeAllGuilds();
        Logging.infoToConsole("Initialized commands in guilds.");

        CLIENT.on(ChatInputInteractionEvent.class, event -> {

            if (event.getCommandName().equals("player")) {

                GetPlayer getPlayer = new GetPlayer();

                return getPlayer.run(event);

            }

            else
                return event.reply("That could not be found?").withEphemeral(true);

        }).subscribe();

        // https://docs.discord4j.com/error-handling

    }

    /**
     * This function returns an array of ApplicationCommandRequest objects
     * 
     * @return An array of ApplicationCommandRequest objects.
     */
    public static ApplicationCommandRequest[] getInteractions() {

        ApplicationCommandRequest interactions[] = new ApplicationCommandRequest[1];

        interactions[0] = GetPlayer.getApplicationCommandRequest();

        return interactions;

    }

    /**
     * It creates a list of all the guilds the bot is in, and then creates a list of all the
     * interactions the bot has, and then creates an interaction for each guild
     */
    public static void initializeAllGuilds() {

        Flux<Guild> fluxGuilds = CLIENT.getGuilds();

        fluxGuilds.collectList().block().forEach(guild -> {

            for (ApplicationCommandRequest interaction : getInteractions()) {

                Logging.infoToConsole("Creating interaction (" + interaction.name() + ") in Guild: " + guild.getName()
                        + "(" + guild.getId() + ")");

                CLIENT.getRestClient().getApplicationService()
                        .createGuildApplicationCommand(CLIENT.getRestClient().getApplicationId().block(),
                                guild.getId().asLong(),
                                interaction)
                        .subscribe();

            }

        });

    }

    /**
     * It creates a single interaction for a single guild
     * 
     * @param guildID The ID of the guild you want to add the interaction to.
     */
    public static void singleGuildInteraction(long guildID) {

        for (ApplicationCommandRequest interaction : getInteractions()) {

            Logging.infoToConsole(
                    "Creating single interaction (" + interaction.name() + ") in Guild (" + guildID + ")");

            CLIENT.getRestClient().getApplicationService()
                    .createGuildApplicationCommand(CLIENT.getRestClient().getApplicationId().block(), guildID,
                            interaction)
                    .subscribe();

        }

    }

}
