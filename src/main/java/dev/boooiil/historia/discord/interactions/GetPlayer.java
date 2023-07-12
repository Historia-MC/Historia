package dev.boooiil.historia.discord.interactions;

import java.time.Instant;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.dependents.towny.TownyHandler;
import dev.boooiil.historia.util.DateUtil;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.util.Color;

/**
 * It's a command that allows you to view a player's information
 */
public class GetPlayer {
    
    /**
     * It takes a ChatInputInteractionEvent, gets the username from the event, gets the HistoriaPlayer
     * object from the username, and then sends an embed with the player's information
     * 
     * @param event The event that triggered the command.
     * @return A Discord4J InteractionApplicationCommandCallbackReplyMono object.
     */
    public InteractionApplicationCommandCallbackReplyMono run(ChatInputInteractionEvent event) {

        String username = event.getOption("username")
        .flatMap(ApplicationCommandInteractionOption::getValue)
        .map(ApplicationCommandInteractionOptionValue::asString)
        .get();

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(username, true);
        
        // It's checking if the HistoriaPlayer object is valid.
        if (historiaPlayer.isValid()) {
            
            Logging.infoToConsole(historiaPlayer.toString());

            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title(historiaPlayer.getUsername().replaceAll("_", "\\_") + " (" + (historiaPlayer.isOnline() ? "Online" : "Offline" ) + ")" )
                .addField("Town", !TownyHandler.hasTown(historiaPlayer.getUUID()) ? "Wilderness" : historiaPlayer.getTown().getName(), true)
                .addField("Town Rank", historiaPlayer.getResident().getTownRanks().toString(), true)
                .addField("Nation Rank", historiaPlayer.getResident().getNationRanks().toString(), true)
                .addField("Class", historiaPlayer.getClassName(), true)
                .addField("Level", String.valueOf(historiaPlayer.getLevel()), true)
                .addField("Experience", historiaPlayer.getTotalExperience() + "/" + historiaPlayer.getMaxExperience(), true)
                .addField("Playtime", DateUtil.convertMillisecondsIntoStringTime(historiaPlayer.getPlaytime(), false), true)
                .thumbnail("https://minotar.net/avatar/" + historiaPlayer.getUUID())
                .timestamp(Instant.ofEpochMilli(historiaPlayer.getLastLogin() > historiaPlayer.getLastLogout() ? historiaPlayer.getLastLogin() : historiaPlayer.getLastLogout()))
                .footer("Last Seen", "")
                .build();
            
            return event.reply("").withEmbeds(embed);
            

        }
        else return event.reply("That player could not be found."); 

    }

    /**
     * It returns an ApplicationCommandRequest object that contains a command named "player" with a
     * description of "View a player's information." and an option named "username" with a description
     * of "The username of the user you would like to view." that is required and of type string
     * 
     * @return An ApplicationCommandRequest object.
     */
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
