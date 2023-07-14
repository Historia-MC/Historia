package dev.boooiil.historia.events.food;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.handlers.food.PlayerConsume;
import dev.boooiil.historia.util.PlayerStorage;

public class FoodLevelChange implements Listener {
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {

        //TODO: I might not stick with this plan. We will have to check the players health every tick and apply regen if they are full.

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getEntity().getUniqueId(), false);
        PlayerConsume playerConsume = new PlayerConsume(event, historiaPlayer);
        playerConsume.doConsume();

    }
    
}
