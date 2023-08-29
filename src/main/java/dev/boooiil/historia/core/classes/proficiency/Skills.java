package dev.boooiil.historia.core.classes.proficiency;

import dev.boooiil.historia.core.classes.enums.proficiency.SkillType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class represents the skills of a player in the game. It contains boolean
 * variables that represent
 * Whether the player has certain skills or abilities.
 */
public class Skills {

    private final EnumMap<SkillType, Boolean> skills = new EnumMap<>(SkillType.class);
    private final HashMap<Pattern, Enchantment> skillEnchants = new HashMap<>();

    public Skills(FileConfiguration config, String root) {

        skills.put(SkillType.NAME_TAG, config.getBoolean(root + ".nametag"));
        skills.put(SkillType.FEATHER_FALL, config.getBoolean(root + ".featherFall"));
        skills.put(SkillType.QUICK_CHARGE, config.getBoolean(root + ".quickCharge"));
        skills.put(SkillType.EFFICIENCY_PICKAXE, config.getBoolean(root + ".efficiencyPickaxe"));
        skills.put(SkillType.EFFICIENCY_SHOVEL, config.getBoolean(root + ".efficiencyShovel"));
        skills.put(SkillType.EFFICIENCY_AXE, config.getBoolean(root + ".efficiencyAxe"));
        skills.put(SkillType.CHANCE_EXTRA_ORE, config.getBoolean(root + ".chanceExtraOre"));
        skills.put(SkillType.CHANCE_EXTRA_WOOD, config.getBoolean(root + ".chanceExtraWood"));
        skills.put(SkillType.CHANCE_EXTRA_FEATHERS, config.getBoolean(root + ".chanceExtraFeathers"));
        skills.put(SkillType.CHANCE_NO_ANVIL_DAMAGE, config.getBoolean(root + ".chanceNoAnvilDamage"));
        skills.put(SkillType.CHANCE_NO_CONSUME_BLOCK, config.getBoolean(root + ".chanceNoConsumeBlock"));
        skills.put(SkillType.LADDER_BYPASS, config.getBoolean(root + ".ladderBypass"));
        skills.put(SkillType.IGNITE_OIL, config.getBoolean(root + ".igniteOil"));
        skills.put(SkillType.BREAK_GRASS, config.getBoolean(root + ".breakGrass"));
        skills.put(SkillType.TAME_ANIMALS, config.getBoolean(root + ".tameAnimals"));
        skills.put(SkillType.SWEEPING_EDGE, config.getBoolean(root + ".sweepingEdge"));
        skills.put(SkillType.BREAK_BEEHIVE, config.getBoolean(root + ".breakBeehive"));
        skills.put(SkillType.APPLY_UNBREAKING, config.getBoolean(root + ".applyUnbreaking"));
        skills.put(SkillType.APPLY_SHARPNESS, config.getBoolean(root + ".applySharpness"));
        skills.put(SkillType.SHEAR_CHICKEN, config.getBoolean(root + ".shearChicken"));
        skills.put(SkillType.HARVEST_BONES, config.getBoolean(root + ".bonesFromAnimals"));
        skills.put(SkillType.HARVEST_LEATHER, config.getBoolean(root + ".harvestLeather"));
        skills.put(SkillType.MAKE_KNOWLEDGE_BOOK, config.getBoolean(root + ".makeKnowledgeBook"));

        for (String item : config.getConfigurationSection(root + ".skills.enchants").getKeys(false)) {

            Pattern pattern = Pattern.compile(item);
            Enchantment enchantment = Enchantment.getByName(config.getString(root + ".skills.enchants." + item));
            skillEnchants.put(pattern, enchantment);

        }

    }

    public boolean hasSkill(SkillType skill) {
        return skills.get(skill);
    }

    public boolean hasSkillEnchants() {
        return !skillEnchants.isEmpty();
    }
    public Enchantment getSkillEnchantment(Material material) {

        for (Map.Entry<Pattern, Enchantment> entry : skillEnchants.entrySet()) {

            if (entry.getKey().matcher(material.toString()).matches())
                return entry.getValue();

        }

        return null;
    }

    @Override
    public String toString() {
        return "Skills{" +
                "skills=" + skills +
                '}';
    }
}
