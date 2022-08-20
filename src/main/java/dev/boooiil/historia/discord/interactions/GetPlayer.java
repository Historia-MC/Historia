package dev.boooiil.historia.discord.interactions;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.util.PlayerStorage;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

public class GetPlayer {
    
    public static InteractionApplicationCommandCallbackReplyMono run(ChatInputInteractionEvent event) {

        String username = event.getOption("username")
            .flatMap(ApplicationCommandInteractionOption::getValue)
            .map(ApplicationCommandInteractionOptionValue::asString)
            .orElse("null");

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(username, true);

        if (historiaPlayer != null) return event.reply("Player " + historiaPlayer.username + " is a " + historiaPlayer.className + "!");
        else return event.reply("That player could not be found."); 

    }

    public static ApplicationCommandRequest getApplicationCommandRequest() {

        return ApplicationCommandRequest.builder()
            .name("player")
            .description("View a player's information.")
            .addOption(ApplicationCommandOptionData.builder()
                .name("username")
                .description("The username of the user you would like to view.")
                .type(ApplicationCommandOption.Type.STRING.getValue())
                .required(true)
                .build()
            ).build();

    }

}
