package dev.boooiil.historia.core.proficiency.experience;

public enum CraftingSources {

    NONE(AllSources.NONE),
    WEAPON_CRAFT(AllSources.WEAPON_CRAFT),
    TOOL_CRAFT(AllSources.TOOL_CRAFT),
    ARMOR_CRAFT(AllSources.ARMOR_CRAFT),
    OTHER_CRAFT(AllSources.OTHER_CRAFT);

    private final AllSources key;

    CraftingSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}