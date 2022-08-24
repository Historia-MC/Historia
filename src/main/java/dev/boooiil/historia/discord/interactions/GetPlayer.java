package dev.boooiil.historia.discord.interactions;

import java.time.Instant;

import dev.boooiil.historia.classes.HistoriaPlayer;
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

public class GetPlayer {
    
    public InteractionApplicationCommandCallbackReplyMono run(ChatInputInteractionEvent event) {

        String username = event.getOption("username")
        .flatMap(ApplicationCommandInteractionOption::getValue)
        .map(ApplicationCommandInteractionOptionValue::asString)
        .get();

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(username, true);
        
        if (historiaPlayer != null && historiaPlayer.resident != null) {
            
            Logging.infoToConsole(historiaPlayer.toString());

            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title(historiaPlayer.username.replaceAll("_", "\\_"))
                .addField("Town", historiaPlayer.town == null ? "Wilderness" : historiaPlayer.town.getName(), true)
                .addField("Town Rank", historiaPlayer.resident.getTownRanks().toString(), true)
                .addField("Nation Rank", historiaPlayer.resident.getNationRanks().toString(), true)
                .addField("Jobs", "TODO", true)
                .addField("Class", historiaPlayer.className, true)
                .addField("Level", String.valueOf(historiaPlayer.level), true)
                .thumbnail("https://minotar.net/avatar/" + historiaPlayer.uuid)
                .timestamp(Instant.ofEpochMilli(historiaPlayer.lastLogin > historiaPlayer.lastLogout ? historiaPlayer.lastLogin : historiaPlayer.lastLogout))
                .footer("Last Seen", "")
                .build();
            
            return event.reply("").withEmbeds(embed);
            

        }
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
