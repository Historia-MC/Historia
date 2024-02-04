package dev.boooiil.historia.core.proficiency.experience;

public enum AnimalSources {

    NONE(AllSources.NONE),
    BREED_ANIMAL(AllSources.BREED_ANIMAL),
    TAME_ANIMAL(AllSources.TAME_ANIMAL);

    private final AllSources key;

    AnimalSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}