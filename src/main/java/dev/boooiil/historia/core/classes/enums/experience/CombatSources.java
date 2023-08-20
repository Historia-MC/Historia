package dev.boooiil.historia.core.classes.enums.experience;

public enum CombatSources {

    NONE(AllSources.valueOf("NONE")),
    ATTACK(AllSources.valueOf("ATTACK")),
    DEFEND(AllSources.valueOf("DEFEND")),
    EVADE(AllSources.valueOf("EVADE")),
    KILL(AllSources.valueOf("KILL")),
    DEATH(AllSources.valueOf("DEATH")),
    RANGED_HIT(AllSources.valueOf("RANGED_HIT")),
    RANGED_KILL(AllSources.valueOf("RANGED_KILL"));

    private final AllSources key;

    CombatSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}