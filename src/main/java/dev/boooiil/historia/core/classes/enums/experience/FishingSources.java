package dev.boooiil.historia.core.classes.enums.experience;

public enum FishingSources {

    NONE(AllSources.valueOf("NONE")),
    FISH(AllSources.valueOf("FISH")),
    FISH_TRASH(AllSources.valueOf("FISH_TRASH")),
    FISH_TREASURE(AllSources.valueOf("FISH_TREASURE"));

    private final AllSources key;

    FishingSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}