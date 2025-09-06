package dev.boooiil.historia.core.proficiency.skills;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.registry.Registry;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Skill holder for HistoriaPlayer skills.
 */
@NullMarked
public class Skills implements JSONSerializable {

    public enum SkillType {
        PASSIVE,
        ACTIVE,
        RUNNER_PASSIVE,
        RUNNER_ACTIVE
    }

    /**
     * Skill types for HistoriaPlayer skills.
     */
    public enum SkillName {
        NAME_TAG(HistoriaCore.getNamespacedKey("nametag")),
        FEATHER_FALL(HistoriaCore.getNamespacedKey("feather_fall")),
        QUICK_CHARGE(HistoriaCore.getNamespacedKey("quick_charge")),
        EFFICIENCY_PICKAXE(HistoriaCore.getNamespacedKey("efficiency_pickaxe")),
        EFFICIENCY_SHOVEL(HistoriaCore.getNamespacedKey("efficiency_shovel")),
        EFFICIENCY_AXE(HistoriaCore.getNamespacedKey("efficiency_axe")),
        CHANCE_EXTRA_ORE(HistoriaCore.getNamespacedKey("chance_extra_ore")),
        CHANCE_EXTRA_WOOD(HistoriaCore.getNamespacedKey("chance_extra_wood")),
        CHANCE_EXTRA_WOOL(HistoriaCore.getNamespacedKey("chance_extra_wool")),
        CHANCE_EXTRA_FEATHERS(HistoriaCore.getNamespacedKey("chance_extra_feathers")),
        CHANCE_NO_ANVIL_DAMAGE(HistoriaCore.getNamespacedKey("chance_no_anvil_damage")),
        CHANCE_NO_CONSUME_BLOCK(HistoriaCore.getNamespacedKey("chance_no_consume_block")),
        LADDER_BYPASS(HistoriaCore.getNamespacedKey("ladder_bypass")),
        IGNITE_OIL(HistoriaCore.getNamespacedKey("ignite_oil")),
        BREAK_GRASS(HistoriaCore.getNamespacedKey("break_Grass")),
        TAME_ANIMALS(HistoriaCore.getNamespacedKey("tame_Animals")),
        SWEEPING_EDGE(HistoriaCore.getNamespacedKey("sweeping_Edge")),
        BREAK_BEEHIVE(HistoriaCore.getNamespacedKey("break_Beehive")),
        APPLY_UNBREAKING(HistoriaCore.getNamespacedKey("apply_unbreaking")),
        APPLY_SHARPNESS(HistoriaCore.getNamespacedKey("apply_sharpness")),
        SHEAR_CHICKEN(HistoriaCore.getNamespacedKey("shear_chicken")),
        HARVEST_BONES(HistoriaCore.getNamespacedKey("bones_from_animals")),
        HARVEST_LEATHER(HistoriaCore.getNamespacedKey("harvest_leather")),
        MAKE_KNOWLEDGE_BOOK(HistoriaCore.getNamespacedKey("make_knowledge_book")),
        CAN_BREED(HistoriaCore.getNamespacedKey("can_breed")),
        CAN_CLIMB_LOGS(HistoriaCore.getNamespacedKey("can_climb_logs"));

        private final NamespacedKey key;

        SkillName(NamespacedKey key) {

            this.key = key;

        }

        public NamespacedKey getKey() {

            return this.key;

        }
    }

    /** Holds all existing skills and whether they have them. */
    private final Registry<Boolean> skills = new Registry<>(Boolean.class);
    //private final HashMap<SkillName, Boolean> skills = new HashMap<>();

    public Skills(ConfigurationSection section) {

        for (SkillName key : SkillName.values()) {
            skills.register(key.getKey(), section.getBoolean(key.getKey().getKey()));
        }

        if (section.contains("enchants")) {

            for (String itemNumber : section.getConfigurationSection("enchants").getKeys(false)) {

                String regex = section.getString("enchants." + itemNumber + ".regex");

                CoreLogger.debugToConsole("Adding weapon regex " + regex + " to skill enchants");
                Pattern pattern = Pattern.compile(regex);

                for (String enchantment : section.getStringList("enchants." + itemNumber + ".values")) {

                    CoreLogger.debugToConsole("Adding enchantment " + enchantment + " to item " + itemNumber);

                    Enchantment enchant = Enchantment
                            .getByKey(NamespacedKey.minecraft(enchantment));

                    // we will eventually have to use this method:
                    // Enchantment enchant =
                    // Registry.ENCHANTMENT.get(NamespacedKey.minecraft(enchantment.toLowerCase()));

                    skillEnchants.put(pattern, enchant);

                }

            }

        }

    }

    /**
     * Check if a player has a certain skill.
     * 
     * @param skill The skill to check.
     * @return true if the player has that skill.
     */
    public boolean hasSkill(NamespacedKey skill) {
        return skills.containsKey(skill);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Skills");
        sb.append("{");
        sb.append(JSONUtils.fromMap("skills", skills, true) + ", ");
        sb.append(JSONUtils.fromMap("skillEnchants", skillEnchants, true));
        sb.append("}");

        return sb.toString();
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromMap("skills", skills) + ", ");
        sb.append(JSONUtils.fromMap("skillEnchants", skillEnchants));
        sb.append("}");

        return sb.toString();
    }
}
