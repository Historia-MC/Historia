package dev.boooiil.historia.handlers.food;

import org.bukkit.event.entity.FoodLevelChangeEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.Logging;

public class PlayerConsume {
    
    private HistoriaPlayer historiaPlayer;
    private FoodLevelChangeEvent event;

    public PlayerConsume(FoodLevelChangeEvent event, HistoriaPlayer historiaPlayer) {

        this.event = event;
        this.historiaPlayer = historiaPlayer;

    }

    public void doConsume() {

        float maxFoodLevel = historiaPlayer.getProficiency().getStats().getBaseFood();

        if (event.getFoodLevel() > maxFoodLevel) {

            event.setFoodLevel((int) maxFoodLevel);
            Logging.infoToPlayer("You feel full!", historiaPlayer.getUUID());

        }

    }

}
