package dev.boooiil.historia.core.proficiency.skills.passive.item;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.skills.ISkillHandler;
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier;
import dev.boooiil.historia.core.proficiency.skills.Skills;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.regex.Pattern;

public class SkillEnchant implements ISkillHandler {

    private final EnumMap<Material, Skills.SkillName> WANTED_ITEMS = new EnumMap<>(Material.class);

    SkillEnchant(NamespacedKey proficiencyName, int level) {

    }

    @EventHandler
    public void handle(PlayerItemHeldEvent event) {
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
    public NamespacedKey getName() {
        return Skills.SkillName.EFFICIENCY_PICKAXE.getKey();
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    @Override
    public void execute(SkillSupplier<?>... skillSuppliers) {

        PlayerItemHeldEvent event = (PlayerItemHeldEvent) skillSuppliers[0].get();

        for (Player player : Bukkit.getOnlinePlayers()) {

                Inventory inventory = player.getInventory();
                ItemStack item = inventory.getItem(48);

                if (item == null) continue;

                if (WANTED_ITEMS.containsKey(item.getType())) {
                    Skills.SkillName skillName = WANTED_ITEMS.get(item.getType());
                    HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

                    if (historiaPlayer.getProficiency().hasSkill(this)) {
//                        addEnchant(inventory, i, Enchantment.EFFICIENCY);
                        AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_BREAK_SPEED);
                        AttributeModifier modifier = new AttributeModifier(HistoriaCore.getNamespacedKey("skill_block_break"), 3f ,AttributeModifier.Operation.ADD_NUMBER);

                        float attributeValue = 3f * historiaPlayer.getLevel();

                        if (attributeValue != attribute.getValue()) {
                            // apply
                            attribute.addModifier(modifier);
                        } else {
                            //
                        }

                        if (attribute == null) {
                            throw new IllegalStateException("Player does not have attribute BLOCK_BREAK_SPEED");
                        }

                    }

                }
        }
    }

    /**
     * Register to be used to handle when the skill executes.
     */
    @Override
    public void register() {

        for (Material material : Material.values()) {
            if (material.isLegacy()) continue;

            if (Pattern.compile(".*+_PICKAXE").matcher(material.toString()).matches()) {
                WANTED_ITEMS.put(material, Skills.SkillName.EFFICIENCY_PICKAXE);
                continue;
            }

            if (Pattern.compile(".*+_AXE").matcher(material.toString()).matches()) {
                WANTED_ITEMS.put(material, Skills.SkillName.EFFICIENCY_AXE);
                continue;
            }

            if (Pattern.compile(".*+_SHOVEL").matcher(material.toString()).matches()) {
                WANTED_ITEMS.put(material, Skills.SkillName.EFFICIENCY_SHOVEL);
            }
        }

        HistoriaCore.getInstance().registerEvent(this);
    }

    @Override
    public void deregister() {

    }

    @Override
    public String toJSON() {
        return "";
    }
}
