package dev.boooiil.historia.expiry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExpiryHandler {

    public static void initiateExpiry(Player player, ItemStack item, Integer inventorySlot) {

        if (item.getType().equals(Material.AIR)) return;
        if (!item.getType().isEdible()) return;

        if (inventorySlot != null) {
            System.out.println("[INV SLOT] Slot: " + inventorySlot + " Item: " + item.getType().toString() + " Player: " + player.getDisplayName());
        }
    }

    public static ItemStack setExpiry(ItemStack item, Integer amount) {

        Calendar date = Calendar.getInstance();

        ItemStack modifyItem = new ItemStack(Material.matchMaterial(item.getType().toString()));
        
        ItemMeta meta = modifyItem.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add("Expiry: " + (date.get(Calendar.MONTH) + 1)  + "-" + date.get(Calendar.DATE) + "-" + date.get(Calendar.YEAR));
        
        meta.setLore(lore); 
        modifyItem.setItemMeta(meta); 
        modifyItem.setAmount(amount); 
        return modifyItem;

    }
    public ItemStack setExpired(ItemStack item, Integer amount) {

        ItemStack modifyItem = new ItemStack(Material.matchMaterial(item.getType().toString()));
        
        ItemMeta meta = modifyItem.getItemMeta();
        
        meta.setLore(Arrays.asList("Spoiled")); modifyItem.setItemMeta(meta); modifyItem.setAmount(amount); return modifyItem;

    }
    public boolean checkIfExpired(String loreLines) {

            String[] compare = loreLines.replace("Expiry: ", "").split("-");

                Calendar date = Calendar.getInstance();

                int year = 0; int month = 0; int day = 0;

                month = (int) Integer.parseInt(compare[0]);
                day = (int) Integer.parseInt(compare[1]);
                year = (int) Integer.parseInt(compare[2]);
                            
                if (year < date.get(Calendar.YEAR)) return true;
                else if (month < (date.get(Calendar.MONTH) + 1)) return true;
                else if (month == (date.get(Calendar.MONTH) + 1) && day < date.get(Calendar.DATE)) return true;
                else return false;
    
    }
}