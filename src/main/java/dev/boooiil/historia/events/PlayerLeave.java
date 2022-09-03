package dev.boooiil.historia.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.mysql.MySQLHandler;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerLeave implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getPlayer().getUniqueId(), false);

        historiaPlayer.saveCharacter();
        
        MySQLHandler.setLogout(historiaPlayer.getUUID(), historiaPlayer.getLastLogin(), historiaPlayer.getPlaytime());

        PlayerStorage.markOffline(event.getPlayer().getUniqueId());
        
        HistoriaDiscord.setActivity("Online: " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());

    }

}
