package dev.boooiil.historia.core.proficiency.experience;

public enum CombatSources {

    NONE(AllSources.NONE),
    ATTACK(AllSources.ATTACK),
    DEFEND(AllSources.DEFEND),
    EVADE(AllSources.EVADE),
    KILL(AllSources.KILL),
    DEATH(AllSources.DEATH),
    RANGED_HIT(AllSources.RANGED_HIT),
    RANGED_KILL(AllSources.RANGED_KILL);

    private final AllSources key;

    CombatSources(AllSources key) {
        this.key = key;
    }

    public AllSources getKey() {
        return key;
    }

}