package dev.boooiil.historia.runnable;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.Logging;

public class ClassEnchants {

    private HistoriaPlayer historiaPlayer;
    private PlayerInventory inventory;

    public ClassEnchants(HistoriaPlayer historiaPlayer, PlayerInventory inventory) {
        this.historiaPlayer = historiaPlayer;
        this.inventory = inventory;
    }

    public void doApplyEnchants() {

        inventory.iterator().forEachRemaining(item -> {

            if (item == null)
                return;

            String itemType = item.getType().toString().toUpperCase();
            ItemMeta itemMeta = item.getItemMeta();

            if (itemType.contains("_PICKAXE")) {

                if (historiaPlayer.getProficiency().getSkills().hasEfficiencyPickaxe()) {

                    if (!itemMeta.hasEnchant(Enchantment.DIG_SPEED)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had a pickaxe in their inventory that wasn't enchanted.");

                        itemMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
                        item.setItemMeta(itemMeta);

                    }

                } else {

                    if (itemMeta.hasEnchant(Enchantment.DIG_SPEED)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had a pickaxe in their inventory with an illegal enchant.");

                        itemMeta.removeEnchant(Enchantment.DIG_SPEED);
                        item.setItemMeta(itemMeta);

                    }

                }

            }

            if (itemType.contains("_SHOVEL")) {

                if (historiaPlayer.getProficiency().getSkills().hasEfficiencyShovel()) {

                    if (!itemMeta.hasEnchant(Enchantment.DIG_SPEED)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had a shovel in their inventory that wasn't enchanted.");

                        itemMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
                        item.setItemMeta(itemMeta);

                    }

                } else {

                    if (itemMeta.hasEnchant(Enchantment.DIG_SPEED)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had a shovel in their inventory with an illegal enchant.");

                        itemMeta.removeEnchant(Enchantment.DIG_SPEED);
                        item.setItemMeta(itemMeta);

                    }

                }

            }

            if (itemType.contains("_AXE")) {

                if (historiaPlayer.getProficiency().getSkills().hasEfficiencyAxe()) {

                    if (!itemMeta.hasEnchant(Enchantment.DIG_SPEED)) {

                        Logging.debugToConsole(
                                historiaPlayer.getUsername() + " had an axe in their inventory that wasn't enchanted.");

                        itemMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
                        item.setItemMeta(itemMeta);

                    }

                } else {

                    if (itemMeta.hasEnchant(Enchantment.DIG_SPEED)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had a pickaxe in their inventory with an illegal enchant.");

                        itemMeta.removeEnchant(Enchantment.DIG_SPEED);
                        item.setItemMeta(itemMeta);

                    }

                }
            }

            if (itemType.contains("BOOTS")) {

                if (historiaPlayer.getProficiency().getSkills().hasFeatherFall()) {

                    if (!itemMeta.hasEnchant(Enchantment.PROTECTION_FALL)) {

                        Logging.debugToConsole(
                                historiaPlayer.getUsername() + " had boots in their inventory that weren't enchanted.");

                        itemMeta.addEnchant(Enchantment.PROTECTION_FALL, 3, true);
                        item.setItemMeta(itemMeta);

                    }

                } else {

                    if (itemMeta.hasEnchant(Enchantment.PROTECTION_FALL)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had boots in their inventory with an illegal enchant.");

                        itemMeta.removeEnchant(Enchantment.PROTECTION_FALL);
                        item.setItemMeta(itemMeta);

                    }

                }

            }

            if (itemType.contains("CROSSBOW")) {

                if (historiaPlayer.getProficiency().getSkills().hasQuickCharge()) {

                    if (!itemMeta.hasEnchant(Enchantment.QUICK_CHARGE)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had a crossbow in their inventory that wasn't enchanted.");

                        itemMeta.addEnchant(Enchantment.QUICK_CHARGE, 1, true);
                        item.setItemMeta(itemMeta);

                    }

                } else {

                    if (itemMeta.hasEnchant(Enchantment.QUICK_CHARGE)) {

                        Logging.debugToConsole(historiaPlayer.getUsername()
                                + " had a crossbow in their inventory with an illegal enchant.");

                        itemMeta.removeEnchant(Enchantment.QUICK_CHARGE);
                        item.setItemMeta(itemMeta);

                    }

                }

            }

        });

    }

}
