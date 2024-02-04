package dev.boooiil.historia.core.proficiency.skills;

public enum SkillType {
    NAME_TAG("nametag"),
    FEATHER_FALL("featherFall"),
    QUICK_CHARGE("quickCharge"),
    EFFICIENCY_PICKAXE("efficiencyPickaxe"),
    EFFICIENCY_SHOVEL("efficiencyShovel"),
    EFFICIENCY_AXE("efficiencyAxe"),
    CHANCE_EXTRA_ORE("chanceExtraOre"),
    CHANCE_EXTRA_WOOD("chanceExtraWood"),
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