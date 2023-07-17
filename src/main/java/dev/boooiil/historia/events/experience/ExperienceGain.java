package dev.boooiil.historia.events.experience;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class ExperienceGain implements Listener {

    @EventHandler
    public void onExperienceGain(PlayerExpChangeEvent event) {
        event.setAmount(0);
    }

}
