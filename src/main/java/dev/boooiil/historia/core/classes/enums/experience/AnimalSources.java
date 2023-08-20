package dev.boooiil.historia.core.classes.enums.experience;

public enum AnimalSources {

    NONE(AllSources.valueOf("NONE")),
    BREED_ANIMAL(AllSources.valueOf("BREED_ANIMAL")),
    TAME_ANIMAL(AllSources.valueOf("TAME_ANIMAL"));

    private final AllSources key;

    AnimalSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}