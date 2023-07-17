package dev.boooiil.historia.classes.enums;

public enum IncomeTypes {
    
    ATTACK(1l),
    DEFEND(2l),
    EVADE(2l),
    KILL(3l),
    DEATH(-1l),
    CROP_BREAK(1l),
    ORE_BREAK(1l),
    BLOCK_PLACE(1l);

    private long key;

    private IncomeTypes(long key) {
        this.key = key;
    }

    public long getKey() {
        return key;
    }

}
