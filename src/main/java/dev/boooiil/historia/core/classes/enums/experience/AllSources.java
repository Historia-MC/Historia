package dev.boooiil.historia.core.classes.enums.experience;

public enum AllSources {

    ATTACK(1d),
    DEFEND(2d),
    EVADE(2d),
    KILL(3d),
    DEATH(-2d),
    CROP_PLACE(1d),
    WEAPON_CRAFT(1d),
    TOOL_CRAFT(1d),
    ARMOR_CRAFT(1d),
    OTHER_CRAFT(1d),
    CROP_BREAK(1d),
    ORE_BREAK(1d),
    BLOCK_PLACE(1d),
    BLOCK_BREAK(1d),
    BLOCK_INTERACT(1d),
    BREED_ANIMAL(1d),
    TAME_ANIMAL(1d),
    FISH(1d),
    FISH_TRASH(-1d),
    FISH_TREASURE(3d),
    RANGED_HIT(1d),
    RANGED_KILL(2d),
    NONE(0d);

    private final double key;

    AllSources(double key) {
        this.key = key;
    }

    public double getKey() {
        return key;
    }

}
