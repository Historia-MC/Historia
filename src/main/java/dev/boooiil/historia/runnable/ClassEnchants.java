package dev.boooiil.historia.runnable;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.PlayerInventory;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;

public class ClassEnchants {

    private HistoriaPlayer historiaPlayer;
    private PlayerInventory inventory;

    public ClassEnchants(HistoriaPlayer historiaPlayer, PlayerInventory inventory) {
        this.historiaPlayer = historiaPlayer;
        this.inventory = inventory;
    }

    public void doApplyEnchants() {

        inventory.iterator().forEachRemaining(item -> {

            if (item.getType().toString().contains("PICKAXE")) {

                if (historiaPlayer.getProficiency().getSkills().hasEfficiencyPickaxe()) {

                    if (!item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().addEnchant(Enchantment.DIG_SPEED, 1, true);

                    }

                } else {

                    if (item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().removeEnchant(Enchantment.DIG_SPEED);

                    }

                }

            }

            if (item.getType().toString().contains("AXE")) {

                if (historiaPlayer.getProficiency().getSkills().hasEfficiencyAxe()) {

                    if (!item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().addEnchant(Enchantment.DIG_SPEED, 1, true);

                    }

                } else {

                    if (item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

                        item.getItemMeta().removeEnchant(Enchantment.DIG_SPEED);

                    }

                }
            }

            if (item.getType().toString().contains("BOOTS")) {

                if (historiaPlayer.getProficiency().getSkills().hasFeatherFall()) {

                    if (!item.getItemMeta().hasEnchant(Enchantment.PROTECTION_FALL)) {

                        item.getItemMeta().addEnchant(Enchantment.PROTECTION_FALL, 3, true);

                    }

                } else {

                    if (item.getItemMeta().hasEnchant(Enchantment.PROTECTION_FALL)) {

                        item.getItemMeta().removeEnchant(Enchantment.PROTECTION_FALL);

                    }

                }

            }

            if (item.getType().toString().contains("CROSSBOW")) {

                if (historiaPlayer.getProficiency().getSkills().hasQuickCharge()) {

                    if (!item.getItemMeta().hasEnchant(Enchantment.QUICK_CHARGE)) {

                        item.getItemMeta().addEnchant(Enchantment.QUICK_CHARGE, 1, true);

                    }

                } else {

                    if (item.getItemMeta().hasEnchant(Enchantment.QUICK_CHARGE)) {

                        item.getItemMeta().removeEnchant(Enchantment.QUICK_CHARGE);

                    }

                }

            }

        });

    }

}
