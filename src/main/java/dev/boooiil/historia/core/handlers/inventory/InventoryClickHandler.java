package dev.boooiil.historia.core.handlers.inventory;

import dev.boooiil.historia.core.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickHandler {

    private final InventoryClickEvent event;

    private ItemStack slottedItem;
    private ItemStack cursorItem;

    public InventoryClickHandler(InventoryClickEvent event) {

        this.event = event;
        this.slottedItem = event.getCurrentItem();
        this.cursorItem = event.getCursor();

    }

    public void doInventoryClick() {

        if (cursorItem.getType() == Material.FLINT_AND_STEEL && slottedItem.getType() == Material.ARROW) {
            doApplyFlameToArrow();
        }
    }

    private void doApplyFlameToArrow() {

        // TODO: Possibly account for arrows that have multiple uses (through lore)

        // TODO: Account for multiple items in the slot
        // TODO: Ignite user for too many ignited arrows

        // TODO: Enable this on release
        // if (!event.getCurrentItem().getItemMeta().hasLore()) return;

        ItemStack flintAndSteelItem = event.getCursor();
        ItemStack newArrow = new ItemStack(Material.ARROW);
        // ItemStack arrowItem = event.getCurrentItem();

        ItemMeta flintSteelMeta = this.cursorItem.getItemMeta();
        ItemMeta newArrowMeta = Main.server().getItemFactory().getItemMeta(Material.ARROW);

        Damageable flintAndSteelDamageable = (Damageable) flintSteelMeta;

        // TODO: Comment this out on release
        List<String> lore = new ArrayList<>();

        // TODO: Enable this on release
        // List<String> lore = arrowMeta.getLore();

        lore.add(ChatColor.RED + "Ignited - 1/1");
        newArrowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        newArrowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        newArrowMeta.lore(List.of(
                Component.text("Ignited - 1/1", TextColor.color(255, 0, 0))));

        flintAndSteelDamageable.setDamage(flintAndSteelDamageable.getDamage() + 8);

        if (flintAndSteelDamageable.getDamage() >= flintAndSteelItem.getType().getMaxDurability()) {
            flintAndSteelItem.setAmount(0);
            ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), "entity.item.break", 1, 1);
        }

        else {
            flintAndSteelItem.setItemMeta(flintAndSteelDamageable);
        }

        newArrow.setItemMeta(newArrowMeta);

        event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() - 1);

        event.getClickedInventory().addItem(newArrow);
        event.setCancelled(true);

    }

}
