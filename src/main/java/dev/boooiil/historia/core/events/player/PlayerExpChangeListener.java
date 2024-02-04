package dev.boooiil.historia.core.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import dev.boooiil.historia.core.handlers.player.PlayerExpChangeHandler;

public class PlayerExpChangeListener implements Listener {

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        PlayerExpChangeHandler playerExpChangeHandler = new PlayerExpChangeHandler(event);

        playerExpChangeHandler.doNullAmount();
    }

}
