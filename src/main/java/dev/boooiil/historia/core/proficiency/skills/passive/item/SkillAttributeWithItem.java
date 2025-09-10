package dev.boooiil.historia.core.proficiency.skills.passive.item;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.skills.ISkillHandler;
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier;
import dev.boooiil.historia.core.proficiency.skills.Skills;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;

public class SkillAttributeWithItem implements ISkillHandler {

    private Attribute attribute;
    private AttributeModifier modifier;
    private NamespacedKey name;
    private Material material;
    private int level;

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
        return this.name;
    }

    /**
     * Execute the skill with a given set of supplied objects.
     *
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    @Override
    public void execute(SkillSupplier<?>... skillSuppliers) {

        PlayerItemHeldEvent event = (PlayerItemHeldEvent) skillSuppliers[0].get();
        Player player = event.getPlayer();
        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        if (!historiaPlayer.getProficiency().hasSkill(this)
                || historiaPlayer.getProficiency().getSkills().get(this) < level) {
            return;
        }

        Inventory inventory = player.getInventory();
        Material previousMaterial = inventory.getItem(event.getPreviousSlot()).getType();
        Material newMaterial = inventory.getItem(event.getNewSlot()).getType();

        if (material.equals(newMaterial)) {
            // addEnchant(inventory, i, Enchantment.EFFICIENCY);
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            attributeInstance.addModifier(modifier);

        } else if (material.equals(previousMaterial)) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);

            if (attributeInstance.getModifiers().contains(modifier)) {
                attributeInstance.removeModifier(modifier);
            }
        }
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

    public SkillAttributeWithItem create(ConfigurationSection section) {

        if (!section.contains("attribute")) {
            throw new IllegalArgumentException("Key 'attribute' must be specified.");
        }

        if (!section.contains("material")) {
            throw new IllegalArgumentException("Key 'material' must be specified.");
        }

        if (!section.contains("level")) {
            throw new IllegalArgumentException("Key 'level' must be specified.");
        }

        ConfigurationSection sModifier = section.getConfigurationSection("attribute");
        String sMat = section.getString("material");
        Material material = Material.matchMaterial(sMat);
        int skillLevel = section.getInt("level", 1);

        if (material == null) {
            throw new IllegalArgumentException("Invalid material specified.");
        }

        if (!sModifier.contains("attribute")) {
            throw new IllegalArgumentException("Key 'attribute' in attribute must be specified.");
        }

        if (!sModifier.contains("operation")) {
            throw new IllegalArgumentException("Key 'operation' in attribute must be specified.");
        }

        if (!sModifier.contains("level")) {
            throw new IllegalArgumentException("Key 'level' in attribute must be specified.");
        }

        String sAttribute = sModifier.getString("attribute");
        String sOperation = sModifier.getString("operation");
        double modifierLevel = sModifier.getDouble("factor", 1);

        Attribute attribute = Attribute.valueOf(sAttribute);
        AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(sOperation);
        AttributeModifier modifier = new AttributeModifier(
                HistoriaCore.getNamespacedKey("skill_attribute_with_item_" + material.toString().toLowerCase()),
                modifierLevel,
                operation);

        SkillAttributeWithItem skill = new SkillAttributeWithItem();
        skill.name = HistoriaCore.getNamespacedKey("skill_attribute_with_item_" + material.toString().toLowerCase());
        skill.modifier = modifier;
        skill.attribute = attribute;
        skill.material = material;
        skill.level = skillLevel;
        return skill;
    }

    @Override
    public String toJSON() {
        return "";
    }
}
