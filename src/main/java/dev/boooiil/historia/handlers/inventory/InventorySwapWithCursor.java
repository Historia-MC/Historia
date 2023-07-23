package dev.boooiil.historia.handlers.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.util.Construct;
import net.md_5.bungee.api.ChatColor;

public class InventorySwapWithCursor {

    private InventoryClickEvent event;

    public InventorySwapWithCursor(InventoryClickEvent event) {

        this.event = event;

    }

    public void doClick() {

        //TODO: Possibly account for arrows that have multiple uses (through lore)

        // TODO: Account for multiple items in the slot
        // TODO: Ignite user for too many ignited arrows

        if (event.getCurrentItem() == null)
            return;
        if (event.getCurrentItem().getType() != Material.ARROW)
            return;
        if (event.getCursor().getType() != Material.FLINT_AND_STEEL)
            return;

        // TODO: Enable this on release
        // if (!event.getCurrentItem().getItemMeta().hasLore()) return;

        ItemStack flintAndSteelItem = event.getCursor();
        ItemStack newArrow = Construct.itemStack(Material.ARROW.toString(), 1, "Ignited Arrow", "Ignited_Arrow");
        // ItemStack arrowItem = event.getCurrentItem();

        ItemMeta flintAndSteelMeta = flintAndSteelItem.getItemMeta();
        ItemMeta arrowMeta = newArrow.getItemMeta();

        Damageable flintAndSteelDamageable = (Damageable) flintAndSteelMeta;

        // TODO: Comment this out on release
        List<String> lore = new ArrayList<String>();

        // TODO: Enable this on release
        // List<String> lore = arrowMeta.getLore();

        lore.add(ChatColor.RED + "Ignited - 1/1");
        arrowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        arrowMeta.setLore(lore);

        arrowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        flintAndSteelDamageable.setDamage(flintAndSteelDamageable.getDamage() + 8);

        if (flintAndSteelDamageable.getDamage() >= flintAndSteelItem.getType().getMaxDurability()) {
            flintAndSteelItem.setAmount(0);
            ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), "entity.item.break", 1, 1);
        }

        else {
            flintAndSteelItem.setItemMeta(flintAndSteelDamageable);
        }

        newArrow.setItemMeta(arrowMeta);

        event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() - 1);

        event.getClickedInventory().addItem(newArrow);
        event.setCancelled(true);

    }

}
