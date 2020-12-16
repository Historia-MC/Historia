package dev.boooiil.historia.tools;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FurnaceManager {
    
    public void FishSmelting(FurnaceSmeltEvent furnaceSmeltEvent){
        
        // ~~~ Return checks ~~~

        // return if the following conditions are failed
        if (!(furnaceSmeltEvent instanceof FurnaceSmeltEvent)) return;
        //Bukkit.getLogger().info("1 check");
        if (!furnaceSmeltEvent.getSource().getItemMeta().hasLore()) return;
        //Bukkit.getLogger().info("2 check");
        // THIS THIRD CHECK SHOULD SEE IF lbs exists in the code!
        // if (!furnaceSmeltEvent.getSource().getItemMeta().getLore().contains("lbs")) return;
        //Bukkit.getLogger().info("3 check");

        // Get item stack and item meta
        ItemStack item = furnaceSmeltEvent.getSource();
        ItemMeta meta = item.getItemMeta();
        
        // pounds set to 1 by default
        int lbs = 1;

        // For each value in meta lore
        for (String lore : meta.getLore()) {
            // Replace " lbs" with ""
            // then parse the int to get the weight
            String lbsStr = Pattern.compile(" lbs").matcher(lore).replaceAll("");
            lbs = Integer.parseInt(lbsStr);
        }

        // Fish amount equal to 1 by default
        int amount = 1;

        // If the pounds is greater than 5
        // set amount to the floor of pounds divided by 5
        if (lbs > 5){
            amount = (int)Math.floor((double)(lbs / 5));
        }

        // Debug statement
        //Bukkit.getLogger().info("Pounds: " + lbs);

        // Get the resulting output item
        ItemStack output = furnaceSmeltEvent.getResult();
        
        // Add to the current amount the amount
        // Subtract 1 from the amount! kind of a glitch... Too bad!
        output.setAmount(output.getAmount() - 1 + (amount));

        // Debug statement
        Bukkit.getLogger().info("Finished: " + amount);

    }

}
