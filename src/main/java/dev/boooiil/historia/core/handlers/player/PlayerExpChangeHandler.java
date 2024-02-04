package dev.boooiil.historia.core.handlers.player;

import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerExpChangeHandler {

    private PlayerExpChangeEvent event;

    public PlayerExpChangeHandler(PlayerExpChangeEvent event) {
        this.event = event;
    }

    public void doNullAmount() {
        event.setAmount(0);
    }

}
