package dev.boooiil.historia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

import dev.boooiil.historia.classes.Ingot;


/**
 * I'm trying to get the FurnaceSmeltEvent to fire when a player smelts an item in a furnace.
 * 
 */
public class FurnaceSmeltFinish implements Listener {
    
    @EventHandler
    // It's a method that is called when the FurnaceSmeltEvent is fired.
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {

        //Not being fired, ??

        String localizedName = event.getSource().getItemMeta().getLocalizedName();

        if (Ingot.ingotConfig.isValidIngot(localizedName)) {

            Ingot ingot = Ingot.ingotConfig.getIngot(localizedName);
            
            if (ingot.hasProgression()) {

                event.setResult(ingot.getProgression().getItemStack());

            }

            
        }

    }

}
