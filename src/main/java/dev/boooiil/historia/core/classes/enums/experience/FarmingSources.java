package dev.boooiil.historia.core.classes.enums.experience;

public enum FarmingSources {

    NONE(AllSources.valueOf("NONE")),
    CROP_PLACE(AllSources.valueOf("CROP_PLACE")),
    CROP_BREAK(AllSources.valueOf("CROP_BREAK"));

    private final AllSources key;

    FarmingSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }


}