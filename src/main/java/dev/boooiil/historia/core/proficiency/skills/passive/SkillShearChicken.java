package dev.boooiil.historia.core.proficiency.skills.passive;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.skills.ISkillHandler;
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier;
import dev.boooiil.historia.core.proficiency.skills.Skills;
import dev.boooiil.historia.core.util.NumberUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class SkillShearChicken implements ISkillHandler {

    @EventHandler
    public void handle(PlayerInteractEntityEvent event) {
        execute(new SkillSupplier<>(event));
    }

    /**
     * Get the type of the skill.
     *
     * @return Type of the skill.
     */
    @Override
    public Skills.SkillType getType() {
        return Skills.SkillType.PASSIVE;
    }

    /**
     * Get the name of the skill.
     *
     * @return Name of the skill.
     */
    @Override
    public Skills.SkillName getName() {
        return Skills.SkillName.SHEAR_CHICKEN;
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    @Override
    public void execute(SkillSupplier<?>... skillSuppliers) {

        // [x] Player has shear
        // [x] Entity is chicken
        // [x] Player has skill
        // [x] Chicken is adult
        // [x] Break shear on 0 durability
        // [x] Set damaged shear in inventory
        // [x] Drop item
        // [x] Reset age of chicken

        PlayerInteractEntityEvent event = (PlayerInteractEntityEvent) skillSuppliers[0].get();
        Player player = event.getPlayer();
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        boolean isChicken = event.getRightClicked().getType() == EntityType.CHICKEN;
        boolean hasSkill = historiaPlayer.getProficiency().getSkills().hasSkill(Skills.SkillName.SHEAR_CHICKEN.getKey());
        boolean isHoldingShears = player.getInventory().getItemInMainHand().getType() == Material.SHEARS;

        if (!isChicken || !hasSkill || !isHoldingShears) {
            return;
        }

        Ageable agedChicken = (Ageable) event.getRightClicked();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!agedChicken.isAdult()) {
            return;
        }

        Damageable damageableItem = (Damageable) item;
        Location location = event.getRightClicked().getLocation();
        World world = player.getWorld();

        // has one use left
        if (damageableItem.getDamage() == 1) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            world.playSound(location, Sound.ENTITY_ITEM_BREAK, 1, 1);
        }
        else {
            damageableItem.setDamage(damageableItem.getDamage() + 1);
            player.getInventory().setItemInMainHand((ItemStack)damageableItem);
        }

        world.playSound(location, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
        world.dropItemNaturally(location, new ItemStack(Material.FEATHER, NumberUtils.randomInt(1, 4)));

        agedChicken.setBaby();

    }

    /**
     * Register to be used to handle when the skill executes.
     */
    @Override
    public void register() {
        HistoriaCore.getInstance().registerEvent(this);
    }

    @Override
    public void deregister() {

    }
}
