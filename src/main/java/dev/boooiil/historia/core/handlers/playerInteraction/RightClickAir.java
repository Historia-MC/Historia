package dev.boooiil.historia.core.handlers.playerInteraction;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import dev.boooiil.historia.core.proficiency.skills.SkillType;

import java.util.List;

public class RightClickAir extends BaseInteractionEventBlock {

    public RightClickAir(PlayerInteractEvent event) {
        super(event);
    }

    @Override
    public void doInteraction() {

        if (this.getHeldItem() == null)
            return;
        if (this.getHeldItem().getType() != Material.BOW)
            return;
        if (this.getOffHandItem().getType() != Material.FLINT_AND_STEEL)
            return;
        if (!this.getHistoriaPlayer().getProficiency().getSkills().hasSkill(SkillType.IGNITE_OIL))
            return;

        ItemMeta handItemMeta = this.getHeldItem().getItemMeta();
        ItemMeta offhandItemMeta = this.getOffHandItem().getItemMeta();
        Damageable offhandItemDamageable = (Damageable) offhandItemMeta;

        List<String> lore = handItemMeta.getLore();

        lore.add(ChatColor.RED + "Ignited - 1/1");
        handItemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        handItemMeta.setLore(lore);

        offhandItemDamageable.setDamage(offhandItemDamageable.getDamage() + 1);

        if (offhandItemDamageable.getDamage() >= getHeldItem().getType().getMaxDurability()) {
            getHeldItem().setAmount(0);
            getPlayer().playSound(getPlayer().getLocation(), "entity.item.break", 1, 1);
        }

        else {
            this.getOffHandItem().setItemMeta(offhandItemMeta);
        }

        this.getHeldItem().setItemMeta(handItemMeta);

    }

}
