package dev.boooiil.historia.core.handlers.pvp;

import dev.boooiil.historia.core.classes.items.craftable.Weapon;
import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDamaged {

    private final Player attacker;
    private final Player defender;
    private final EntityDamageByEntityEvent event;
    private final HistoriaPlayer historiaAttacker;
    private final HistoriaPlayer historiaDefender;

    public PlayerDamaged(EntityDamageByEntityEvent event) {

        this.event = event;
        this.attacker = (Player) event.getDamager();
        this.defender = (Player) event.getEntity();
        this.historiaAttacker = PlayerStorage.getPlayer(attacker.getUniqueId(), false);
        this.historiaDefender = PlayerStorage.getPlayer(defender.getUniqueId(), false);

    }

    // calculate and apply base damage values to the event
    public void doAttack() {

        if (event.isCancelled()) {
            return;
        }

        ItemStack weapon = attacker.getInventory().getItemInMainHand();

        if (weapon.getItemMeta() == null)
            weapon = new ItemStack(Material.STICK);

        // if it is a valid weapon from the config
        if (ConfigurationLoader.getWeaponConfig().isValid(weapon.getItemMeta().getLocalizedName())) {

            Weapon historiaWeapon = ConfigurationLoader.getWeaponConfig()
                    .getObject(weapon.getItemMeta().getLocalizedName());

            // if the player is proficient with the weapon
            if (historiaAttacker.getProficiency().getStats().getUsableWeaponTypes()
                    .contains(historiaWeapon.getWeightClass())) {

                // update the weapon stats from the given lore
                // historiaWeapon.updateWeaponStats(weapon.getItemMeta().getLore())

                // reduce damage based on the sword (generic attack) proficiency
                // double damage = historiaWeapon.getDamage()
                // * historiaAttacker.getProficiency().getStats().getBaseSwordProficiency();

                // Logging.infoToPlayer("You hit " + defender.getCustomName() + " for " + damage
                // + " damage!",
                // attacker.getUniqueId());
                // Logging.infoToPlayer(attacker.getCustomName() + " hit you for " + damage + "
                // damage!",
                // defender.getUniqueId());

                // event.setDamage(damage);

                Logging.debugToConsole("Player attacked with " + event.getDamage() + " damage.");

            } else {

                Logging.infoToPlayer("You are not proficient with this weapon!", attacker.getUniqueId());
                Logging.infoToPlayer(attacker.getCustomName() + " is not proficient with their weapon and misses!",
                        defender.getUniqueId());

                event.setCancelled(true);

            }
        }

    }

    public void doDefend() {

        if (event.isCancelled()) {
            return;
        }

        double evasionRoll = (double) Math.round(Math.random() * 100) / 100;

        if (evasionRoll < historiaDefender.getProficiency().getStats().getBaseEvasion()) {

            Logging.infoToPlayer(defender.getDisplayName() + " has evaded the attack!",
                    attacker.getUniqueId());
            Logging.infoToPlayer("You evaded an attack from " + attacker.getDisplayName() + "!",
                    defender.getUniqueId());

            event.setCancelled(true);
            return;

        }

        defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3, 1));

    }

    public void doEvade() {

    }

}
