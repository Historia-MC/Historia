package dev.boooiil.historia.core.proficiency.experience;

public enum BlockSources {

    NONE(AllSources.NONE),
    CROP_PLACE(AllSources.CROP_PLACE),
    CROP_BREAK(AllSources.CROP_BREAK),
    ORE_BREAK(AllSources.ORE_BREAK),
    BLOCK_PLACE(AllSources.BLOCK_PLACE),
    BLOCK_BREAK(AllSources.BLOCK_BREAK),
    BLOCK_INTERACT(AllSources.BLOCK_INTERACT);

    private final AllSources key;

    BlockSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}
