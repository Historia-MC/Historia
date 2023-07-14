package dev.boooiil.historia.handlers.pvp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;

public class PlayerKilled {

    PlayerDeathEvent event;
    HistoriaPlayer historiaPlayer;
    
    public PlayerKilled(PlayerDeathEvent event, HistoriaPlayer historiaPlayer) {

        this.event = event;
        this.historiaPlayer = historiaPlayer;

    }

    public void doDeath() {

        Player killer = event.getEntity().getKiller();

        Pattern beheadWeaponRegex = Pattern.compile("sword|axe", Pattern.CASE_INSENSITIVE);
        Matcher beheadWeaponMatch = beheadWeaponRegex.matcher(killer.getInventory().getItemInMainHand().getType().toString());
        boolean validBeheadWeapon = beheadWeaponMatch.find();

        if (validBeheadWeapon) {

            Float beheadChance = historiaPlayer.getProficiency().getStats().getBeheadChance();
            Float beheadRoll = (float) Math.round(Math.random() * 100) / 100;

            if (beheadRoll <= beheadChance) {

                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();

                headMeta.setOwningPlayer(event.getEntity());
                head.setItemMeta(headMeta);

                event.setDeathMessage(event.getEntity().getDisplayName() + " was beheaded by " + event.getEntity().getKiller().getDisplayName() + "!");
                event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), head);

            }

        }

    }

}
