package dev.boooiil.historia.core.proficiency.experience;

/**
 * This is a list of all experience source incomes for farming activities.
 */
public enum FarmingSources {

    NONE(AllSources.NONE),
    CROP_PLACE(AllSources.CROP_BREAK),
    CROP_BREAK(AllSources.CROP_PLACE);

    private final AllSources key;

    FarmingSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}