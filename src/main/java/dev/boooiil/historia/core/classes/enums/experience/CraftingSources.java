package dev.boooiil.historia.core.classes.enums.experience;

public enum CraftingSources {

    NONE(AllSources.valueOf("NONE")),
    WEAPON_CRAFT(AllSources.valueOf("WEAPON_CRAFT")),
    TOOL_CRAFT(AllSources.valueOf("TOOL_CRAFT")),
    ARMOR_CRAFT(AllSources.valueOf("ARMOR_CRAFT")),
    OTHER_CRAFT(AllSources.valueOf("OTHER_CRAFT"));

    private final AllSources key;

    CraftingSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}