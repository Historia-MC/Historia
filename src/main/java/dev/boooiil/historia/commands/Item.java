package dev.boooiil.historia.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("item")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                ItemStack sword = new ItemStack(Material.IRON_SWORD);
                ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                ItemStack boots = new ItemStack(Material.IRON_BOOTS);
                ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);

                ItemMeta swordMeta = sword.getItemMeta();

                swordMeta.setLocalizedName("Placeholder_Sword");
                sword.setItemMeta(swordMeta);

                ItemMeta helmetMeta = helmet.getItemMeta();

                helmetMeta.setLocalizedName("Placeholder_Helmet");
                helmet.setItemMeta(helmetMeta);

                ItemMeta chestplateMeta = chestplate.getItemMeta();

                chestplateMeta.setLocalizedName("Placeholder_Chestplate");
                chestplate.setItemMeta(chestplateMeta);

                ItemMeta bootsMeta = boots.getItemMeta();

                bootsMeta.setLocalizedName("Placeholder_Boots");
                boots.setItemMeta(bootsMeta);

                ItemMeta leggingsMeta = leggings.getItemMeta();

                leggingsMeta.setLocalizedName("Placeholder_Leggings");
                leggings.setItemMeta(leggingsMeta);
                
                //helmet.getItemMeta().setLocalizedName("Placeholder_Helmet");
                //chestplate.getItemMeta().setLocalizedName("Placeholder_Chestplate");
                //leggings.getItemMeta().setLocalizedName("Placeholder_Leggings");
                //boots.getItemMeta().setLocalizedName("Placeholder_Boots");

                player.sendMessage(sword.toString());

                sword.setItemMeta(sword.getItemMeta());

                Location location = player.getLocation();
                World world = player.getWorld();

                world.dropItemNaturally(location, sword);
                world.dropItemNaturally(location, helmet);
                world.dropItemNaturally(location, chestplate);
                world.dropItemNaturally(location, leggings);
                world.dropItemNaturally(location, boots);

                return true;

               
            }
        }
        return false;
    }

    public ItemStack getItem(String material) {

        if (material == "") {
            ItemStack boots = new ItemStack(Material.IRON_BOOTS);
            ItemMeta meta = boots.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Boots of Leaping");

            List<String> lore = new ArrayList<String>();

            lore.add("");
            lore.add(ChatColor.GOLD + "Boots of the Gods.");
            meta.setLore(lore);
            meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setUnbreakable(true);

            boots.setItemMeta(meta);

            return boots;
        }
        ItemStack item = new ItemStack(Material.matchMaterial(material));
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "" + material + " of Legends");

        List<String> lore = new ArrayList<String>();

        lore.add("");
        lore.add(ChatColor.GOLD + "" + material + " of the Gods.");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);

        return item;
    }
}