package dev.boooiil.historia.tools;

import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.Config;

public class FurnaceManager {
    
    public static void fishSmelting(FurnaceSmeltEvent furnaceSmeltEvent){
        
        // ~~~ Return checks ~~~

        // return if the following conditions are failed
        if (!(furnaceSmeltEvent instanceof FurnaceSmeltEvent)) return;
        //Bukkit.getLogger().info("1 check");
        if (!furnaceSmeltEvent.getSource().getItemMeta().hasLore()) return;
        //Bukkit.getLogger().info("2 check");
        // THIS THIRD CHECK SHOULD SEE IF the code is the right type!
        //Bukkit.getLogger().info("3 check");
        Boolean hasRequiredLore = false;
        
        for (String lore : furnaceSmeltEvent.getSource().getItemMeta().getLore()) {
            if (lore.contains("catch")){
                hasRequiredLore = true;
                break;
            }
        }

        if (!hasRequiredLore) return;

        // Checks finish

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
            default:
                amount = 1;
                break;
        }

        // Debug statement (leave commented unless you want a ton of messages!)
        //Bukkit.getLogger().info("Pounds: " + lbs);

        // Get the resulting output item
        ItemStack output = furnaceSmeltEvent.getResult();
        
        // Add to the current amount the amount
        // Subtract 1 from the amount! kind of a glitch... Too bad!
        output.setAmount(output.getAmount() - 1 + (amount));

        // Debug statement (COMNMENT OUT UNLESS YOU WANT A LOT OF MESSAGES!)
        // Bukkit.getLogger().info("Finished: " + amount);

    }

    public static void grantExperienceFromSmelting(FurnaceSmeltEvent event){

        //This should check to see what the item result is and then apply experience to the current player based on that.

        ItemStack item = event.getSource();
        ItemMeta meta = item.getItemMeta();
        
        // Checks finish

    }

    public static void setCookTime(ItemStack item, Block block) {

        if (block instanceof Furnace && item.getItemMeta().hasLocalizedName()) {

            Furnace furnace = (Furnace) block;

            Map<String, Object> ore = Config.getOreInfo(item.getItemMeta().getLocalizedName());

            int cookTime = (int) ore.get("SMELT_TIME") * 60 * 20;

            if (furnace.getCookTimeTotal() != cookTime) {

                furnace.setCookTimeTotal(cookTime);

            }
        }
    }

    public static void setCookTime(ItemStack item, Furnace furnace) {

        if (item.getItemMeta().hasLocalizedName()) {

            Map<String, Object> ore = Config.getOreInfo(item.getItemMeta().getLocalizedName());

            int cookTime = (int) ore.get("SMELT_TIME") * 60 * 20;

            if (furnace.getCookTimeTotal() != cookTime) {

                furnace.setCookTimeTotal(cookTime);

            }
        }
    }

    public static ItemStack smeltChunk(ItemStack chunk) {

        //Fails inside of this function.

        ItemMeta meta = chunk.getItemMeta();

        String ingot = meta.getLocalizedName().replace("CHUNK", "INGOT");

        Map<String, Object> ingotMap = Config.getIngotInfo(ingot);

        Bukkit.getLogger().info("LOOKFING FOR INGOT: " + ingot + " RETURNED: " + ingotMap.toString());

        return (ItemStack) ingotMap.get("ITEM");

    }

    public static ItemStack upgradeIngot(ItemStack oldIngot) {

        if (!oldIngot.getItemMeta().hasLocalizedName()) {

            Map<String, Object> oldIngotMap;

            if (oldIngot.getType() == Material.IRON_INGOT) oldIngotMap = Config.getIngotInfo("LOW_IRON_INGOT");
            else oldIngotMap = Config.getIngotInfo("LOW_GOLD_INGOT");

            return (ItemStack) oldIngotMap.get("ITEM");
        }

        else {

            Map<String, Object> oldIngotMap = Config.getIngotInfo(oldIngot.getItemMeta().getLocalizedName());

            if (oldIngotMap.get("PROGRESSION") != null) {
    
                Bukkit.getLogger().info("Ingot had progression.");
    
                Map<String, Object> newIngotMap = Config.getIngotInfo((String) oldIngotMap.get("PROGRESSION"));
    
                return (ItemStack) newIngotMap.get("ITEM");
            }
    
            else return oldIngot;

        }
    }
}
