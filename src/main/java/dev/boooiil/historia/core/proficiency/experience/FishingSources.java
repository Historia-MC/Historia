package dev.boooiil.historia.core.proficiency.experience;

public enum FishingSources {

    NONE(AllSources.NONE),
    FISH(AllSources.FISH),
    FISH_TRASH(AllSources.FISH_TRASH),
    FISH_TREASURE(AllSources.FISH_TREASURE);

    private final AllSources key;

    FishingSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}