package dev.boooiil.historia.core.proficiency.experience;

public enum AnimalSources {

    NONE(AllSources.NONE),
    BREED_ANIMAL(AllSources.BREED_ANIMAL),
    KILL_ANIMAL(AllSources.KILL_ANIMAL),
    HARVEST_LEATHER(AllSources.HARVEST_LEATHER),
    HARVEST_BONES(AllSources.HARVEST_BONES),
    HARVEST_WOOL(AllSources.HARVEST_WOOL),
    HARVEST_FEATHERS(AllSources.HARVEST_FEATHERS),
    TAME_ANIMAL(AllSources.TAME_ANIMAL);

    private final AllSources key;

    AnimalSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}