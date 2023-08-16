package dev.boooiil.historia.core.handlers.pvp;

import dev.boooiil.historia.core.util.Logging;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDecreaseSharpness {

    EntityDamageByEntityEvent event;
    ItemStack weapon;

    public PlayerDecreaseSharpness(EntityDamageByEntityEvent event) {

        this.event = event;
        this.weapon = ((Player) event.getDamager()).getInventory().getItemInMainHand();

    }

    public void doDecrease() {

        // decrement sharpness level
        if (weapon.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {

            Logging.debugToConsole("[PlayerDecreaseSharpness] Weapon has sharpness.");

            int uses = getSharpnessUses(weapon);

            Logging.debugToConsole("[PlayerDecreaseSharpness] Sharpness uses: " + uses);

            decreaseSharpness(weapon, weapon.getEnchantmentLevel(Enchantment.DAMAGE_ALL), uses);

        }

    }

    private int getSharpnessUses(ItemStack weapon) {

        List<String> lore = weapon.getItemMeta().getLore();
        Pattern pattern = Pattern.compile("Sharpness \\(I+\\): (\\d+)\\/\\d+");
        Matcher matcher = pattern.matcher(lore.get(lore.size() - 1));

        if (matcher.find()) {

            Logging.debugToConsole("[PlayerDecreaseSharpness] Matched uses: " + matcher.group(1));

            return Integer.parseInt(matcher.group(1));

        }

        else
            return 0;

    }

    private void decreaseSharpness(ItemStack weapon, int sharpnessLevel, int sharpnessUses) {

        ItemMeta itemMeta = weapon.getItemMeta();
        List<String> lore = weapon.getItemMeta().getLore();

        // if 0/<level> uses, decrease
        if (sharpnessUses - 1 <= 0) {

            Logging.debugToConsole("[PlayerDecreaseSharpness] Sharpness uses is 0.");

            // if sharpness level 0, remove enchantment
            if (sharpnessLevel - 1 == 0) {

                Logging.debugToConsole("[PlayerDecreaseSharpness] Sharpness level is 0.");

                itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);
                lore.remove(lore.size() - 1);
                itemMeta.setLore(lore);

            }

            // if the sharpness level is not 0, decrement
            else {

                Logging.debugToConsole("[PlayerDecreaseSharpness] Sharpness level is not 0.");

                int adjustedLevel = sharpnessLevel - 1;
                int adjustedUses = (int) Math.round(adjustedLevel * 1.5);

                weapon.addEnchantment(Enchantment.DAMAGE_ALL, adjustedLevel);
                lore.set(lore.size() - 1, "Sharpness (" + "I".repeat(adjustedLevel) + "): " + adjustedUses
                        + "/" + adjustedUses);
                itemMeta.setLore(lore);

            }
        }

        // else decrement use
        else {

            Logging.debugToConsole("[PlayerDecreaseSharpness] Sharpness uses is not 0.");

            lore.set(lore.size() - 1, "Sharpness (" + "I".repeat(sharpnessLevel) + "): " + (sharpnessUses - 1)
                    + "/" + ((int) Math.round(sharpnessLevel * 1.5)));
            
            itemMeta.setLore(lore);

        }

        weapon.setItemMeta(itemMeta);

    }
}
