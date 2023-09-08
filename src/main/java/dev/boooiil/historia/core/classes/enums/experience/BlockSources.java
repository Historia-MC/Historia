package dev.boooiil.historia.core.classes.enums.experience;

public enum BlockSources {

    NONE(AllSources.valueOf("NONE")),
    CROP_PLACE(AllSources.valueOf("CROP_PLACE")),
    CROP_BREAK(AllSources.valueOf("CROP_BREAK")),
    ORE_BREAK(AllSources.valueOf("ORE_BREAK")),
    BLOCK_PLACE(AllSources.valueOf("BLOCK_PLACE")),
    BLOCK_BREAK(AllSources.valueOf("BLOCK_BREAK")),
    BLOCK_INTERACT(AllSources.valueOf("BLOCK_INTERACT"));

    private final AllSources key;

    BlockSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}
