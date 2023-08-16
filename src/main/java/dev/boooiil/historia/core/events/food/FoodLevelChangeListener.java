package dev.boooiil.historia.core.events.food;

import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.handlers.food.PlayerConsume;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {

        //TODO: I might not stick with this plan. We will have to check the players health every tick and apply regen if they are full.

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getUniqueId(), false);
        PlayerConsume playerConsume = new PlayerConsume(event, historiaPlayer);
        playerConsume.doConsume();

    }
    
}
