package dev.boooiil.historia.crafting;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponStats {
    
    public static void initiate(CraftItemEvent event){

        // Get item stack and meta from the result
        ItemStack item = event.getRecipe().getResult();
        ItemMeta meta = item.getItemMeta();

        // Remove th Hide Attributes flag if it exists
        /*
        if (meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)){
            meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        */

        // Get the range values
        // (get from config.java eventually)
        double rangeMin = 1.0;
        double rangeMax = 2.0;

        // Get random double for the sword
        Random random = new Random();
        double damage = rangeMin + (rangeMax - rangeMin) * random.nextDouble();

        // Set the weapon damage
        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        // Add the generic attack modifier
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);

        // Set the item meta
        item.setItemMeta(meta);

        Bukkit.getLogger().info("Attribute: " + meta.getAttributeModifiers());


    }
    
}
