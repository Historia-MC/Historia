package dev.boooiil.historia.events.pvp;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.classes.items.craftable.Weapon;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

public class PlayerHit implements Listener {
    
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {

        Entity attacker = event.getDamager();
        Entity defender = event.getEntity();

        if (attacker instanceof Player && defender instanceof Player) {

            Player attackerPlayer = (Player) attacker;
            Player defenderPlayer = (Player) defender;

            HistoriaPlayer historiaAttacker = PlayerStorage.getPlayer(attackerPlayer.getUniqueId(), false);
            HistoriaPlayer historiaDefender = PlayerStorage.getPlayer(defenderPlayer.getUniqueId(), false);

            ItemStack weapon = attackerPlayer.getInventory().getItemInMainHand();

            float evasionRoll = Math.round(Math.random() * 100) / 100;

            if (evasionRoll < historiaDefender.getProficiency().getStats().getBaseEvasion()) {

                Logging.infoToPlayer(defenderPlayer.getCustomName() + " has evaded the attack!", attackerPlayer.getUniqueId());
                Logging.infoToPlayer("You evaded an attack from " + attackerPlayer.getCustomName() + "!", defenderPlayer.getUniqueId());

                event.setCancelled(true);
                return;

            }

            // if it is a valid weapon from the config
            if (Config.getWeaponConfig().isValid(weapon.getItemMeta().getLocalizedName())) {

                Weapon historiaWeapon = Config.getWeaponConfig().getObject(weapon.getItemMeta().getLocalizedName());
                
                // if the player is proficient with the weapon
                if (historiaAttacker.getProficiency().getStats().getWeaponProficiency().contains(historiaWeapon.getWeightClass())) {

                    // update the weapon stats from the given lore
                    historiaWeapon.updateWeaponStats(weapon.getItemMeta().getLore());

                    // reduce damage based on the sword (generic attack) proficiency
                    double damage = historiaWeapon.getDamage() * historiaAttacker.getProficiency().getStats().getBaseSwordProficiency();

                    Logging.infoToPlayer("You hit " + defenderPlayer.getCustomName() + " for " + damage + " damage!", attackerPlayer.getUniqueId());
                    Logging.infoToPlayer(attackerPlayer.getCustomName() + " hit you for " + damage + " damage!", defenderPlayer.getUniqueId());

                    event.setDamage(damage);

                } else {

                    Logging.infoToPlayer("You are not proficient with this weapon!", attackerPlayer.getUniqueId());
                    Logging.infoToPlayer(attackerPlayer.getCustomName() + " is not proficient with their weapon!", defenderPlayer.getUniqueId());

                    event.setCancelled(true);
                    return;

                }

            }

            //TODO: check armor proficiency
            



        }
        
        
    }

}
