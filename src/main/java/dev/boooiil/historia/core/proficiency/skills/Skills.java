package dev.boooiil.historia.core.proficiency.skills;

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

    /**
     * Skill types for HistoriaPlayer skills.
     */
    public enum SkillType {
        NAME_TAG("nametag"),
        FEATHER_FALL("featherFall"),
        QUICK_CHARGE("quickCharge"),
        EFFICIENCY_PICKAXE("efficiencyPickaxe"),
        EFFICIENCY_SHOVEL("efficiencyShovel"),
        EFFICIENCY_AXE("efficiencyAxe"),
        CHANCE_EXTRA_ORE("chanceExtraOre"),
        CHANCE_EXTRA_WOOD("chanceExtraWood"),
        CHANCE_EXTRA_WOOL("chanceExtraWool"),
        CHANCE_EXTRA_FEATHERS("chanceExtraFeathers"),
        CHANCE_NO_ANVIL_DAMAGE("chanceNoAnvilDamage"),
        CHANCE_NO_CONSUME_BLOCK("chanceNoConsumeBlock"),
        LADDER_BYPASS("ladderBypass"),
        IGNITE_OIL("igniteOil"),
        BREAK_GRASS("breakGrass"),
        TAME_ANIMALS("tameAnimals"),
        SWEEPING_EDGE("sweepingEdge"),
        BREAK_BEEHIVE("breakBeehive"),
        APPLY_UNBREAKING("applyUnbreaking"),
        APPLY_SHARPNESS("applySharpness"),
        SHEAR_CHICKEN("shearChicken"),
        HARVEST_BONES("bonesFromAnimals"),
        HARVEST_LEATHER("harvestLeather"),
        MAKE_KNOWLEDGE_BOOK("makeKnowledgeBook"),
        CAN_BREED("canBreed"),
        CAN_CLIMB_LOGS("canClimbLogs");

        private final String key;

        SkillType(String key) {

            this.key = key;

        }

        public String getKey() {

            return this.key;

        }
    }

    /** Holds all existing skills and whether they have them. */
    private final HashMap<SkillType, Boolean> skills = new HashMap<>();
    /** Map of Regex patterns and enchants to apply to certain items. */
    private final HashMap<Pattern, Enchantment> skillEnchants = new HashMap<>();

    public Skills(ConfigurationSection section) {

        skills.put(SkillType.NAME_TAG, section.getBoolean("nametag"));
        skills.put(SkillType.FEATHER_FALL, section.getBoolean("featherFall"));
        skills.put(SkillType.QUICK_CHARGE, section.getBoolean("quickCharge"));
        skills.put(SkillType.EFFICIENCY_PICKAXE, section.getBoolean("efficiencyPickaxe"));
        skills.put(SkillType.EFFICIENCY_SHOVEL, section.getBoolean("efficiencyShovel"));
        skills.put(SkillType.EFFICIENCY_AXE, section.getBoolean("efficiencyAxe"));
        skills.put(SkillType.CHANCE_EXTRA_ORE, section.getBoolean("chanceExtraOre"));
        skills.put(SkillType.CHANCE_EXTRA_WOOD, section.getBoolean("chanceExtraWood"));
        skills.put(SkillType.CHANCE_EXTRA_WOOL, section.getBoolean("chanceExtraWool"));
        skills.put(SkillType.CHANCE_EXTRA_FEATHERS, section.getBoolean("chanceExtraFeathers"));
        skills.put(SkillType.CHANCE_NO_ANVIL_DAMAGE, section.getBoolean("chanceNoAnvilDamage"));
        skills.put(SkillType.CHANCE_NO_CONSUME_BLOCK, section.getBoolean("chanceNoConsumeBlock"));
        skills.put(SkillType.LADDER_BYPASS, section.getBoolean("ladderBypass"));
        skills.put(SkillType.IGNITE_OIL, section.getBoolean("igniteOil"));
        skills.put(SkillType.BREAK_GRASS, section.getBoolean("breakGrass"));
        skills.put(SkillType.TAME_ANIMALS, section.getBoolean("tameAnimals"));
        skills.put(SkillType.SWEEPING_EDGE, section.getBoolean("sweepingEdge"));
        skills.put(SkillType.BREAK_BEEHIVE, section.getBoolean("breakBeehive"));
        skills.put(SkillType.APPLY_UNBREAKING, section.getBoolean("applyUnbreaking"));
        skills.put(SkillType.APPLY_SHARPNESS, section.getBoolean("applySharpness"));
        skills.put(SkillType.SHEAR_CHICKEN, section.getBoolean("shearChicken"));
        skills.put(SkillType.HARVEST_BONES, section.getBoolean("bonesFromAnimals"));
        skills.put(SkillType.HARVEST_LEATHER, section.getBoolean("harvestLeather"));
        skills.put(SkillType.MAKE_KNOWLEDGE_BOOK, section.getBoolean("makeKnowledgeBook"));
        skills.put(SkillType.CAN_BREED, section.getBoolean("canBreed"));
        skills.put(SkillType.CAN_CLIMB_LOGS, section.getBoolean("canClimbLogs"));

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
    public boolean hasSkill(SkillType skill) {
        return skills.get(skill);
    }

    /**
     * Check if the player has skill enchants.
     * 
     * @return true if the player has skill enchants.
     */
    public boolean hasSkillEnchants() {
        return !skillEnchants.isEmpty();
    }

    /**
     * Get the enchantment for a specific material that matches the pattern.
     * 
     * @param material The material to check against the pattern.
     * @return The Enchantment that matches the pattern, or null if no match is
     *         found.
     */
    public Enchantment getSkillEnchantment(Material material) {

        for (Map.Entry<Pattern, Enchantment> entry : skillEnchants.entrySet()) {

            if (entry.getKey().matcher(material.toString()).matches())
                return entry.getValue();

        }

        return null;
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
