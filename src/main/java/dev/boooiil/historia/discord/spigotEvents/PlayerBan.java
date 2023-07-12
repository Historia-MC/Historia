package dev.boooiil.historia.discord.spigotEvents;

import java.time.Instant;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.dependents.towny.TownyHandler;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.util.DateUtil;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;

public class PlayerBan implements Listener {

    

    @EventHandler
    public void onPlayerBan(PlayerQuitEvent event) {

        if (event.getPlayer().isBanned()) {

            HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), true);

            // It's checking if the HistoriaPlayer object is valid.
            if (historiaPlayer.isValid()) {

                Logging.infoToConsole(historiaPlayer.toString());

                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        .title(historiaPlayer.getUsername().replaceAll("_", "\\_") + " ("
                                + (historiaPlayer.isOnline() ? "Online" : "Offline") + ")")
                        .addField("Town", !TownyHandler.hasTown(historiaPlayer.getUUID()) ? "Wilderness" : historiaPlayer.getTown().getName(),
                                true)
                        .addField("Town Rank", historiaPlayer.getResident().getTownRanks().toString(), true)
                        .addField("Nation Rank", historiaPlayer.getResident().getNationRanks().toString(), true)
                        .addField("Class", historiaPlayer.getProficiency().getProficiencyName(), true)
                        .addField("Level", String.valueOf(historiaPlayer.getLevel()), true)
                        .addField("Experience",
                                historiaPlayer.getTotalExperience() + "/" + historiaPlayer.getMaxExperience(), true)
                        .addField("Playtime",
                                DateUtil.convertMillisecondsIntoStringTime(historiaPlayer.getPlaytime(), false), true)
                        .thumbnail("https://minotar.net/avatar/" + historiaPlayer.getUUID())
                        .timestamp(Instant.ofEpochMilli(historiaPlayer.getLastLogin() > historiaPlayer.getLastLogout()
                                ? historiaPlayer.getLastLogin()
                                : historiaPlayer.getLastLogout()))
                        .footer("Last Seen", "")
                        .build();

                HistoriaDiscord.getClient().getChannelById(Snowflake.of("854405602103918593"))
                        .ofType(GuildMessageChannel.class)
                        .flatMap(channel -> channel.createMessage(MessageCreateSpec.builder()
                                .content("Player Banned\n\nReason: " + event.getQuitMessage())
                                .addEmbed(embed)
                                .build()))
                        .subscribe();

            }

        }

    }

}
