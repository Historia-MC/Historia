package dev.boooiil.historia.discord.handlers;

import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.discord.interactions.GetPlayer;
import dev.boooiil.historia.util.Logging;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Guild;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Flux;

public class InteractionHandler {

    private static final GatewayDiscordClient CLIENT = HistoriaDiscord.getClient();

    public static void init() {

        Logging.infoToConsole("Initializing commands in guilds...");
        initializeAllGuilds();
        Logging.infoToConsole("Initialized commands in guilds.");

        CLIENT.on(ChatInputInteractionEvent.class, event -> {

            if (event.getCommandName().equals("player")) {

                return GetPlayer.run(event);

            }

            else
                return event.reply("That could not be found?").withEphemeral(true);

        }).subscribe();

    }

    public static ApplicationCommandRequest[] getInteractions() {

        ApplicationCommandRequest interactions[] = new ApplicationCommandRequest[1];

        interactions[0] = GetPlayer.getApplicationCommandRequest();

        return interactions;

    }

    public static void initializeAllGuilds() {

        Flux<Guild> fluxGuilds = CLIENT.getGuilds();

        fluxGuilds.collectList().flatMap(guilds -> {

            for (Guild guild : guilds) {

                for (ApplicationCommandRequest interaction : getInteractions()) {

                    Logging.infoToConsole("Creating interaction (" + interaction.name() + ") in Guild: " + guild.getName() + "(" + guild.getId() + ")");

                    CLIENT.getRestClient().getApplicationService()
                            .createGuildApplicationCommand(CLIENT.getRestClient().getApplicationId().block(), guild.getId().asLong(),
                                    interaction)
                            .subscribe();

                }

            }

            return null;

        });

    }

    public static void singleGuildInteraction(long guildID) {

        for (ApplicationCommandRequest interaction : getInteractions()) {

            Logging.infoToConsole("Creating single interaction (" + interaction.name() + ") in Guild (" + guildID + ")");

            CLIENT.getRestClient().getApplicationService()
                            .createGuildApplicationCommand(CLIENT.getRestClient().getApplicationId().block(), guildID,
                                    interaction)
                            .subscribe();

        }

    }

}
