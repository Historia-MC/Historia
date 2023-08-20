package dev.boooiil.historia.core.classes.proficiency;

import dev.boooiil.historia.core.classes.enums.proficiency.SkillType;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

/**
 * This class represents the skills of a player in the game. It contains boolean
 * variables that represent
 * Whether the player has certain skills or abilities.
 */
public class Skills {

    private final HashMap<SkillType, Boolean> skills = new HashMap<>();

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

    }

    public boolean hasSkill(SkillType skill) {
        return skills.get(skill);
    }

    @Override
    public String toString() {
        return "Skills{" +
                "skills=" + skills +
                '}';
    }
}
