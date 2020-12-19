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
        String catchType = "";

        // For each value in meta lore
        for (String lore : meta.getLore()) {
            // Replace " catch" with ""
            // then get the string to get the weight
            String catchStr = Pattern.compile(" catch").matcher(lore).replaceAll("");
            catchType = catchStr;
        }

        // Fish amount equal to 1 by default
        int amount = 1;

        // If the pounds is greater than 5
        // set amount to the floor of pounds divided by 5
        switch (catchType){
            case("Small"):
                amount = 1;
                break;
            case("Medium"):
                amount = 2;
                break;
            case("Large"):
                amount = 3;
                break;
            case("Legendary"):
                amount = 5;
                break;
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
