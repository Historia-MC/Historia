package dev.boooiil.historia.handlers.connection;

import org.bukkit.entity.Player;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.PlayerStorage;

public class InitialStatLoader {

    private HistoriaPlayer historiaPlayer;
    private Player player;

    public InitialStatLoader(Player player) {

        this.historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), true);
        this.player = player;

    }

    public void apply() {

        //TODO: Need to make sure that the player is not losing health or food each time they join if base health > 20
        player.setHealth(historiaPlayer.getProficiency().getStats().getBaseHealth() - (20 - player.getHealth()));
        player.setTotalExperience(historiaPlayer.getLevel());
        player.setWalkSpeed(historiaPlayer.getProficiency().getStats().getBaseSpeed());
        player.setFoodLevel(historiaPlayer.getProficiency().getStats().getBaseFood() - (20 - player.getFoodLevel()));

    }
    
}
