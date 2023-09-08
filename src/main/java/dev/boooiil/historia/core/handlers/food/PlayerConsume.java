package dev.boooiil.historia.core.handlers.food;

import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerConsume {
    
    private final HistoriaPlayer historiaPlayer;
    private final FoodLevelChangeEvent event;

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
