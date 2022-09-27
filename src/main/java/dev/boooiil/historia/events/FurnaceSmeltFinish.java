package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

import dev.boooiil.historia.configuration.IngotConfig;
import dev.boooiil.historia.classes.Ingot;


public class FurnaceSmeltFinish implements Listener {
    
    @EventHandler
    public void onPlayerKill(FurnaceSmeltEvent event) {

        //Not being fired, ??

        String localizedName = event.getSource().getItemMeta().getLocalizedName();

        if (IngotConfig.isValidIngot(localizedName)) {

            Ingot ingot = IngotConfig.getIngot(localizedName);
            
            if (ingot.hasProgression()) {

                event.setResult(ingot.getProgression().getItemStack());

            }

            
        }

    }

}
